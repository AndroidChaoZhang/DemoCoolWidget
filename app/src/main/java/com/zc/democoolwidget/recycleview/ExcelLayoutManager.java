package com.zc.democoolwidget.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class ExcelLayoutManager extends RecyclerView.LayoutManager {

    /**
     * 为每一个childView设置的LayoutParams在这个方法中返回。很简单，一般我们都直接返回一个WrapContent的lp
     * 这个方法就是RecyclerView Item的布局参数，若是想修改子Item的布局参数（比如：宽/高/margin/padding等等），那么可以在该方法内进行设置。*/
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
