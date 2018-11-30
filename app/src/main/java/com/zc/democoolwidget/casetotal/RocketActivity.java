package com.zc.democoolwidget.casetotal;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.zc.democoolwidget.R;

public class RocketActivity extends Activity{
	private ImageView rocketImage;
	private AnimationDrawable rocketAnimation;
	private ImageView iv_bottom;
	private ImageView iv_top;
	/**
	 * AnimationDrawable:通过一系列的图片，来创建帧动画。 1.多个图片。
	 * 2.创建xml文件，在该xml文件中将这些图片一一列出 3.控件将该xml文件设为背景 4.控件获得背景 5.开启动画
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rocket);

		rocketImage = (ImageView) findViewById(R.id.iv_rocket);
		iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
		iv_top = (ImageView) findViewById(R.id.iv_top);
		/**
		 * 启动小火箭的火焰运动  1.设置火箭的背景 2.获取火箭背景 3.开启
		 */
		rocketImage.setBackgroundResource(R.drawable.rocket);
		rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
		rocketAnimation.start();
		/**
		 * 小火箭移动接触事件
		 */
		rocketImage.setOnTouchListener(new OnTouchListener() {
			//这里很重要  要不是全局变量就会出问题
			int startX = 0;
			int startY = 0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {//获得事件的类型
					case MotionEvent.ACTION_DOWN:
						//getX()是表示Widget相对于自身左上角的x坐标;getRawX()是表示相对于屏幕左上角的x坐标值(注意:这个屏幕左上角是手机屏幕左上角,不管activity是否有titleBar或是否全屏幕),getY(),getRawY()一样的道理
						startX = (int) event.getRawX();
						startY = (int) event.getRawY();
						break;
					case MotionEvent.ACTION_MOVE:
						int newX = (int) event.getRawX();
						int newY = (int) event.getRawY();
						int dX = newX - startX;
						int dY = newY - startY;
						rocketImage.layout(rocketImage.getLeft()+dX, rocketImage.getTop()+dY, rocketImage.getRight()+dX, rocketImage.getBottom()+dY);
						startX = (int) event.getRawX();
						startY = (int) event.getRawY();
						break;
					case MotionEvent.ACTION_UP:
						//火箭发射架
						int newl = rocketImage.getLeft();
						int newt = rocketImage.getTop();
						int newr = rocketImage.getRight();
						if(newl > 100&&newt>320&&newr<320){
							Toast.makeText(getApplicationContext(), "火箭发射架", 0).show();
							sendRocket();
							//火箭上天的烟
							AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
							alphaAnimation.setStartOffset(10);//执行前的等待时间
							SystemClock.sleep(500);//让烟先延迟一段时间
							alphaAnimation.setDuration(400);//设置动画持续时间
							alphaAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
							alphaAnimation.setRepeatCount(1);//设置重复次数
							alphaAnimation.setRepeatMode(AlphaAnimation.REVERSE);//取反
							iv_bottom.setVisibility(View.VISIBLE);
							iv_top.setVisibility(View.VISIBLE);
							iv_bottom.setAnimation(alphaAnimation);
							iv_top.setAnimation(alphaAnimation);
						}
						break;
				}
				//setOnTouchListener 单独使用的时候返回值需要为true，这样才能保证移动的时候能后获取相应的监听，而非一次监听（即每次只有一个按下的事件）
				return true;
			}
		});
	}
	/**
	 * 火箭发射系统
	 */
	private void sendRocket() {
		new Thread(){
			public void run() {
				for (int i = 0; i < 12; i++) {
					SystemClock.sleep(5);//火箭发射太快 需要休眠肉眼看的见
					int postion = 380 - 38*i;
					Message msg = Message.obtain();
					msg.obj = postion;
					handler.sendMessage(msg);
				}
			};
		}.start();
	}
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			int position = (Integer)msg.obj;
			//针对其父视图的相对位置
			rocketImage.layout(rocketImage.getLeft(), position, rocketImage.getRight(), position+rocketImage.getHeight());
//			if(position<320){
//			iv_top.setVisibility(View.VISIBLE);
//			AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
//			aa.setDuration(300);
//			iv_top.startAnimation(aa);
//			
//		}
//		if(position<20){
//			AlphaAnimation aa = new AlphaAnimation(1.0f, 0.0f);
//			aa.setDuration(200);
//			aa.setFillAfter(true);
//			iv_top.startAnimation(aa);
//		}
		};
	};
}
