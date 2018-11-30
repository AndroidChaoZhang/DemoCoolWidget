package com.zc.democoolwidget.casetotal.customer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by NEU on 2018/4/10.
 * http://www.gcssloop.com/customview/Path_Over
 * 安卓自定义View进阶-Path之完结篇
 */

public class PathOverView extends View {
    private int centerX,centerY;
    public PathOverView(Context context) {
        this(context,null);
    }

    public PathOverView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2;
        centerY = h/2;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        Path path = new Path();
        path.moveTo(100,100);
//        path.lineTo(100,200);
        path.rLineTo(100,200);//不带r的方法是基于原点的坐标系(偏移量)， rXxx方法是基于当前点坐标系(偏移量)
//        path.setFillType(Path.FillType.EVEN_ODD);//// 奇偶规则
//        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);//// 反奇偶规则
        path.addCircle(100,100,50, Path.Direction.CW);
        path.addCircle(100,100,100, Path.Direction.CCW);
        path.setFillType(Path.FillType.WINDING);
        canvas.drawPath(path,paint);

        Path pathOne = new Path();
        Path pathTwo = new Path();
        Path pathOpResult = new Path();
        pathOne.addCircle(400,100,40, Path.Direction.CW);
        pathTwo.addCircle(450,100,40, Path.Direction.CW);
        //DIFFERENCE差集 Path1中减去Path2后剩下的部分
        pathOpResult.op(pathOne,pathTwo, Path.Op.DIFFERENCE);
        canvas.drawPath(pathOpResult,paint);
        canvas.drawText("DIFFERENCE",500,100,paint);
        //REVERSE_DIFFERENCE差集 Path2中减去Path1后剩下的部分
        pathOne.reset();
        pathTwo.reset();
        pathOne.addCircle(400,200,40, Path.Direction.CW);
        pathTwo.addCircle(450,200,40, Path.Direction.CW);
        pathOpResult.op(pathOne,pathTwo, Path.Op.REVERSE_DIFFERENCE);
        canvas.drawPath(pathOpResult,paint);
        canvas.drawText("REVERSE_DIFFERENCE",500,200,paint);
        //INTERSECT交集 Path1与Path2相交的部分
        pathOne.reset();
        pathTwo.reset();
        pathOne.addCircle(400,300,40, Path.Direction.CW);
        pathTwo.addCircle(450,300,40, Path.Direction.CW);
        pathOpResult.op(pathOne,pathTwo, Path.Op.INTERSECT);
        canvas.drawPath(pathOpResult,paint);
        canvas.drawText("INTERSECT",500,300,paint);
        //UNION并集 包含全部Path1和Path2
        pathOne.reset();
        pathTwo.reset();
        pathOne.addCircle(400,400,40, Path.Direction.CW);
        pathTwo.addCircle(450,400,40, Path.Direction.CW);
        pathOpResult.op(pathOne,pathTwo, Path.Op.UNION);
        canvas.drawPath(pathOpResult,paint);
        canvas.drawText("UNION",500,400,paint);
        //XOR异或 包含Path1与Path2但不包括两者相交的部分
        pathOne.reset();
        pathTwo.reset();
        pathOne.addCircle(400,500,40, Path.Direction.CW);
        pathTwo.addCircle(450,500,40, Path.Direction.CW);
        pathOpResult.op(pathOne,pathTwo, Path.Op.XOR);
        canvas.drawPath(pathOpResult,paint);
        canvas.drawText("XOR",500,500,paint);

        RectF rectF = new RectF();
        //布尔操作(API19)
        canvas.translate(centerX,centerY+300);
        Path path1 = new Path();
        Path path2 = new Path();
        Path path3 = new Path();
        Path path4 = new Path();
        path1.addCircle(0, 0, 200, Path.Direction.CW);
        path2.addRect(0, -200, 200, 200, Path.Direction.CW);
        path3.addCircle(0, -100, 100, Path.Direction.CW);
        path4.addCircle(0, 100, 100, Path.Direction.CCW);

        path1.op(path2, Path.Op.DIFFERENCE);
        path1.op(path3, Path.Op.UNION);
        path1.op(path4, Path.Op.DIFFERENCE);

        path1.computeBounds(rectF,true);
        canvas.drawPath(path1,paint);
        //computeBounds(RectF bounds, boolean exact) 计算Path所占用的空间以及所在位置  exact是否精确测量，目前这一个参数作用已经废弃，一般写true即可。
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectF,paint);// 绘制边界
        //重置路径  是否保留FillType设置  是否保留原有数据结构
        //reset            是                   否
        //rewind           否                   是

    }
}
