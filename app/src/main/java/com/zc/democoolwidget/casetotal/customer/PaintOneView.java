package com.zc.democoolwidget.casetotal.customer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by NEU on 2018/3/15.
 * 自定义View进阶-Canvas之绘制图形 1
 * http://www.gcssloop.com/customview/Canvas_BasicGraphics
 */

public class PaintOneView extends View {

    public PaintOneView(Context context) {
        this(context,null);
    }
    // 2.在构造函数中初始化
    public PaintOneView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawPaint(mPaint);
        Paint mPaint = new Paint();
        mPaint.setColor(Color.BLACK);//设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);//设置画笔模式为填充
        mPaint.setStrokeWidth(10);//设置画笔宽度为10px
        mPaint.setAntiAlias(true);//抗锯齿

        //1. 画点
        canvas.drawPoint(50,50,mPaint);
        //1.2.画一组点
        mPaint.setColor(Color.RED);
        canvas.drawPoints(new float[]{100,50,150,50,200,50},mPaint);

        //2.画一条直线
        canvas.drawLine(250,50,350,50,mPaint);
        //2.1.画一组直线
        mPaint.setColor(Color.BLACK);//设置画笔颜色
        canvas.drawLines(new float[]{400,50,450,50,500,50,600,50,},mPaint);

        mPaint.setStrokeWidth(5);//设置画笔宽度为10px

        //3.画矩形
        canvas.drawRect(50,100,200,200,mPaint);//对角线的两个点的坐标值，这里一般采用左上角和右下角的两个点的坐标 ☆坐标不要重复，不然不显示
        //3.1.画矩形
        canvas.drawRect(new Rect(250,100,350,200),mPaint);
        //3.2.画矩形
        canvas.drawRect(new RectF(400,100,550,200),mPaint);//Rect是int(整形)的，而RectF是float(单精度浮点型)的

        //4.画圆角矩形
        canvas.drawRoundRect(50,250,350,350,30,30,mPaint);
        //4.1.画圆角矩形
        canvas.drawRoundRect(new RectF(450,250,550,350),30,30,mPaint);
        //4.1.1.圆角矩形 rx ry 大于宽高一半
        mPaint.setColor(Color.RED);
        canvas.drawRoundRect(new RectF(450,250,550,350),100,100,mPaint);//rx大于宽度的一半，ry大于高度的一半时，实际上是无法计算出圆弧的，所以drawRoundRect对大于该数值的参数进行了限制(修正)，凡是大于一半的参数均按照一半来处理

        //5.画椭圆
        canvas.drawOval(50,400,350,450,mPaint);
        //5.1.画椭圆
        canvas.drawOval(new RectF(450,400,550,450),mPaint);

        //6.画圆
        canvas.drawCircle(100,500,50,mPaint);// 绘制一个圆心坐标在(100,500)，半径为50 的圆。
        mPaint.setStyle(Paint.Style.STROKE);//描边
        canvas.drawCircle(250,500,50,mPaint);// 绘制一个圆心坐标在(250,500)，半径为50 的圆。
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);//描边加填充
        canvas.drawCircle(400,500,50,mPaint);// 绘制一个圆心坐标在(400,500)，半径为50 的圆。

        //7.画圆弧 startAngle开始角度 sweepAngle扫过角度 useCenter是否使用中心
        canvas.drawRect(new RectF(50,600,300,700),mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(50,600,300,700,0,90,true,mPaint);
        //7.1.画圆弧 startAngle开始角度 sweepAngle扫过的角度 useCenter是否使用中心
        canvas.drawRect(new RectF(310,600,560,700),mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawArc(new RectF(310,600,560,700),0,90,false,mPaint);//不使用中心点则是圆弧起始点和结束点之间的连线加上圆弧围成的图形


        //练习  画圆饼  个个百分比
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        canvas.drawCircle(250,1000,200,mPaint);
        RectF rectF = new RectF(50, 800, 450, 1200);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(rectF,-60,180,true,mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawArc(rectF,210,60,true,mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF,270,20,true,mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawArc(rectF,290,40,true,mPaint);
    }
}
