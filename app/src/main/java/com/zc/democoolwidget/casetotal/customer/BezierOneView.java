package com.zc.democoolwidget.casetotal.customer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by NEU on 2018/4/9.
 * quadTo, cubicTo 分别为二次和三次贝塞尔曲线的方法
 * 安卓自定义View进阶-Path之贝塞尔曲线http://www.gcssloop.com/customview/Path_Bezier
 */

public class BezierOneView extends View {
    private int centerX;
    private int centerY;
    private PointF controlPoint;

    public BezierOneView(Context context) {
        this(context,null);
    }

    public BezierOneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        controlPoint = new PointF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.e("MyLog","-----------onSizeChanged---------");
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2;
        centerY = h/2;

        controlPoint.x = centerX;
        controlPoint.y = centerY-100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("MyLog","-----------onDraw---------");
        super.onDraw(canvas);
        Paint paint = new Paint();
        // 绘制数据点和控制点
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        canvas.drawPoint(centerX -200,centerY,paint);
        canvas.drawPoint(centerX +200,centerY,paint);
        // 绘制辅助线
        paint.setStrokeWidth(4);
        canvas.drawLine(centerX -200,centerY,controlPoint.x,controlPoint.y,paint);
        canvas.drawLine(centerX +200,centerY,controlPoint.x,controlPoint.y,paint);
        // 绘制贝塞尔曲线
        paint.setStrokeWidth(6);
        paint.setColor(Color.RED);
        Path path = new Path();
        path.moveTo(centerX -200,centerY);
        path.quadTo(controlPoint.x,controlPoint.y,centerX +200,centerY);
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘
        controlPoint.x = event.getX();
        controlPoint.y = event.getY();
        invalidate();
        return true;

    }
}
