package com.zc.democoolwidget.recycleview.simple;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

public class SimpleLayoutManager extends RecyclerView.LayoutManager {

    /**保存所有的Item的上下左右的偏移量信息*/
    private SparseArray<Rect> allItemFrames = new SparseArray<>();
    /**记录Item是否出现过屏幕且还没有回收。true表示出现过屏幕上，并且还没被回收*/
    private SparseBooleanArray hasAttachedItems = new SparseBooleanArray();

    /**
     * 为每一个childView设置的LayoutParams在这个方法中返回。很简单，一般我们都直接返回一个WrapContent的lp
     * 这个方法就是RecyclerView Item的布局参数，若是想修改子Item的布局参数（比如：宽/高/margin/padding等等），那么可以在该方法内进行设置。
     * */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int itemCount = getItemCount();
        if (itemCount <= 0) {////如果没有item，直接返回
            return;
        } else if (state.isPreLayout()) {// 跳过preLayout，preLayout主要用于支持动画
            return;
        }
        //在布局之前，将所有的子view先detach掉，放入到Scrap中   detach轻量回收View
        detachAndScrapAttachedViews(recycler);

        int offsetHeight = 0;
        View viewForPosition = recycler.getViewForPosition(0);// 根据position获取一个碎片view，可以从回收的view中获取，也可能新构造一个
        addView(viewForPosition);//将View加入到RecyclerView中
        measureChildWithMargins(viewForPosition,0,0);//对子View进行测量
        for (int i = 0; i < getItemCount(); i++) {
            ///把宽高拿到，宽高都是包含ItemDecorate的尺寸
            int decoratedMeasuredWidth = mTotalWidth = getDecoratedMeasuredWidth(viewForPosition);
            int decoratedMeasuredHeight = getDecoratedMeasuredHeight(viewForPosition);
//            layoutDecoratedWithMargins(viewForPosition,0,offsetHeight,decoratedMeasuredWidth, offsetHeight+decoratedMeasuredHeight);//最后，将View布局

            Rect frame = allItemFrames.get(i);
            if (null == frame) {
                frame = new Rect();
            }
            frame.set(0,offsetHeight,decoratedMeasuredWidth,offsetHeight+decoratedMeasuredHeight);
            allItemFrames.put(i,frame);// 将当前的Item的Rect边界数据保存
            hasAttachedItems.put(i,false);// 由于已经调用了detachAndScrapAttachedViews，因此需要将当前的Item设置为未出现过
            offsetHeight += decoratedMeasuredHeight;
        }
        //如果所有子View的高度和没有填满RecyclerView的高度，
        // 则将高度设置为RecyclerView的高度
        mTotalHeight = Math.max(offsetHeight,getVerticalSpace());
        recycleAndFillItems(recycler, state);
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }


    private int mVerticalScrollOffset = 0;//竖直方向的偏移量
    private int mTotalHeight = 0;//总高度
    private int mTotalWidth = 0;//宽度
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int realScrollY = dy;//实际要滑动的距离
        //如果滑动到最顶部
        if (mVerticalScrollOffset + dy < 0) {
            realScrollY = -mVerticalScrollOffset;
        } else if (mVerticalScrollOffset + dy > mTotalHeight - getVerticalSpace()) {//如果滑动到最底部
            realScrollY = mTotalHeight - getVerticalSpace() - mVerticalScrollOffset;
        }

        mVerticalScrollOffset += realScrollY;
        offsetChildrenVertical(-realScrollY);//垂直移动RecyclerView内所有的item
        recycleAndFillItems(recycler, state);
        return realScrollY;

    }

    /**
     * 回收不需要的Item，并且将需要显示的Item从缓存中取出
     */
    private void recycleAndFillItems(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {// 跳过preLayout，preLayout主要用于支持动画
            return;
        }
        // 当前scroll offset状态下的显示区域
        Rect displayFrame = new Rect(getPaddingLeft(), mVerticalScrollOffset, mTotalWidth - getPaddingRight(), mVerticalScrollOffset + getVerticalSpace());
        Rect recycleFrame = new Rect(getPaddingLeft(), 0, mTotalWidth - getPaddingRight(), getVerticalSpace());
        //将滑出屏幕的Items回收到Recycle缓存中
        Rect childFrame = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            childFrame.left = getDecoratedLeft(childAt);
            childFrame.top = getDecoratedTop(childAt);
            childFrame.right = getDecoratedRight(childAt);
            childFrame.bottom = getDecoratedBottom(childAt);
            //如果Item没有在显示区域，就说明需要回收
            if (!Rect.intersects(childFrame,recycleFrame)) {
                Log.e("MyLog","====="+"回收：");
                removeAndRecycleView(childAt,recycler);
            }
        }
        //先detach掉所有的子View
        detachAndScrapAttachedViews(recycler);
        //重新显示需要出现在屏幕的子View
        for (int i = 0; i < getItemCount(); i++) {
            if (Rect.intersects(allItemFrames.get(i),displayFrame)) {
                View viewForPosition = recycler.getViewForPosition(i);
                measureChildWithMargins(viewForPosition,0,0);
                addView(viewForPosition);
                //将这个item布局出来
                Rect rect = allItemFrames.get(i);
                layoutDecorated(viewForPosition,
                        rect.left,rect.top - mVerticalScrollOffset,rect.right,rect.bottom - mVerticalScrollOffset);
                Log.e("MyLog","====="+"布局：");
            }
        }
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    /**获取RecyclerView在垂直方向上的可用空间，即去除了padding后的高度*/
    private int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }
}
