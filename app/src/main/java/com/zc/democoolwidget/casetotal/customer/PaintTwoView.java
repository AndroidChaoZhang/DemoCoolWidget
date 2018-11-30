package com.zc.democoolwidget.casetotal.customer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by NEU on 2018/3/15.
 * http://www.gcssloop.com/customview/Canvas_Convert
 * 安卓自定义View进阶-Canvas之画布操作 二
 */

public class PaintTwoView extends View {

    private int viewHeight = 0;
    public PaintTwoView(Context context) {
        super(context);
    }

    public PaintTwoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);//填充
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);//抗锯齿

        canvas.drawCircle(100,100,50,mPaint);// 绘制一个圆心坐标在(100,100)，半径为50 的圆。

        //1.位移(translate)位移是基于当前位置移动，而不是每次基于屏幕左上角的(0,0)点移动
        mPaint.setColor(Color.BLACK);
        canvas.translate(200,0);//坐标原点（200,0）
        canvas.drawCircle(100,100,50,mPaint);// 绘制一个圆心坐标在(100,100)，半径为50 的圆。

        //2.缩放(scale)缩放的中心默认为坐标原点,而缩放中心轴就是坐标轴   缩放也是可以叠加的。
        canvas.translate(-150,200);//坐标原点（50,200）
        mPaint.setStyle(Paint.Style.STROKE);//描边
        RectF rectF = new RectF(0, 0, 150, 100);
        canvas.drawRect(rectF,mPaint);
        canvas.scale(0.5f,0.5f);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(rectF,mPaint);
        canvas.scale(1.0f,-1.0f);//☆当缩放比例为负数的时候会根据缩放中心轴进行翻转
        mPaint.setColor(Color.RED);
        canvas.drawRect(rectF,mPaint);
        //2.1缩放(scale)缩放的中心默认为坐标原点,而缩放中心轴就是坐标轴
        canvas.scale(2f,-2f);
        canvas.translate(200,0);//坐标原点（250,200）
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(rectF,mPaint);
        canvas.scale(0.5f,0.5f,75,50);// 画布缩放 先位移，后缩放（缩放中心） <-- 缩放中心向右偏移了75个单位向下偏移50个单位
        mPaint.setColor(Color.RED);
        canvas.drawRect(rectF,mPaint);
        //2.2缩放(scale)当缩放比例为负数的时候会根据缩放中心轴进行翻转
        canvas.scale(2f,2f);
        canvas.translate(150,-25);//坐标原点（450,200）
        mPaint.setColor(Color.BLACK);
        for (int i =0;i<=20;i++) {
            canvas.scale(0.9f,0.9f);
            canvas.drawRect(rectF,mPaint);
        }


        //3.旋转(rotate)
        canvas.scale(8f,8f);
        canvas.translate(-400,200);
        canvas.drawRect(rectF,mPaint);
        canvas.rotate(180);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(rectF,mPaint);
        canvas.rotate(60);
        mPaint.setColor(Color.RED);
        canvas.drawRect(rectF,mPaint);
        canvas.rotate(30,100,0);// 旋转30度 <-- 旋转中心向右偏移100个单位
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(rectF,mPaint);

        //4.错切(skew)float sx:将画布在x方向上倾斜相应的角度，sx倾斜角度的tan值,float sy:将画布在y轴方向上倾斜相应的角度，sy为倾斜角度的tan值.
        canvas.translate(-100,300);
        canvas.rotate(90);
        canvas.drawRect(rectF,mPaint);
//        canvas.skew(1,0);// 水平错切 <-- 45度
        canvas.skew(0,1);                       // 垂直错切
        mPaint.setColor(Color.RED);
        canvas.drawRect(rectF,mPaint);

        //5.快照(save)和回滚(restore)
    }
}
