package com.zc.democoolwidget.casetotal.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 1.创建背景的图片  onMeasure onLayout两个方法
 * 2.GestureDetector onTouchEvent触摸事件
 * 3.onInterceptTouchEvent中断事件
 * 4.IOnPageChangedListener 1.2.3.4
 * @author ZC
 *
 */
public class MyScrollView extends ViewGroup{
	private Context context;
	private GestureDetector gestureDetector;
	private int firstX;//down事件时的X会标
	private int lastX;//上一个事件时的X会标
	private boolean isFling = false;//刚开始设置快速滑动标识符为false
	private int currIndex=0;//当前屏幕显示的子view的下标
	private Scroller scroller;//系统的快速滑动     基本上都使用这个
	//	private MyScroller scroller;//缓慢滑动
	private int downX;//在中断事件方法 中，down 事件的X的值
	private int downY;//在中断事件方法 中，down 事件的Y的值
	public MyScrollView(Context context) {
		super(context);
	}
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;//方便调用context
		init();
	}

	private void init() {
		scroller = new Scroller(context);
//		scroller =new MyScroller(context);
		//手势监听
		gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
			/**3.
			 * 当手指在屏幕上，平滑移动的时候，回调此方法
			 */
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
									float distanceY) {
				/*
				 * 让当前view的内容 移动一段距离
				 * distanceX x方向的距离
				 * distanceY y方向的距离
				 */
				//在当前视图内容继续偏移(x , y)个单位，显示(可视)区域也跟着偏移(x,y)个单位
				scrollBy((int) distanceX, 0);//为了让view中的内容移动，即图片
				Log.e("MyLog", "onScroll: ===="+distanceX );
				return false;
			}
			/**6.
			 * 当手指在屏幕上快速滑动的时候，回调此方法
			 * 这个事快速滑动页面切换效果
			 */
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
								   float velocityY) {//e1:第1个ACTION_DOWN MotionEvent  e2：最后一个 ACTION_MOVE MotionEvent
				System.out.println("velocityX::"+velocityX);
				isFling = true;
				// 手指向右滑，速度为正数
				if (velocityX>0 && currIndex>0) {
					currIndex--;
				}
				// 手指向左滑，速度为负数
				if (velocityX<0 && currIndex<getChildCount()-1) {
					currIndex++;
				}
				moveToDest(currIndex);//移动到当前的下标那里  即滑动后应该显示的页面
				Log.e("MyLog", "currIndex: ===="+currIndex );
				return false;
			}
			@Override
			public boolean onSingleTapUp(MotionEvent e) {//轻击触摸屏后，弹起。如果这个过程中产生了onLongPress、onScroll和onFling事件，就不会产生onSingleTapUp事件
				return false;
			}
			@Override
			public void onShowPress(MotionEvent e) {//点击了触摸屏，但是没有移动和弹起的动作
			}
			@Override
			public void onLongPress(MotionEvent e) {// 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
			}
			@Override
			public boolean onDown(MotionEvent e) {//鼠标按下的时候，会产生onDown。由一个ACTION_DOWN产生
				return false;
			}
		});
	}
	/**8.
	 * 判断是否中断事件的传递
	 * false 不中断        默认为
	 * true 中断
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean result = false;
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downX = (int) ev.getX();
				downY = (int) ev.getY();
			/*
			 * 下面这二句，是为了解决当中断的时候， onTouchEvent方法中，没有DOWN事件，所产生 的BUG
			 */
				gestureDetector.onTouchEvent(ev);
				firstX = lastX = (int) ev.getX();
				break;
			case MotionEvent.ACTION_MOVE:
				// 如果水平滑动，大于 竖直滑动，，那么就返回   true
				//否则  返回 false 不中断事件，按默认的规则执行
				int distanceX = (int) Math.abs(ev.getX()-downX);
				int distanceY = (int) Math.abs(ev.getY()-downY);
				if(distanceX > distanceY && distanceX > 5){
					result = true;
				}else{
					result = false;
				}
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		return result;
	}
	/**
	 * 1.当系统需要测量本view的大小的时候，会调用此方法
	 * 做为一个view来说，只要测量自己的大小，就可以了，
	 * 但，做为viewGroup来说，除了测量自己的大小，还要发出命令，测量所有的子view的大小
	 * 所以它会执行两次
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//MeasureSpec封装了父布局传递给子布局的布局要求，每个MeasureSpec代表了一组宽度和高度的要求
		int size = MeasureSpec.getSize(widthMeasureSpec);
		System.out.println("size:"+size);
		int mode = MeasureSpec.getMode(widthMeasureSpec);
		System.out.println("mode:"+mode);
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			view.measure(widthMeasureSpec, heightMeasureSpec);
		}
	}
	/**2.
	 * l t r b  是指，当前viewGroup在他的父view中的位置
	 * left top right bottom
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		/**
		 * 当父view为我确定位置后，会调用onLayout方法，而我在此时，应该做的事，是，为我的子view确定位置
		 */
//		View child1 = getChildAt(0);
//		child1.layout(30, 50, 200, 240);
//		View child2 = getChildAt(1);
		// l t r b  是指，child2  在他的父view中的位置
//		child2.layout(60, 250, 400, 500);
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
//			view.getWidth();  值为0
			view.layout(0+getWidth()*i, 0, getWidth()+i*getWidth(), getHeight());
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/**4.
		 * 手指点击在listView中，down事件，被listview处理了
		 * 手指水平移，不断产生move事件，当水平滑动大于竖起滑动时，onInterceptTouchEvent 返回true 中断事件的传递，
		 * 此时，onTouchEvent 才开始执行，
		 * 所以： 此时，onTouchEvent 方法 ，收到的第一个事件，是 MOVE 事件，
		 *
		 */
		//用于手势处理接触事件  接收事件的为手势处理
		gestureDetector.onTouchEvent(event);
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				//设置第一次点击的页面下标  开始定位
				firstX = (int) event.getX();
				lastX = (int) event.getX();
//			isFling = false;//这里设为false  或者up的时候设为false
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				if (!isFling) {//解决与快速滑动的冲突
					// up 时的x - down时的x > 宽度的一半 就让 currIndex -1
					// down时的x - up 时的x> 宽度的一半 就让 currIndex +1
					// 否则 currIndex 不变
					int tempIndex = currIndex;//把当前页面下表赋给临时下标
					if (event.getX()-firstX > getWidth()/2) {
						tempIndex--;
					}else if (firstX - event.getX() > getWidth()/2){
						tempIndex++;
					}
					moveToDest(tempIndex);//让指定下标的子view移动到屏幕上
				}
				isFling = false;//这里设为false 或者down的时候设为false
				break;
		}
		return true;
	}
	/**7.
	 * 重写View的computeScroll()，完成实际的滚动
	 */
	@Override
	public void computeScroll() {
		Log.e("Mylog", "computeScroll: " );
		if(scroller.computeScrollOffset()){//返回值为boolean，true说明滚动尚未完成，false说明滚动已经完成   用来判断是否滚动是否结束
			Log.e("Mylog", "computeScroll:1111 " );
			long currX = scroller.getCurrX();
			scrollTo((int) currX, 0);
			invalidate(); //  invalidate 刷新页面，会导致  computeScroll
		}
	}
	/**5.
	 * 让指定下标的子view移动到屏幕上
	 * @param tempIndex
	 */
	public void moveToDest(int tempIndex) {
		if (tempIndex<0) {
			tempIndex = 0;
		}
		if (tempIndex>getChildCount()-1) {
			tempIndex = getChildCount()-1;
		}
		currIndex = tempIndex;
		/**
		 * 4.激活事件监听
		 */
		if (null != onPageChangedListener) {
			onPageChangedListener.onPagedChanged(currIndex);
		}
		// 让下标为currIndex的子view移动到屏幕上   这是瞬间移动的  我们需要匀速缓慢运动的
//		scrollTo(currIndex*getWidth(), 0);//在当前视图内容偏移至(x , y)坐标处，即显示(可视)区域位于(x , y)坐标处。
		// 要移动的距离  =  终点坐标  - 当前的坐标
		int distanceX =currIndex*getWidth() - getScrollX();//getScrollX() 就是当前view的左上角相对于母视图的左上角的X轴偏移量
		//滚动，startX, startY为开始滚动的位置，dx,dy为滚动的偏移量, duration为完成滚动的时间  使用默认完成时间250ms
//		scroller.startScroll(getScrollX(), 0, distanceX, 0);//这是页面切换的时候速度很快的时候
		scroller.startScroll(getScrollX(), 0, distanceX, 0,Math.abs(distanceX));//页面切换每个页面都有固定的时间，从一切换到五就花五倍时间
		invalidate(); //  invalidate 刷新页面，会导致  computeScroll
	}

	/**
	 * 1.2.3
	 * 1.当页面发生改变时的，回调接口
	 */
	public interface IOnPageChangedListener{
		public void onPagedChanged(int currIndex);
	}
	/**
	 * 2.声明接口类型的变量
	 */
	private IOnPageChangedListener onPageChangedListener;
	public IOnPageChangedListener getOnPageChangedListener() {
		return onPageChangedListener;
	}
	/**
	 * 3.  get set方法
	 * @param onPageChangedListener
	 */
	public void setOnPageChangedListener(IOnPageChangedListener onPageChangedListener) {
		this.onPageChangedListener = onPageChangedListener;
	}
	/**
	 * 4.激活事件监听         搜索（激活事件监听）
	 */
}
