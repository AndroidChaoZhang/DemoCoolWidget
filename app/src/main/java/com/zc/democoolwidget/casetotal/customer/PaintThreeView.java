package com.zc.democoolwidget.casetotal.customer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.zc.democoolwidget.R;

/**
 * Created by NEU on 2018/3/16.
 * http://www.gcssloop.com/customview/Canvas_PictureText
 * 使用Picture前请关闭硬件加速，以免引起不必要的问题！
 * 安卓自定义View进阶-Canvas之图片文字 三
 */

public class PaintThreeView extends View {
    private Context mContext;
    // 1.创建Picture
    private Picture mPicture = new Picture();

    public PaintThreeView(Context context) {
        this(context,null);
    }

    // 3.在使用前调用(我在构造函数中调用了)
    public PaintThreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPicture();
    }

    // 2.录制内容方法
    private void initPicture() {
        // 开始录制 (接收返回值Canvas)
        Canvas canvas = mPicture.beginRecording(500, 500);
        Paint mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        canvas.translate(250,250);
        canvas.drawCircle(0,0,100,mPaint);
        mPicture.endRecording();

    }

    @Override
    protected void onDraw(Canvas canvas) {//使用Picture前请关闭硬件加速，以免引起不必要的问题！ android:hardwareAccelerated=”false”以关闭整个应用的硬件加速
        super.onDraw(canvas);

        //////一.drawPicture(矢量图)
        // 1.将Picture中的内容绘制在Canvas上   ☆这种方法在比较低版本的系统上绘制后可能会影响Canvas状态，所以这种方法一般不会使用
//        mPicture.draw(canvas);

        //2.使用Canvas提供的drawPicture方法绘制  绘制的内容根据选区进行了缩放
//        canvas.drawPicture(mPicture,new RectF(0,0,mPicture.getHeight(),200));
        //3.将Picture包装成为PictureDrawable，使用PictureDrawable的draw方法绘制
//        PictureDrawable pictureDrawable = new PictureDrawable(mPicture);// 包装成为Drawable
//        // 设置绘制区域 -- 注意此处所绘制的实际内容不会缩放
//        pictureDrawable.setBounds(0,0,250,mPicture.getHeight());//setBounds是设置在画布上的绘制区域，并非根据该区域进行缩放，也不是剪裁Picture，每次都从Picture的左上角开始绘制
//        pictureDrawable.draw(canvas);

        //////二.drawBitmap(位图)绘制Bitmap都是读取已有的图片转换为Bitmap绘制到Canvas
        //获取图片的三种方式
        //1.资源文件(drawable/mipmap/raw)
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
        //2.资源文件(assets)
//        try {
//            InputStream inputStream = mContext.getAssets().open("bitmap.png");
//            Bitmap bitmap1 = BitmapFactory.decodeStream(inputStream);
//        } catch (IOException e) {}
        //3.内存卡文件
//        Bitmap bitmap1 = BitmapFactory.decodeFile("/sdcard/bitmap.png");
//        canvas.drawBitmap(bitmap,new Matrix(),new Paint());
//        canvas.drawBitmap(bitmap,200,500,new Paint());
        // src 指定绘制图片的区域 dst 指定图片在屏幕上显示(绘制)的区域,图片宽高会根据指定的区域自动进行缩放
        Rect rectSrc = new Rect(0, 0, bitmap.getWidth()/2, bitmap.getHeight()/2);
        Rect rectDst = new Rect(0, 0, 50, 80);
        canvas.drawBitmap(bitmap,rectSrc,rectDst,null);


        ////2.绘制文字
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(50);
        //第一类(drawText)
        //第一类可以指定文本开始的位置，可以截取文本中部分内容进行绘制
        String content = "测试绘制文字ABCD";
        // 参数分别为 (文本 基线x 基线y 画笔)
        canvas.drawText(content,200,100,paint);
        // 参数分别为 (字符串 开始截取位置 结束截取位置 基线x 基线y 画笔)
        canvas.drawText(content,2,10,200,150,paint);
        char[] chars = "测试绘制文字ABCD".toCharArray();
        // 参数为 (字符数组 起始坐标 截取长度 基线x 基线y 画笔)
        canvas.drawText(chars,2,8,200,200,paint);

        //第二类(drawPosText)不推荐使用
        String posText = "不推荐使用";
        canvas.drawPosText(posText,new float[] {
                100,100,    // 第一个字符位置
                200,200,    // 第二个字符位置
                300,300,    // ...
                400,400,
                500,500
        },paint);

        //第三类(drawTextOnPath)
    }

}
