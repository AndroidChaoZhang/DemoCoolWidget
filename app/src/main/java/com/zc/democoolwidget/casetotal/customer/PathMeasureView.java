package com.zc.democoolwidget.casetotal.customer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zc.democoolwidget.R;

/**
 * Created by NEU on 2018/4/10.
 * 安卓自定义View进阶-PathMeasure
 * http://www.gcssloop.com/customview/Path_PathMeasure
 */

public class PathMeasureView extends View {

    private Bitmap mBitmap;
    private float currentValue = 0;     // 用于纪录当前的位置,取值范围[0,1]映射Path的整个长度
    private float[] pos = new float[2];                // 当前点的实际位置
    private float[] tan= new float[2];                // 当前点的tangent值,用于计算图片所需旋转的角度
    private Matrix mMatrix = new Matrix();             // 矩阵,用于对图片进行一些操作

    public PathMeasureView(Context context) {
        this(context,null);
    }

    public PathMeasureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_arrow,options);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        Path path = new Path();
        path.moveTo(100,100);
        path.lineTo(100,200);
        path.lineTo(200,200);
        path.lineTo(300,200);
        //PathMeasure   getLength 用于获取 Path 的总长度
        //1. 不论 forceClosed 设置为何种状态(true 或者 false)， 都不会影响原有Path的状态，即 Path 与 PathMeasure 关联之后，之前的的 Path 不会有任何改变。
        //2. forceClosed 的设置状态可能会影响测量结果，如果 Path 未闭合但在与 PathMeasure 关联的时候设置 forceClosed 为 true 时，测量结果可能会比 Path 实际长度稍长一点，获取到到是该 Path 闭合时的状态。
        PathMeasure pathMeasure = new PathMeasure(path, false);
        PathMeasure pathMeasure1 = new PathMeasure(path, true);

        Log.e("MyLog","==============="+pathMeasure.getLength());
        Log.e("MyLog","=========pathMeasure1======"+pathMeasure1.getLength());

        //getSegment(float startD, float stopD, Path dst, boolean startWithMoveTo) 用于获取Path的一个片段
        //startD 开始截取位置距离 Path 起点的长度 取值范围: 0 <= startD < stopD <= Path总长度
        //stopD  结束截取位置距离 Path 起点的长度 取值范围: 取值范围: 0 <= startD < stopD <= Path总长度
        //dst  截取的 Path 将会添加到 dst 中 ☆注意: 是添加，而不是替换
        //startWithMoveTo 起始点是否使用 moveTo 用于保证截取的 Path 第一个点位置不变

        Path pathTwo = new Path();
        pathTwo.addRect(300,300,400,400, Path.Direction.CW);
        Path pathDst = new Path();
        pathDst.moveTo(250,250);
        pathDst.lineTo(250,300);

        PathMeasure pathMeasure2 = new PathMeasure(pathTwo, false);
//        pathMeasure2.getSegment(100,300,pathDst,true);
        pathMeasure2.getSegment(100,300,pathDst,false);//// <--- 截取一部分 不使用 startMoveTo, 保持 dst 的连续性
        //从该示例我们又可以得到一条结论：如果 startWithMoveTo 为 true, 则被截取出来到Path片段保持原状，
        // 如果 startWithMoveTo 为 false，则会将截取出来的 Path 片段的起始点移动到 dst 的最后一个点，以保证 dst 的连续性。
        canvas.drawPath(pathDst,paint);


        //nextContour 用于跳转到下一条曲线到方法，如果跳转成功，则返回 true， 如果跳转失败，则返回 false。
        Path pathCircle = new Path();
        pathCircle.addRect(200,450,300,550, Path.Direction.CW);
        pathCircle.addRect(400,450,600,650, Path.Direction.CW);
        canvas.drawPath(pathCircle,paint);

        PathMeasure pathMeasure3 = new PathMeasure(pathCircle,false);
        float length = pathMeasure3.getLength();
        pathMeasure3.nextContour();
        float length2 = pathMeasure3.getLength();
        Log.e("MyLog","=============length=="+length);
        Log.e("MyLog","=========length2======"+length2);
        //1.曲线的顺序与 Path 中添加的顺序有关。
        //2.getLength 获取到到是当前一条曲线分长度，而不是整个 Path 的长度。
        //3.getLength 等方法是针对当前的曲线(其它方法请自行验证)。

        // 5.getPosTan(float distance, float[] pos, float[] tan) 用于得到路径上某一长度的位置以及该位置的正切值  例子：小箭头旋转
        // distance 距离 Path 起点的长度 取值范围: 0 <= distance <= getLength
        // pos 该点的坐标值 当前点在画布上的位置，有两个数值，分别为x，y坐标
        // tan 该点的正切值 当前点在曲线上的方向，使用 Math.atan2(tan[1], tan[0]) 获取到正切角的弧度值。
        Path arrowPath = new Path();
        arrowPath.addCircle(200,800,100, Path.Direction.CW);
        currentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
        if (currentValue >= 1) {
            currentValue = 0;
        }
        PathMeasure pathMeasure4 = new PathMeasure(arrowPath, false);
        //6.getMatrix(float distance, Matrix matrix, int flags)
        // distance 距离 Path 起点的长度 取值范围: 0 <= distance <= getLength
        // matrix  根据 falgs 封装好的matrix 会根据 flags 的设置而存入不同的内容
        // flags  规定哪些内容会存入到matrix中 可选择 POSITION_MATRIX_FLAG(位置) ANGENT_MATRIX_FLAG(正切)
        pathMeasure4.getMatrix(pathMeasure4.getLength()*currentValue,mMatrix,PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);   // <-- 将图片绘制中心调整到与当前点重合(注意:此处是前乘pre)
        //等价于 下面的 start
//        mMatrix.reset();// 重置Matrix
//        pathMeasure4.getPosTan(pathMeasure4.getLength()*currentValue,pos,tan);
//        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度
//        mMatrix.postRotate(degrees,mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片
//        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合
        //等价于  end
        canvas.drawPath(arrowPath,paint);// 绘制 Path
        canvas.drawBitmap(mBitmap,mMatrix,paint);// 绘制箭头
        invalidate();// 重绘页面

    }
}
