
package com.zc.democoolwidget.springindicator.library.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;

import java.lang.reflect.Field;

public class ScrollerViewPager extends ViewPager {

    public ScrollerViewPager(Context context) {
        super(context);
    }

    public ScrollerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void fixScrollSpeed(int duration){
        setScrollSpeedUsingRefection(duration);
    }


    private void setScrollSpeedUsingRefection(int duration) {
        try {
            Field localField = ViewPager.class.getDeclaredField("mScroller");
            localField.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(getContext(), new DecelerateInterpolator(1.5F));
            scroller.setDuration(duration);
            localField.set(this, scroller);
            return;
        } catch (IllegalAccessException localIllegalAccessException) {
        } catch (IllegalArgumentException localIllegalArgumentException) {
        } catch (NoSuchFieldException localNoSuchFieldException) {
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
