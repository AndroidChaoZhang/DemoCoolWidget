package com.zc.democoolwidget.recycleview.simple;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class CustomLayoutManager extends RecyclerView.LayoutManager {


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    //用来存取每一个itemView的位置信息
    private SparseArray<Rect> itemRects = null;
    private int mTotalHeight = -1;//总的itemView的总高度
    private boolean oneFlag = true;//看是否是第一次加载
    private int mTheMoveDistance = 0;//实际的位移

    /**
     * 判断是符合条件
     *
     * @param state
     * @return
     */
    private boolean cut(RecyclerView.State state) {
        if (oneFlag) {
            oneFlag = false;
        }

        if (getItemCount() <= 0 || state.isPreLayout()) {
            return true;
        }
        return false;
    }
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        if (cut(state)) return;
        detachAndScrapAttachedViews(recycler);
        //1.获取一个item高度
        int itemHeight = initStepHeight(recycler);
        //2.获取可见高度的中总的item数，也就是getchildCount（）
        int visibleCount = initVisibleCounts(itemHeight);
        //3.计算每个item的位置信息
        initItemRectSparse(itemHeight);
        //4把可见的item放在现实出来
        layoutChilder(recycler, visibleCount);
    }
    //获取可见高度
    public int getVerticalVisibleHeight() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }
    //计算可见区域的rect
    public Rect getVisiblArea() {
        return new Rect(getPaddingLeft(),
                getPaddingTop() + mTheMoveDistance,
                getWidth() + getPaddingRight(),
                getVerticalVisibleHeight() + mTheMoveDistance);
    }
    //计算一个item的高度这里是高度不变的情况
    public int initStepHeight(RecyclerView.Recycler recycler) {
        View childView = recycler.getViewForPosition(0);
        addView(childView);
        measureChildWithMargins(childView, 0, 0);
        int result = getDecoratedMeasuredHeight(childView);
        removeAndRecycleAllViews(recycler);
        return result;
    }
    //计算可见区域item的个数
    public int initVisibleCounts(int steHeight) {
        return getVerticalVisibleHeight() / steHeight;
    }
    /**
     * 计算每个条目位置信息
     *
     * @param stepItemHeight 一个item的高度
     */
    public void initItemRectSparse(int stepItemHeight) {
        itemRects = new SparseArray<>();
        int offfsetY = getPaddingTop();
        for (int i = 0; i < getItemCount(); i++) {
            itemRects.put(i, new Rect(getPaddingLeft(), offfsetY, getWidth() + getPaddingRight(), offfsetY + stepItemHeight));
            offfsetY += stepItemHeight;
            mTotalHeight = offfsetY;
        }
    }
    //显示可见item
    public void layoutChilder(RecyclerView.Recycler recycler, int visibleCount) {
        detachAndScrapAttachedViews(recycler);
        for (int i = 0; i < visibleCount; i++) {
            View childView = recycler.getViewForPosition(i);
            addView(childView);
            measureChildWithMargins(childView, 0, 0);
            layoutDecorated(childView, itemRects.get(i).left, itemRects.get(i).top, itemRects.get(i).right, itemRects.get(i).bottom);
        }
    }
    @Override
    public boolean canScrollVertically() {
        return true;
    }
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        dy = handleScroll(dy, recycler, state);
        handleRecycle(recycler,state);
        return dy;
    }
    public int handleScroll(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //获取可见高度
        int theRvVisibleHeight = getVerticalVisibleHeight();
        //获取不可见的总高度
        int theMoreHeight = mTotalHeight - theRvVisibleHeight;
        if (mTheMoveDistance + dy < 0) {//到达顶点
            //修复边界
            dy = -mTheMoveDistance;
        } else if (mTotalHeight > theRvVisibleHeight && mTheMoveDistance + dy > theMoreHeight) {
            dy = theMoreHeight - mTheMoveDistance;
        }
        mTheMoveDistance += dy;
        offsetChildrenVertical(-dy);
        return dy;
    }

    //处理回收问题
    private void handleRecycle(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (cut(state))return;
        detachAndScrapAttachedViews(recycler);
        //1获取可见区域的rect
        Rect visiblArea = getVisiblArea();
        //2获取已经存取过的所有item位置信息的itemRect
        for (int i = 0; i < getItemCount(); i++) {
            View childView = recycler.getViewForPosition(i);
            //比较当前view是否在可见区域 不在回收在显示
            Rect rect = itemRects.get(i);
            if (Rect.intersects(visiblArea,rect)){
                addView(childView);
                measureChildWithMargins(childView,0,0);
                //让不见变为可见就要减掉当前的位移也就是 mTheMoveDistance 否则显示不出来
                layoutDecorated(childView,rect.left,rect.top-mTheMoveDistance,rect.right,rect.bottom-mTheMoveDistance);
            }else{//不可见回收
                removeAndRecycleView(childView,recycler);
            }
        }
    }
}