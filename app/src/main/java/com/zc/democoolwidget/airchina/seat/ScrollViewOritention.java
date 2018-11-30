package com.zc.democoolwidget.airchina.seat;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ScrollViewOritention extends ScrollView {
 
	private ScrollListener mListener;
 
	public static interface ScrollListener {
		public void scrollOritention(String oritention);
	}

	/**
	 * 最小的滑动距离
	 */
	private static final int SCROLLLIMIT = 8;
 
	public ScrollViewOritention(Context context) {
		super(context, null);
	}
 
	public ScrollViewOritention(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
	}
 
	public ScrollViewOritention(Context context, AttributeSet attrs,
								int defStyle) {
		super(context, attrs, defStyle);
	}
 
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
 
		if (oldt > t && oldt - t > SCROLLLIMIT) {// 向下
			if (mListener != null)
				mListener.scrollOritention("down");
		} else if (oldt < t && t - oldt > SCROLLLIMIT) {// 向上
			if (mListener != null)
				mListener.scrollOritention("up");
		}
	}
 
	public void setScrollListener(ScrollListener l) {
		this.mListener = l;
	}
}