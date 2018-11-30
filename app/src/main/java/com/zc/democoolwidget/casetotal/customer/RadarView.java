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
 * 雷达图(蜘蛛网图)绘制
 */

public class RadarView extends View {
    private int centerX ;//中心X
    private int centerY ;//中心Y
    private Paint mRadarPaint;//蜘蛛网paint
    private Paint mValuePaint;//蜘蛛网值paint
    private Paint mTextPaint;//蜘蛛网字paint
    private int count = 6;  //数据个数
    private float angle = (float) (Math.PI*2/count);//多边形弧度
    private float radius;                   //网格最大半径

    public RadarView(Context context) {
        this(context,null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    //1、获得布局中心
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(h, w) / 2*0.8f;//获取到手机宽高的最小值后的0.8倍
        //获取整个布局的中心坐标
        centerX = w/2;
        centerY = h/2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(centerX,centerY);
        drawPolygon(canvas);
        drawText(canvas);
        drawRegion(canvas);
    }

    private void initPaint () {
        mRadarPaint = new Paint();
        mRadarPaint.setColor(Color.GRAY);
        mRadarPaint.setStyle(Paint.Style.STROKE);
        mRadarPaint.setAntiAlias(true);

        mValuePaint = new Paint();
        mValuePaint.setColor(Color.BLUE);
        mValuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mValuePaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(20);
    }

    //2 绘制蜘蛛网络
    private void drawPolygon(Canvas canvas) {
        Path radarPath = new Path();
        Path linePath = new Path();
        float r = radius/(count-1);//蜘蛛丝之间的间距
        for (int i=0;i<count;i++) {
            float currentR = r * i ;//当前半径
            radarPath.reset();
            for (int j =0;j<count;j++) {
                float x = (float) (currentR * Math.sin(angle / 2 + angle * j));
                float y = (float) (currentR * Math.cos(angle / 2 + angle * j));
                if (j == 0) {
                    radarPath.moveTo(x, y);//移动下一次操作的起点位置
                } else {
                    radarPath.lineTo(x, y);//添加上一个点到当前点之间的直线到Path
                }
                if (i == count -1) {//当绘制最后一环时绘制连接线
                    linePath.moveTo(0,0);
                    linePath.lineTo(x,y);
                    canvas.drawPath(linePath,mRadarPaint);
                }
            }
            radarPath.close();//连接第一个点连接到最后一个点，形成一个闭合区域
            canvas.drawPath(radarPath,mRadarPaint);
        }
    }

    //3绘制文本
    private void drawText(Canvas canvas) {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < count; i++) {
            float x = (float) ((radius + textHeight * 2) * Math.sin(angle / 2 + angle * i));
            float y = (float) ((radius + textHeight * 2) * Math.cos(angle / 2 + angle * i));
            String title = "测试";
            float dis = mTextPaint.measureText(title);//文本长度
            canvas.drawText(title, x - dis / 2, y, mTextPaint);
        }
    }

    //4绘制覆盖区域
    private void drawRegion(Canvas canvas) {
        mValuePaint.setAlpha(255);
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            double percent = 0.55;
            float x = (float) (radius * Math.sin(angle / 2 + angle * i) * percent);
            float y = (float) (radius * Math.cos(angle / 2 + angle * i) * percent);
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            //绘制小圆点
            canvas.drawCircle(x, y, 5, mValuePaint);
        }
        path.close();
        mValuePaint.setAlpha(100);
        canvas.drawPath(path,mValuePaint);
    }
}
