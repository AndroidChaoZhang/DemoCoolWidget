package com.zc.democoolwidget.casetotal;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.zc.democoolwidget.R;

import java.util.ArrayList;

public class AdvertActivity extends Activity{
	private ViewPager viewpager;
	private TextView tv_desc;
	private LinearLayout ll_point_group;
	private int lastPosition;//页面切换后，的上一个位置
	private boolean isRunning = false;
	// 图片标题集合
	private final String[] imageDescriptions = { "巩俐不低俗，我就不能低俗",
			"扑树又回来啦！再唱经典老歌引万人大合唱", "揭秘北京电影如何升级", "乐视网TV版大派送", "热血屌丝的反杀" };
	// 图片资源ID
	private final int[] imageIds = { R.drawable.a, R.drawable.b, R.drawable.c,
			R.drawable.d, R.drawable.e };
	private ArrayList<ImageView> imageList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advert);

		viewpager = (ViewPager) findViewById(R.id.viewpager);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
		imageList = new ArrayList<ImageView>();

		tv_desc.setText(imageDescriptions[0]);
		for (int i = 0; i < imageIds.length; i++) {
			/**
			 * 1.添加图片背景
			 */
			ImageView imageView = new ImageView(this);//ImageView类可以加载各种来源的图片
			imageView.setBackgroundResource(imageIds[i]);
			imageList.add(imageView);
			// image.setBackgroundResource(resid) // background //
			// 窒完填充满iamgeView ，但有可能变形
			// image.setImageResource(resId) // src 图片不会变形，但有可能不能完全填充满imageView
			/**
			 * 2.添加指示点
			 */
			ImageView point = new ImageView(this);
			//LayoutParams 的类型和要该view的父view的类型一致
			//LinearLayout.LayoutParams.WRAP_CONTENT=-2   MATCH_PARENT=-1
			LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, -2);
			params.leftMargin = 15;//每个左边距距离为15dp
			point.setLayoutParams(params);
			point.setBackgroundResource(R.drawable.point_bg);//获得焦点的背景
			if (i==0) {
				point.setEnabled(true);
			}else {
				point.setEnabled(false);
			}
			ll_point_group.addView(point);
		}
		viewpager.setAdapter(new MyPagerAdapter());
		/**
		 * 3.文字随图片的改变而改变
		 */
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			/**当选择的页面发生改变的时候，回调此方法
			 * position 选择的新的页面
			 */
			@Override
			public void onPageSelected(int position) {
				position = position%imageList.size();//4.往右循环，从5到1
				tv_desc.setText(imageDescriptions[position]);
				// 改变指示点的状态，
				// 让上一个位置的点，设置 enable 为 false
				ll_point_group.getChildAt(lastPosition).setEnabled(false);
				// 让当前位置的点，设置enable 为true
				ll_point_group.getChildAt(position).setEnabled(true);
				lastPosition = position;// 更新上一个位置的值
			}
			/**
			 * 当页面在滑动上，不断的调用
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}
			/**
			 * 当页面的滑动状发生改变的时候，回调 ，
			 * 状态有：按下，滑动，抬起，
			 */
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
		/**
		 * 4.设置viewPager当前页面的页面
		 * item 是页面的位置
		 */
		int item = Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2%imageList.size();
		viewpager.setCurrentItem(item);
		/**
		 * 5.实现动画的方式：
		 * 1、开子线程 while(true)  + Thread.sleep()
		 * 2、Timer
		 * 3、ClockManager 直接调用手机自身的功能
		 * 4、handler  处理高效
		 */
		isRunning = true;
		handler.sendEmptyMessageDelayed(1,4000);//信息2秒后发送执行handle里面的方法
	}
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {//设置当前的viewpager页面
			int cur_item = viewpager.getCurrentItem()+1;
			viewpager.setCurrentItem(cur_item);
			if (isRunning) {
				handler.sendEmptyMessageDelayed(1,4000);//信息2秒后发送执行handle里面的方法
			}
		};
	};
	class MyPagerAdapter extends PagerAdapter{
		/**
		 * 返回页面的个数
		 */
		@Override
		public int getCount() {
//			return imageList.size();
			return Integer.MAX_VALUE;
		}
		/**
		 * 实例化相应的条目
		 * position  该页面对应的位置
		 * container 页面view的父view  其实就是viewPager 自身
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
//			return super.instantiateItem(container, position);
			position = position%imageList.size();//4.往右循环，从5到1
			System.out.println("instantiateItem::"+position);
			// 1、根据当前position获得对应的view,并把view添加至container
			View view = imageList.get(position);
			container.addView(view);
			// 2、返回一个和view有关系的Object对象
			return view;
		}
		/**
		 * 判断view和object的对应关系
		 * view 是 instantiateItem 方法中 添加至 container 的view对象
		 * object 是 instantiateItem方法的返回值
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		/**
		 * 销毁某一个页面
		 * object instantiateItem方法的返回值
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			//下面这句，必须注掉，否则会抛异常，为啥？你懂的。
//			super.destroyItem(container, position, object);
			System.out.println("destroyItem::"+position);
			container.removeView((View) object);
		}
	}
}
