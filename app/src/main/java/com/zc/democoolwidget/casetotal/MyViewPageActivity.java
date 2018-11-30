package com.zc.democoolwidget.casetotal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.zc.democoolwidget.R;
import com.zc.democoolwidget.casetotal.myview.MyScrollView;

public class MyViewPageActivity extends Activity{
	private MyScrollView myScrollView;
	private RadioGroup radioGroup;
	// 图片资源ID 数组
	private int[] ids = new int[] { R.drawable.a1, R.drawable.a2,
			R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6 };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mypage);
		myScrollView = (MyScrollView) findViewById(R.id.my_scrollview);
		radioGroup = (RadioGroup) findViewById(R.id.raidogroup);
		//页面要是这里radioGroup显示不了 就是页面布局问题
		for (int i = 0; i < ids.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(ids[i]);
			//将imageView 添加至msv中
			myScrollView.addView(imageView);
		}
		/*
		 * 添加测试用的页面
		 */
		View view = View.inflate(this, R.layout.test, null);
		myScrollView.addView(view,2);//添加到第三个页面
//		msv.getWidth();  //此时值为0
		/**
		 * 当系统需要测量本view的大小的时候，会调用此方法
		 * 做为一个view来说，只要测量自己的大小，就可以了，
		 * 但，做为viewGroup来说，除了测量自己的大小，还要发出命令，测量所有的子view的大小
		 */
		for (int i = 0; i < myScrollView.getChildCount(); i++) {
			RadioButton radioButton = new RadioButton(this);//有多少个页面就显示多少个radioButton
			if(i == 0){//第一个为选中状态
				radioButton.setChecked(true);
			}
			radioButton.setId(i);// 设置radioButton的ID值 与他的下标值，一样。
			radioGroup.addView(radioButton);
		}
		/**
		 * 上面的复选框随着页面的改变而改变
		 */
		myScrollView.setOnPageChangedListener(new MyScrollView.IOnPageChangedListener() {
			/**
			 * currIndex 页面切换时，新页面的下标值
			 */
			@Override
			public void onPagedChanged(int currIndex) {
//				((RadioButton)radioGroup.getChildAt(currIndex)).setChecked(true);  //该方法对im一些机器有用
				radioGroup.check(currIndex);
			}
		});
		/**
		 * 上面复选框点击改变而改变
		 */
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				myScrollView.moveToDest(checkedId);
			}
		});
	}
}
