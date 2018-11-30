package com.zc.democoolwidget.casetotal.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;

public class Tools {
	/**
	 * 立刻 隐藏指定的view对象
	 */
	public static void hideView(ViewGroup view) {
		hide(view,0);
	}
	/**
	 * 显示指定的view对象
	 */
	public static void showView(ViewGroup view) {
		show(view,0);
	}
	/**
	 * 延时显示指定的view
	 */
	public static void show(ViewGroup view, int i) {
//      圆心： getwindth()/2   ,  getHeight();
//      逆时针隐藏:          360  ---  180
//      顺时针显示：         180  ---  360
         /*
         * 张超  顺时针起点在左边，即0 ，右边是180
         */
		//RotateAnimation rotateAnimation = new RotateAnimation(360, 180, view.getWidth()/2, view.getHeight());
		RotateAnimation rotateAnimation = new RotateAnimation(180, 360, view.getWidth()/2, view.getHeight());//顺时针转动
		rotateAnimation.setDuration(500);//设置动画的时长
		rotateAnimation.setFillAfter(true);//动画完成后，保持完成的状态
		view.startAnimation(rotateAnimation);//千万记得写上  开启动画
		view.setEnabled(true);
		view.setVisibility(View.VISIBLE);
  		/*
  		 * 修改BUG
  		 */
		for (int j = 0; j <view.getChildCount(); j++) {
			View child = view.getChildAt(j);
			child.setClickable(true);
		}
	}
	/**
	 * 延时隐藏指定的view
	 */
	public static void hide(ViewGroup view, int i) {
//      圆心： getwindth()/2   ,  getHeight();
//      逆时针隐藏:          360  ---  180
//      顺时针显示：         180  ---  360
		RotateAnimation rotateAnimation = new RotateAnimation(0, 180, view.getWidth()/2, view.getHeight());//顺时针转动
		/*
         * 张超  逆时针起点在左边，即180 ，右边是0
         */
		//RotateAnimation  rotateAnimation = new RotateAnimation(180, 0, view.getWidth()/2, view.getHeight());
		rotateAnimation.setDuration(500);//设置动画的时长
		rotateAnimation.setFillAfter(true);//动画完成后，保持完成的状态
		view.startAnimation(rotateAnimation);//千万记得写上  开启动画
		/**
		 * ViewGroup中的常用方法：
		 * 		view.getChildCount(); // 返回子view的个数
		 view.getChildAt(i); // 返回指定下标的子view
		 */
		// 解决图标不可见，但依能点击的BUG，将需要响应点击事件的图标，设置 为不可点击的状态
		for (int j = 0; j < view.getChildCount(); j++) {
			View childAt = view.getChildAt(j);
			childAt.setClickable(false);
		}
	}
}
