package com.zc.democoolwidget.casetotal.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
/**
 * 一个view从创建对象，至显示在屏幕上，之间的重要步骤：
 * 1、调用构造函数
 *
 * 2、测量大小
 * 		onMeasure(width,height);
 * 3、确定位置
 * 		onLayout(b,l,t,r,b);
 * 4、绘制View的内容
 * 		onDraw(canvas);
 * 5.onTouchEvent
 */
public class MyRingThree extends View{
	/**
	 * 二个相临波浪中心点的最小距离
	 */
	private static final int DIS_SOLP = 13;
	protected boolean isRunning = false;
	private ArrayList<Wave> wList;
	private int [] colors = new int[]{Color.BLUE,Color.RED,Color.YELLOW,Color.GREEN};
	public MyRingThree(Context context, AttributeSet attrs) {
		super(context, attrs);
		wList = new ArrayList<Wave>();
	}
	/**
	 * 绘制view的内容
	 */
	@Override
	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
		for (int i = 0; i < wList.size(); i++) {
			Wave wave = wList.get(i);
			canvas.drawCircle(wave.cx, wave.cy, wave.r, wave.p);
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				int x = (int) event.getX();
				int y = (int) event.getY();
				addPoint(x,y);
				break;

			default:
				break;
		}
		return true;
	}
	/**
	 * 添加新的波浪中心点
	 */
	private void addPoint(int x, int y) {
		if (wList.size()==0) {
			addPoint2List(x,y);
			//第一次启动动画
			isRunning = true;
			handler.sendEmptyMessage(0);
		}else{
			Wave w = wList.get(wList.size()-1);
			if(Math.abs(w.cx - x)>DIS_SOLP || Math.abs(w.cy-y)>DIS_SOLP){
				addPoint2List(x,y);
			}
		}
	}
	/**
	 * 添加新的波浪
	 */
	private void addPoint2List(int x, int y) {
		Wave w = new Wave();
		w.cx = x;
		w.cy=y;
		Paint pa=new Paint();
		pa.setColor(colors[(int)(Math.random()*4)]);//产生一个1到4的随机数字
		pa.setAntiAlias(true);//打开抗矩齿
		pa.setStyle(Style.STROKE);//设置画笔的绘图样式，为绘制线条
		w.p=pa;
		wList.add(w);
	}
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			//刷新数据
			flushData();
			//刷新页面
			invalidate();
			//循环动画
			if (isRunning) {
				handler.sendEmptyMessageDelayed(0, 50);
			}
		};
	};
	/**
	 * 刷新数据
	 */
	private void flushData() {
		for (int i = 0; i < wList.size(); i++) {
			Wave w = wList.get(i);
			//如果透明度为 0 从集合中删除
			int alpha = w.p.getAlpha();
			if(alpha == 0){
				wList.remove(i);	//删除i 以后，i的值应该再减1 否则会漏掉一个对象，不过，在此处影响不大，效果上看不出来。
				continue;
			}
			alpha-=5;
			if(alpha<5){
				alpha =0;
			}
			//降低透明度
			w.p.setAlpha(alpha);
			//扩大半径
			w.r = w.r+3;
			//设置半径厚度
			w.p.setStrokeWidth(w.r/3);
		}
		/*
		 * 如果集合被清空，就停止刷新动画
		 */
		if(wList.size() == 0){
			isRunning = false;
		}
	}
	/**
	 * 定义一个波浪
	 */
	private class Wave {
		int cx;//圆心X坐标
		int cy;//圆心Y坐标
		Paint p;//画笔
		int r;//半径
	}
}
