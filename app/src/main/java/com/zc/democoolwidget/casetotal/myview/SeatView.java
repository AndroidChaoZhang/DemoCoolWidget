package com.zc.democoolwidget.casetotal.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

public class SeatView extends View {

    private Paint mPaint;
    private static final int SEAT_ROW = 100;//行
    private static final int SEAT_COL = 100;//列
    private static final int SEAT_LENGTH = 100;//座位的大小
    private Scroller scroller;//系统的快速滑动     基本上都使用这个
    private GestureDetector mGestureDetector;

    public SeatView(Context context) {
        super(context);
    }

    public SeatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
        //点击了触摸屏，但是没有移动和弹起的动作
// 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
        mGestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {//点击了触摸屏，但是没有移动和弹起的动作

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                int scrollY = getScrollY();
                int scrollX = getScrollX();
                float maxHeight = SEAT_ROW * SEAT_LENGTH;
                float maxWidth = SEAT_COL * SEAT_LENGTH;
                if (scrollY +distanceY <= 0) {
                    scrollBy(0, -scrollY);
                } else if (scrollX +distanceX <= 0) {
                    scrollBy(-scrollX, 0);
                } else if (scrollX+distanceX >= maxWidth) {
                    scrollBy(0, (int) (maxHeight - scrollY));
                } else if (scrollY+distanceY >= maxHeight) {
                    scrollBy((int) (maxWidth - scrollX), 0);
                } else {
                    scrollBy((int)distanceX, (int) distanceY);
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {// 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float distanceX, float distanceY) {
                scroller.startScroll(getScrollX(), getScrollY(), -(int)distanceX, -(int)distanceY);
                postInvalidate();
                return false;
            }
        });
        initPaint();
    }

    public SeatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(SEAT_ROW * SEAT_LENGTH,SEAT_COL * SEAT_LENGTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i <  SEAT_ROW; i++) {//行
            canvas.drawLine(0, i * SEAT_LENGTH, SEAT_COL * SEAT_LENGTH, i * SEAT_LENGTH, mPaint);
        }
        for (int i = 0; i <  SEAT_COL; i++) {//列
            canvas.drawLine(i * SEAT_LENGTH, 0, i * SEAT_LENGTH, SEAT_ROW * SEAT_LENGTH, mPaint);
        }
    }


    private void initPaint() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;

    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            int scrollY = getScrollY();
            int scrollX = getScrollX();
            float maxHeight = SEAT_ROW * SEAT_LENGTH;
            float maxWidth = SEAT_COL * SEAT_LENGTH;
            if (scrollY < 0) {
                scrollTo( scrollX, 0);
            } else if (scrollX < 0) {
                scrollTo( 0, scrollY);
            } else if (scrollY > maxHeight) {
                scrollTo( 0, (int)maxHeight);
            } else if (scrollX > maxWidth) {
                scrollTo( (int)maxWidth, 0);
            } else {
                scrollTo( scrollX, scrollY);
            }
            postInvalidate();
        }
    }
}
