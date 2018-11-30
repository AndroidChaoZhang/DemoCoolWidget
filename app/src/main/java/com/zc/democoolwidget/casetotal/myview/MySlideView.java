package com.zc.democoolwidget.casetotal.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
/**
 * 1.onMeasure onLayout 创建出UI
 * 2.computeScroll changeState flushState
 * 3.onTouchEvent
 * @author ZC
 *
 */
public class MySlideView extends ViewGroup{
	private Scroller scroller;
	private View content;
	private View menu;
	private boolean isMenuShow = false;
	private int firstX;//down事件时的X会标
	private int lastX;//上一个事件时的X会标
	public MySlideView(Context context, AttributeSet attrs) {
		super(context, attrs);
		scroller = new Scroller(context);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		menu = getChildAt(0);//获得MySlideView下的第一个include内容
		content = getChildAt(1);
		//MeasureSpec.EXACTLY，父视图希望子视图的大小是specSize中指定的大小
		int measureSpecwidth = MeasureSpec.makeMeasureSpec(menu.getLayoutParams().width,  MeasureSpec.EXACTLY);
		menu.measure(measureSpecwidth, heightMeasureSpec);
		content.measure(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		System.out.println("layout:::"+getChildCount());
		//getWidth 得到的事某个View的实际尺寸。
		//getMeasuredWidth 得到的是某个View想要在parent view里面占的大小
		menu.layout(0-menu.getMeasuredWidth(), 0, 0, b);
		content.layout(0, 0, r, b);
	}
	/**
	 * 重写View的computeScroll()，完成实际的滚动
	 * ontouch或invalidate()或postInvalidate()都会导致这个方法的执行。
	 */
	@Override
	public void computeScroll() {
		if(scroller.computeScrollOffset()){//返回值为boolean，true说明滚动尚未完成，false说明滚动已经完成   用来判断是否滚动是否结束
			int currX = scroller.getCurrX();
			scrollTo(currX, 0);
			invalidate(); //  invalidate 刷新页面，会导致  computeScroll
		}
	}
	public void changeState() {
		isMenuShow = !isMenuShow;
		flushState();
	}
	private void flushState() {
		int distance = 0;
		if(!isMenuShow){
//			scrollTo(0,0);
			distance = 0-getScrollX();
		}else{
//			scrollTo(-menu.getWidth(),0);
			distance = -menu.getWidth()-getScrollX();
		}
		//滚动，startX, startY为开始滚动的位置，dx,dy为滚动的偏移量, duration为完成滚动的时间  使用默认完成时间250ms
		//这是页面切换的时候速度很快的时候
		scroller.startScroll(getScrollX(), 0, distance, 0);
		invalidate();//  invalidate 刷新页面，会导致  computeScroll
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				firstX = lastX = (int) event.getX();
				break;
			case MotionEvent.ACTION_MOVE:
				int distanceX = (int) (lastX - event.getX());//down的坐标与移动后的间距
				lastX = (int) event.getX();//更新最后的坐标
				int nextScrollX =  getScrollX()+distanceX;//getScrollX() 就是当前view的左上角相对于母视图的左上角的X轴偏移量
				if( nextScrollX >= -menu.getWidth() && nextScrollX <=0 ){
					scrollBy(distanceX, 0);//为了让view中的内容移动，即图片
				}
				break;
			case MotionEvent.ACTION_UP:
				int curScrollX = getScrollX();//getScrollX() 就是当前view的左上角相对于母视图的左上角的X轴偏移量
				if(curScrollX > -menu.getWidth()/2){
					isMenuShow = false;
				}else{
					isMenuShow = true;
				}
				flushState();
				break;
		}
		return true;
	}
}
