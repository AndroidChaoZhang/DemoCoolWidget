package com.zc.democoolwidget.casetotal.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
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
 */
public class MyRingOne extends View{
	private float cx;//x起点
	private float cy;//y起点
	private float r;//圆心点
	private Paint paint;//画笔
	private Paint lPaint;
	/**
	 * 在代码中，用关键字 new 创建对象的时候，调用
	 */
	public MyRingOne(Context context) {
		super(context);
	}
	/**
	 * 由系统自已用反射的方式调用，
	 * 使用场景：view对象在xml 布局文件当中声明，使用findViewById()方法，获得该view对象的时候，由系统自动调用
	 */
	public MyRingOne(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**几乎用不到
	 */
	public MyRingOne(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	/**
	 * 绘制画笔
	 */
	public void init() {
		paint = new Paint();
		paint.setAntiAlias(true);//打开抗矩齿
		paint.setStyle(Style.STROKE);//设置画笔的绘图样式，为绘制线条
		paint.setStrokeWidth(1000);// 设置画笔的线条宽度
		paint.setColor(Color.RED);//设置画笔颜色
		paint.setAlpha(255);//该方法用于设置画笔的透明度，直观上表现为颜色变淡，具有一定的透明效果

		lPaint = new Paint();
		lPaint.setAntiAlias(true);
		lPaint.setStyle(Style.STROKE);
		lPaint.setColor(Color.GREEN);
	}
	/**
	 * 点击事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				//getX()是表示相对于自身左上角的x坐标;getRawX()是表示相对于屏幕左上角的x坐标值(注意:这个屏幕左上角是手机屏幕左上角,不管activity是否有titleBar或是否全屏幕),getY(),getRawY()一样的道理
				cx = event.getX();
				cy = event.getX();
				init();
				break;

			default:
				break;
		}
		return true;
	}
	/**
	 * 系统测量控件大小的时候，调用
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//测量大小 此例，按照系统的默认规则测量大小  默认 fill_parent
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 我们的任务，就是告诉系统，当前view想要多大空间   //宽和高
		setMeasuredDimension(200, 200);//继承View，实现自己想要的组件,这个方法决定了当前View的大小
	}
	/**
	 * 当view的位置确定好以后，调用此方法
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
							int bottom) {
//		super.onLayout(changed, left, top, right, bottom);
		if (changed) {
			cx = getWidth()/2;//getWidth(): View在设定好布局后整个View的宽度
			cy = getHeight()/2;
			/*getMeasuredWidth(): 对View上的内容进行测量后得到的View内容占据的宽度，
			前提是你必须在父布局的onLayout()方法或者此View的onDraw()方法里调用measure(0,0);
			(measure 参数的值你可以自己定义)，否则你得到的结果和getWidth()得到的结果一样
			*/
		}
	}
	/**
	 * 绘制view的内容
	 */
	@Override
	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
		for (int i = 0; i <  getWidth(); i=i+20) {
			canvas.drawLine(0, i, getWidth(), i, lPaint);
			canvas.drawLine(i, 0, i,getHeight(), lPaint);
		}
//		canvas.translate(160, 160);//把当前画布的原点移到(-20, -20),后面的操作都以(-20, -20)作为参照点,默认原点为(0,0)
//		canvas.drawCircle(cx, cy, r, paint);
		//canvas.restore();//把当前画布返回（调整）到上一个save()状态之前
	}
}
