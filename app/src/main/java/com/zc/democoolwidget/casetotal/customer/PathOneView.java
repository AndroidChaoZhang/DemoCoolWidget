package com.zc.democoolwidget.casetotal.customer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by NEU on 2018/4/4.
 * http://www.gcssloop.com/customview/Path_Basic/
 * 安卓自定义View进阶 - Path之基本操作
 */

public class PathOneView extends View {

    public PathOneView(Context context) {
        this(context,null);
    }

    public PathOneView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //第1组: moveTo、 setLastPoint、 lineTo 和 close
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        Path path = new Path();
        path.lineTo(100,100);
        path.lineTo(100,200);

        //moveTo移动下一次操作的起点位置   setLastPoint设置之前操作的最后一个点位置  区别
        path.moveTo(200,200);
        path.lineTo(300,200);

        path.setLastPoint(250,300);
        path.lineTo(300,300);
        //close方法用于连接当前最后一个点和最初的一个点(如果两个点不重合的话)，最终形成一个封闭的图形
        //注意：close的作用是封闭路径，与连接当前最后一个点和第一个点并不等价。如果连接了最后一个点和第一个点仍然无法形成封闭图形，则close什么也不做
        path.close();


        //第2组: addXxx与arcTo
        //第一类(基本形状)
        path.addRect(200,500,300,600, Path.Direction.CW);//顺时针
        path.setLastPoint(150,550);
        path.addRect(500,500,600,600, Path.Direction.CCW);//逆时针
        path.setLastPoint(450,550);
        //第二类(Path)
        Path pathSrc = new Path();
        path.addRect(200,800,400,900, Path.Direction.CW);
        pathSrc.addCircle(300,700,50, Path.Direction.CW);
        path.addPath(pathSrc,0,100);//将src进行了位移之后再添加进当前path中
        //第三类(addArc与arcTo) addArc 添加一个圆弧到path，arcTo 如果圆弧的起点和上次最后一个坐标点不相同，就连接两个点
        //arcTo (RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo) forceMoveTo为false,等价于arcTo 不移动，而是连接最后一个点与圆弧起点
//        path.addArc(new RectF(500,650,700,850),0,270);
//        path.arcTo(new RectF(500,650,700,850),0,270);
        path.arcTo(new RectF(500,650,700,850),0,270,true);
        //第3组：isEmpty、 isRect、isConvex、 set 和 offset-->将当前path平移后的状态存入dst中，不会影响当前path
        path.addCircle(200,1000,100, Path.Direction.CW);
        Path pathDst = new Path();
        pathDst.addRect(new RectF(200,1000,300,1100), Path.Direction.CW);
        path.offset(200,0,pathDst);
        canvas.drawPath(path,paint);
        paint.setColor(Color.BLUE);
        canvas.drawPath(pathDst,paint);
    }
}
