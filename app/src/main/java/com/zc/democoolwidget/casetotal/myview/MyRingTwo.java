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
/**
 * 一个view从创建对象，至显示在屏幕上，之间的重要步骤：
 * 1、调用构造函数
 // *
 * 2、测量大小
 * 		onMeasure(width,height);
 * 3、确定位置
 * 		onLayout(b,l,t,r,b);
 * 4、绘制View的内容
 * 		onDraw(canvas);
 * 5.onTouchEvent
 */
public class MyRingTwo extends View{
	private float cx;//圆心的X坐标
	private float cy;//圆心的Y坐标
	private float radius = 0;//圆环半径
	private Paint paint;//默认画笔
	protected boolean isRunning = false;
	public MyRingTwo(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	private void init() {
		radius = 0;
		paint = new Paint();
		paint.setAntiAlias(true);//打开抗矩齿
		paint.setStyle(Style.STROKE);//设置画笔的绘图样式，为绘制线条
		paint.setStrokeWidth(radius/4);// 设置画笔的线条宽度
		paint.setColor(Color.RED);//设置画笔颜色
		paint.setAlpha(255);//该方法用于设置画笔的透明度，直观上表现为颜色变淡，具有一定的透明效果
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				cx = event.getX();
				cy = event.getY();
				init();
				startAnim();
				break;
		}
		return true;
	}
	/**
	 * 执行动画
	 */
	private void startAnim() {
		isRunning = true;
		handler.sendEmptyMessageDelayed(0, 50);
	}
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			// 修改圆环的参数
			//修改透明度
			int alpha = paint.getAlpha();
//		alpha -=10;
		/*
		 * alpha 的值是在0--255 之间的数字，如果越过这个范围，系统会自动转换
		 *  alpha 值 = -5  与  250 效果是一样的 数值越小越透明
		 */
//		if (alpha < 0) {
//			alpha = 0;
//		}
			if (alpha == 0) {
				isRunning = false;
			}
			alpha = Math.max(0, alpha-10);//返回数个数字中较大的值
			paint.setAlpha(alpha);//该方法用于设置画笔的透明度，直观上表现为颜色变淡，具有一定的透明效果
			//修改半径
			radius+=5;
			paint.setStrokeWidth(radius/3);
			// 刷新页面
			invalidate();
//		if(alpha >0){
			if (isRunning) {
				handler.sendEmptyMessageDelayed(0, 50);
			}
		};
	};
	/**
	 * 系统测量控件大小的时候，调用
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//测量大小 此例，按照系统的默认规则测量大小  默认 fill_parent
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 我们的任务，就是告诉系统，当前view想要多大空间   //宽和高
		//setMeasuredDimension(200, 200);//继承View，实现自己想要的组件,这个方法决定了当前View的大小
	}
	/**
	 * 当view的位置确定好以后，调用此方法
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
							int bottom) {
		if(changed){
			cx = getWidth()/2;
			cy = getHeight()/2;
		}
	}
	/**
	 * 绘制view的内容
	 */
	@Override
	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
		canvas.drawCircle(cx, cy, radius, paint);
	}
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
//		startAnim() ;
	}
	/**
	 * 销毁View的时候。我们写的这个View不再显示。
	 * onDetachedFromWindow在退出，销毁资源（既销毁view）之后调用
	 */
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		isRunning = false;
	}
}
