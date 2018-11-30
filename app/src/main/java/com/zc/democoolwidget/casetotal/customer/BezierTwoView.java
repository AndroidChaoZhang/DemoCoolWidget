package com.zc.democoolwidget.casetotal.customer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by NEU on 2018/4/9.
 * quadTo, cubicTo 分别为二次和三次贝塞尔曲线的方法
 * 安卓自定义View进阶-Path之贝塞尔曲线http://www.gcssloop.com/customview/Path_Bezier
 */

public class BezierTwoView extends View {
    private int centerX;
    private int centerY;
    private PointF controlPointOne,controlPointTwo;

    public BezierTwoView(Context context) {
        this(context,null);
    }

    public BezierTwoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        controlPointOne = new PointF();
        controlPointTwo = new PointF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2;
        centerY = h/2;

        controlPointOne.x = centerX-200;
        controlPointOne.y = centerY -100;
        controlPointTwo.x = centerX+200;
        controlPointTwo.y = centerY -100;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        // 绘制数据点和控制点
        canvas.drawPoint(centerX-200,centerY,paint);
        canvas.drawPoint(centerX+200,centerY,paint);
        // 绘制辅助线
        paint.setStrokeWidth(4);
        canvas.drawLine(centerX-200,centerY, controlPointOne.x, controlPointOne.y,paint);
        canvas.drawLine(controlPointTwo.x,controlPointTwo.y, controlPointOne.x, controlPointOne.y,paint);
        canvas.drawLine(centerX+200,centerY, controlPointTwo.x, controlPointTwo.y,paint);
        // 绘制贝塞尔曲线
        paint.setStrokeWidth(8);
        paint.setColor(Color.RED);
        Path path = new Path();
        path.moveTo(centerX-200,centerY);
        path.cubicTo(controlPointOne.x,controlPointOne.y,controlPointTwo.x,controlPointTwo.y,centerX+200,centerY);
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controlPointOne.x = event.getX();
        controlPointOne.y = event.getY();
        invalidate();
        return true;
    }
}
