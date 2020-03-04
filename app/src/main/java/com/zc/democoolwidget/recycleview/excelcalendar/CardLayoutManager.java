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
    /**这个方法就是RecyclerView Item的布局参数，若是想修改子Item的布局参数（比如：宽/高/margin/padding等等），那么可以在该方法内进行设置。
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {//这是一个必须重写的方法，这个方法是给RecyclerView的子View创建一个默认的LayoutParams
        return new RecyclerView.LayoutParams(itemWidth,itemWidth);
    }

    /** 重写这个函数来布局RecyclerView当前需要显示的item,确定每个item的位置
     * 1.进行布局之前，我们需要调用detachAndScrapAttachedViews方法把屏幕中的Items都分离出来，内部调整好位置和数据后，再把它添加回去(如果需要的话)；
     * 2.分离了之后，我们就要想办法把它们再添加回去了，所以需要通过addView方法来添加，那这些View在哪里得到呢？ 我们需要调用 Recycler的getViewForPosition(int position) 方法来获取；
     * 3.获取到Item并重新添加了之后，我们还需要对它进行测量，这时候可以调用measureChild或measureChildWithMargins方法，两者的区别我们已经了解过了，相信同学们都能根据需求选择更合适的方法；
     * 4.在测量完还需要做什么呢？ 没错，就是布局了，我们也是根据需求来决定使用layoutDecorated还是layoutDecoratedWithMargins方法；
     * 5.在自定义ViewGroup中，layout完就可以运行看效果了，但在LayoutManager还有一件非常重要的事情，就是回收了，我们在layout之后，还要把一些不再需要的Items回收，以保证滑动的流畅度；
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {//这个方法显然是用于放置子view的位置，十分重要的一个方法
        //getItemCount和getChildCount的区别：前者是adapter中添加的数据的数目，而后者是当前recyclerView中已经添加的子View的数目。
        if (getItemCount() == 0) {//没有Item，界面空着吧
            return;
        } else if (getChildCount() == 0 && state.isPreLayout()) {//state.isPreLayout()是支持动画的
            return;
        }
        //在布局之前，将所有的子view先detach掉，放入到Scrap中   detach轻量回收View
        detachAndScrapAttachedViews(recycler);
        //detachView(view);//超级轻量回收一个View,马上就要添加回来
        //detachAndScrapView(view, recycler);//detach轻量回收指定View
        //attachView(view);//将上个方法detach的View attach回来
        //recycler.recycleView(viewCache.valueAt(i));//detachView 后 没有attachView的话 就要真的回收掉他们
        // recycle真的回收一个View ，该View再次回来需要执行onBindViewHolder方法
//        removeAndRecycleView(View child, Recycler recycler);
//        removeAndRecycleAllViews(Recycler recycler);
/** * 根据Adapter的位置position找RecyclerView中对应的item * 我们不管它是从scrap里取，还是从RecyclerViewPool里取，亦或是onCreateViewHolder里拿 * 系统已经帮我们处理好了缓存了 */
        View first = recycler.getViewForPosition(0);// 根据position获取一个碎片view，可以从回收的view中获取，也可能新构造一个
        measureChildWithMargins(first, 0, 0);// 计算此碎片view包含边距的尺寸，测量View,这个方法会考虑到View的ItemDecoration以及Margin
        int itemWidth = getDecoratedMeasuredWidth(first);// 获取此碎片view包含边距和装饰的宽度width
        int itemHeight = itemWidth;

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
            if (Rect.intersects(displayRect, frame)) {//判断a,b两矩形是否相交 //Item没有在显示区域，就说明需要回收
                View scrap = recycler.getViewForPosition(i);
                addView(scrap);
                measureChildWithMargins(scrap, 0, 0);
                // Important！将ViewLayout出来，显示在屏幕上，布局到RecyclerView容器中，所有的计算都是为了得出任意position的item的边界来布局
                layoutDecorated(scrap, frame.left - mHorizontalOffset, frame.top - mVerticalOffset,
                        frame.right - mHorizontalOffset, frame.bottom - mVerticalOffset);
                /**
                 * 将item　layout布局出来，显示在屏幕上，内部会自动追加上该View的ItemDecoration和Margin
                 * 这里就需要自己去控制显示的位置了
                 */
                //layoutDecoratedWithMargins(scrap, 0, 0, 0, 0);
            }
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {//dy>0:向上滚动  dy<0:向下滚动  如果这个值小于传入的坐标，表明我们已经滑动到了尽头
        detachAndScrapAttachedViews(recycler);
        if (mVerticalOffset + dy < 0) {
            dy = -mVerticalOffset;
        } else if (mVerticalOffset + dy > mTotalHeight - getVerticalSpace()) {
            dy = mTotalHeight - getVerticalSpace() - mVerticalOffset;
        }

        offsetChildrenVertical(-dy);//垂直移动RecyclerView内所有的item
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
    /**获取RecyclerView在垂直方向上的可用空间，即去除了padding后的高度*/
    private int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }
}
