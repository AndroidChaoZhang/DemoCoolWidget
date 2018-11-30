package com.zc.democoolwidget.recycleview.excelcalendar;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by NEU on 2018/1/15.
 */

public class CardLayoutManager extends RecyclerView.LayoutManager {

    private int mGroupSize;
    private int mHorizontalOffset;
    private int mVerticalOffset;
    private int mTotalWidth;
    private int mTotalHeight;
    private int itemWidth;
    private Pool<Rect> mItemFrames;

    public CardLayoutManager(int groupSize,int itemWidth) {
        mGroupSize = groupSize;
        this.itemWidth = itemWidth;
        mItemFrames = new Pool<>(new Pool.New<Rect>() {
            @Override
            public Rect get() { return new Rect();}
        });
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {//这是一个必须重写的方法，这个方法是给RecyclerView的子View创建一个默认的LayoutParams
        return new RecyclerView.LayoutParams(itemWidth,itemWidth);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {//这个方法显然是用于放置子view的位置，十分重要的一个方法
        if (getItemCount() <= 0 || state.isPreLayout()) { return;}

        detachAndScrapAttachedViews(recycler);// 分离所有的itemView，detach轻量回收所有View
        //detachView(view);//超级轻量回收一个View,马上就要添加回来
        //detachAndScrapView(view, recycler);//detach轻量回收指定View
        //attachView(view);//将上个方法detach的View attach回来
        //recycler.recycleView(viewCache.valueAt(i));//detachView 后 没有attachView的话 就要真的回收掉他们
        // recycle真的回收一个View ，该View再次回来需要执行onBindViewHolder方法
//        removeAndRecycleView(View child, Recycler recycler);
//        removeAndRecycleAllViews(Recycler recycler);

        View first = recycler.getViewForPosition(0);// 根据position获取一个碎片view，可以从回收的view中获取，也可能新构造一个
        measureChildWithMargins(first, 0, 0);// 计算此碎片view包含边距的尺寸，测量View,这个方法会考虑到View的ItemDecoration以及Margin
        int itemWidth = getDecoratedMeasuredWidth(first);// 获取此碎片view包含边距和装饰的宽度width
        int itemHeight = getDecoratedMeasuredHeight(first);

        for (int i = 0; i < getItemCount(); i++) {
            Rect item = mItemFrames.get(i);
            int offsetHeight = (int) ((i / mGroupSize) * itemHeight );

            int offsetInLine = i < mGroupSize ? i : i % mGroupSize;
            item.set( offsetInLine * itemWidth, offsetHeight, offsetInLine * itemWidth + itemWidth,
                    itemHeight + offsetHeight);
        }

        mTotalWidth = mGroupSize * itemWidth;
        mTotalHeight = mGroupSize * itemHeight;
        fill(recycler, state);
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0 || state.isPreLayout()) { return;}
        Rect displayRect = new Rect(mHorizontalOffset, mVerticalOffset,
                getHorizontalSpace() + mHorizontalOffset,
                getVerticalSpace() + mVerticalOffset);

        for (int i = 0; i < getItemCount(); i++) {
            Rect frame = mItemFrames.get(i);
            if (Rect.intersects(displayRect, frame)) {
                View scrap = recycler.getViewForPosition(i);
                addView(scrap);
                measureChildWithMargins(scrap, 0, 0);
                layoutDecorated(scrap, frame.left - mHorizontalOffset, frame.top - mVerticalOffset,
                        frame.right - mHorizontalOffset, frame.bottom - mVerticalOffset);// Important！将ViewLayout出来，显示在屏幕上，布局到RecyclerView容器中，所有的计算都是为了得出任意position的item的边界来布局
            }
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        if (mVerticalOffset + dy < 0) {
            dy = -mVerticalOffset;
        } else if (mVerticalOffset + dy > mTotalHeight - getVerticalSpace()) {
            dy = mTotalHeight - getVerticalSpace() - mVerticalOffset;
        }

        offsetChildrenVertical(-dy);
        mVerticalOffset += dy;
        fill(recycler, state);
        return dy;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        if (mHorizontalOffset + dx < 0) {
            dx = -mHorizontalOffset;
        } else if (mHorizontalOffset + dx > mTotalWidth - getHorizontalSpace()) {
            dx = mTotalWidth - getHorizontalSpace() - mHorizontalOffset;
        }

        offsetChildrenHorizontal(-dx);//水平平移容器内的item  取反  往左滑动的时候是正值
        mHorizontalOffset += dx;
        fill(recycler, state);
        return dx;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }
}
