package com.zc.democoolwidget.recycleview.excelcalendar;

import android.support.v4.util.SparseArrayCompat;

/**
 * Created by NEU on 2018/1/15.
 */

public class Pool<T> {
    private SparseArrayCompat<T> mPool;
    private New<T> mNewInstance;

    public Pool(New<T> newInstance) {
        mPool = new SparseArrayCompat<>();
        mNewInstance = newInstance;
    }

    public T get(int key) {
        T res = mPool.get(key);
        if (res == null) {
            res = mNewInstance.get();
            mPool.put(key, res);
        }
        return res;
    }

    public interface New<T> {
        T get();
    }
}
