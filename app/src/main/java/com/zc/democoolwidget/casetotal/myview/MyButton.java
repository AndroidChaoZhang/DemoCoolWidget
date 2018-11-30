package com.zc.democoolwidget.casetotal.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.zc.democoolwidget.R;

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
public class MyButton extends View implements OnClickListener{
	private Bitmap slide_button;//滑动按钮的图片
	private Bitmap switch_background;//做为背景图的图片
	private Paint paint;
	private boolean currState = false;//判断当前开关的状态
	private int slideLeftMax;//滑动按钮，左边距的最大值
	private float slideLeft;//滑动按钮的左边距
	private int firstX;//down事件时的X坐标
	private int lastX;//上一个touch事件时的X坐标
	private boolean isDrop;//是否拖动，如果手指在屏幕上移动超过 10个像素，就认为是托动
	/**
	 * 在代码中，用关键字 new 创建对象的时候，调用
	 */
	public MyButton(Context context) {
		super(context);
	}
	/**
	 * 由系统自已用反射的方式调用，
	 * 使用场景：view对象在xml 布局文件当中声明，使用findViewById()方法，获得该view对象的时候，由系统自动调用
	 */
	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		/**
		 * 这是自定义的开关  图片可以随意更换  不用写死
		 */
		TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.MyButton);
		//这里要写自定义的位置 不然报错
		BitmapDrawable myButton = (BitmapDrawable) typeArray.getDrawable(R.styleable.MyButton_slide_button);
		BitmapDrawable myBackground = (BitmapDrawable) typeArray.getDrawable(R.styleable.MyButton_switch_background);
		slide_button = myButton.getBitmap();
		switch_background = myBackground.getBitmap();


		init();
	}
	/**几乎用不到
	 */
	public MyButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	private void init() {
		//自定义属性的myButton  myBackground将不需要虾米那两步
//		slide_button = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
//		switch_background = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
		slideLeftMax = switch_background.getWidth() - slide_button.getWidth();//当状态是开的时候是最大值
		/**
		 * 初始化画笔
		 */
		paint = new Paint();
		paint.setAntiAlias(true);// 打开抗矩齿
		/**
		 * 给当前view添加点击事件
		 */
		setOnClickListener(this);
	}
	/**
	 * 系统测量控件大小的时候，调用    设置当前view的测量值大小
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//获得view的测量值的大小，即view自己想要多大
//		getMeasuredWidth();
		//获得view的真实的大小。
//		getWidth();
		//设置当前view的测量值大小  与 背景图的大小，一致
		setMeasuredDimension(switch_background.getWidth(), switch_background.getHeight());//继承View，实现自己想要的组件,这个方法决定了当前View的大小
	}
	/**
	 * 绘制view的内容
	 */
	@Override
	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
		//绘制背景
		canvas.drawBitmap(switch_background, 0, 0, paint);
		//绘制滑动按钮
		canvas.drawBitmap(slide_button, slideLeft, 0, paint);//初始状态按钮显示
	}
	/**
	 * 点击按钮触发
	 */
	@Override
	public void onClick(View v) {
		if(isDrop){// 如果发生了托动，就直接返回，
			return ;
		}
		currState = !currState;
		//刷新状态
		flushState();
	}
	/**
	 * 拖动触发
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 点击事件，其实是TouchEvent中的一种，只要发生 MotionEvent.UP 事件，系统就认为，发生了onclick这个动作
		super.onTouchEvent(event);
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN://手按下的时候坐标
				firstX = (int) event.getX();//getRawX()和getRawY()获得的是相对屏幕的位置，
				lastX = (int) event.getX();//getX()和getY()获得的永远是相对view的触摸位置坐标（这两个值不会超过view的长度和宽度
				isDrop = false;
				break;
			case MotionEvent.ACTION_MOVE:
				int distance = (int) (event.getX()-lastX);//移动后的值减去刚触摸的值
				lastX = (int) event.getX();//上一次的值就成了移动后的值
				slideLeft += distance;
				//如果手指在屏幕上移动超过 10个像素，就认为是托动
				if (Math.abs(firstX-event.getX()) >10) {
					isDrop = true ;
				}
				break;
			case MotionEvent.ACTION_UP:
				if (isDrop) {
					//如果此时，滑动按钮的左边距 < 最大值的一半，应该是关的状态
					if (slideLeft < slideLeftMax/2) {
						currState = false;
					}else {
						//否则，是开的状态
						currState = true;
					}
					flushState();
				}
				break;
		}
		flushView();
		return true;
	}
	/**
	 * 刷新状态  此时不能超过开关前后  （视图）
	 */
	private void flushState() {
		if (currState) {//开   左边距 = 背景 - 滑动按钮
			slideLeft = slideLeftMax;
		}else{
			slideLeft = 0;
		}
		flushView();
	}
	/**
	 * 刷新视图   这里判断
	 */
	private void flushView() {
		if(slideLeft<=0){
			slideLeft=0;
		}
		if(slideLeft>slideLeftMax){
			slideLeft = slideLeftMax;
		}
		/**
		 * 刷新当前页面          会导致OnDraw方法的执行
		 */
		invalidate();
	}
}
