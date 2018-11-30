package com.zc.democoolwidget.casetotal.customer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by NEU on 2018/4/9.
 * 圆变心
 */

public class BezierThreeView extends View {
    private static final float C = 0.551915024494f;     // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置
    private int centerX;
    private int centerY;
    private float[] mData = new float[8];               // 顺时针记录绘制圆形的四个数据点
    private float[] mCtrl = new float[16];              // 顺时针记录绘制圆形的八个控制点
    private float mCircleRadius = 200;                  // 圆的半径
    private float mDifference = mCircleRadius * C;        // 圆形的控制点与数据点的差值

    private float mDuration = 1000;                     // 变化总时长
    private float mCurrent = 0;                         // 当前已进行时长
    private float mCount = 100;                         // 将时长总共划分多少份
    private float mPiece = mDuration/mCount;            // 每一份的时长

    public BezierThreeView(Context context) {
        this(context,null);
    }

    public BezierThreeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化数据点  圆的四个角坐标
        mData[0] = 0;
        mData[1] = mCircleRadius;
        mData[2] = mCircleRadius;
        mData[3] = 0;
        mData[4] = 0;
        mData[5] = -mCircleRadius;
        mData[6] = -mCircleRadius;
        mData[7] = 0;

        // 初始化控制点
        mCtrl[0]  = mData[0]+mDifference;
        mCtrl[1]  = mData[1];
        mCtrl[2]  = mData[2];
        mCtrl[3]  = mData[3]+mDifference;
        mCtrl[4]  = mData[2];
        mCtrl[5]  = mData[3]-mDifference;
        mCtrl[6]  = mData[4]+mDifference;
        mCtrl[7]  = mData[5];
        mCtrl[8]  = mData[4]-mDifference;
        mCtrl[9]  = mData[5];
        mCtrl[10] = mData[6];
        mCtrl[11] = mData[7]-mDifference;
        mCtrl[12] = mData[6];
        mCtrl[13] = mData[7]+mDifference;
        mCtrl[14] = mData[0]-mDifference;
        mCtrl[15] = mData[1];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2;
        centerY = h/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(centerX, centerY); // 将坐标系移动到画布中央
        canvas.scale(1,-1);                 // 翻转Y轴

        Path path = new Path();
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(4);
        path.moveTo(mData[0],mData[1]);
        path.cubicTo(mCtrl[0],  mCtrl[1],  mCtrl[2],  mCtrl[3],     mData[2], mData[3]);
        path.cubicTo(mCtrl[4],  mCtrl[5],  mCtrl[6],  mCtrl[7],     mData[4], mData[5]);
        path.cubicTo(mCtrl[8],  mCtrl[9],  mCtrl[10], mCtrl[11],    mData[6], mData[7]);
        path.cubicTo(mCtrl[12], mCtrl[13], mCtrl[14], mCtrl[15],    mData[0], mData[1]);
        canvas.drawPath(path,paint);

        mCurrent += mPiece;
        if (mCurrent < mDuration){

            mData[1] -= 120/mCount;
            mCtrl[7] += 80/mCount;
            mCtrl[9] += 80/mCount;

            mCtrl[4] -= 20/mCount;
            mCtrl[10] += 20/mCount;

            postInvalidateDelayed((long) mPiece);
        }

    }
}
