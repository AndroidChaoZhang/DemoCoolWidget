package com.zc.democoolwidget.casetotal.loadingview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zc.democoolwidget.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LoadingView extends View {

    private int degreeNumber = 12;
    private int degreeDistance = 3;
    private Paint mPaint ;
    private static final int MAX_LEAFS = 8;
    private Random random = new Random();
    /**叶子旋转周期*/
    private long leafCircleTime = 2000;
    /**叶子平移周期*/
    private long leafDintanceTime = 3000;
    /**所有的叶子信息*/
    private List<Leaf> leafs = new LinkedList<Leaf>();
    // 中等振幅大小
    private static final int MIDDLE_AMPLITUDE = 13;
    // 不同类型之间的振幅差距
    private static final int AMPLITUDE_DISPARITY = 5;
    private Bitmap leafBGBitmap;
    private Bitmap fengshanBitmap;
    private Bitmap leafBitmap;
    private int leafBGBitmapWidth = 0;
    private int leafBGBitmapHeight = 0;
    private int leafBitmapWidth = 0;
    private int leafBitmapHeight = 0;
    private int fengshanBitmapWidth = 0;
    private int fengshanBitmapHeight = 0;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initLeafs();
    }
    /**初始化叶子*/
    private void initLeafs() {
        Leaf leaf = null;
        int distanceTime = 0;//间隔时间
        for (int i = 0; i < MAX_LEAFS; i++) {
            leaf = new Leaf();
            int randomType = random.nextInt(StartType.values().length);
            // 随时类型－ 随机振幅
            StartType type = StartType.MIDDLE;
            switch (randomType) {
                case 1:
                    type = StartType.LITTLE;
                    break;
                case 2:
                    type = StartType.BIG;
                    break;
            }
            leaf.type = type;
            // 随机起始的旋转角度
            leaf.rotateAngle = random.nextInt(360);
            leaf.rotateDirection = random.nextInt(2);
            // 为了产生交错的感觉，让开始的时间有一定的随机性
            distanceTime = distanceTime + random.nextInt((int)leafDintanceTime *2);
            leaf.startTime = System.currentTimeMillis() + distanceTime;
            leafs.add(leaf);
        }
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setFilterBitmap(true);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画背景
        if (null==leafBGBitmap) {
            leafBGBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_leaf_kuang);
            leafBGBitmapWidth = leafBGBitmap.getWidth();
            leafBGBitmapHeight = leafBGBitmap.getHeight();
        }
        
        Rect destRect = new Rect(0, 0, leafBGBitmapWidth, leafBGBitmapHeight);
        canvas.drawBitmap(leafBGBitmap,null,destRect,mPaint);

        if (null==fengshanBitmap) {//风扇
            fengshanBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_fengshan);
            fengshanBitmapWidth = fengshanBitmap.getWidth();
            fengshanBitmapHeight = fengshanBitmap.getHeight();
        }
        drawLeafs(canvas);

        // 通过Matrix控制风扇旋转
        Matrix matrix = new Matrix();
        matrix.postTranslate(leafBGBitmapWidth-fengshanBitmapWidth-11,11);
        matrix.postRotate(degreeNumber,leafBGBitmapWidth-fengshanBitmapWidth/2-11,fengshanBitmapHeight/2+11);
        canvas.drawBitmap(fengshanBitmap,matrix,mPaint);
        degreeNumber = degreeNumber+degreeDistance;

//        int progressHeight = leafBGBitmapHeight - 2 * 11;
//        new Rect(0,0,fengshanBitmapHeight - 11,leafBGBitmapHeight - 11);
//        canvas.drawArc();

        postInvalidate();
    }

    private class Leaf {
        // 在绘制部分的位置
        float x, y;
        // 控制叶子飘动的幅度
        StartType type;
        // 旋转角度
        int rotateAngle;
        // 旋转方向--0代表顺时针，1代表逆时针
        int rotateDirection;
        // 起始时间(ms)
        long startTime;
    }
    private enum StartType {
        LITTLE, MIDDLE, BIG
    }

    private void drawLeafs(Canvas canvas) {
        if (null==leafBitmap) {//叶子
            leafBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_leaf);
            leafBitmapWidth = leafBitmap.getWidth();
            leafBitmapHeight = leafBitmap.getHeight();
        }
        for (int i = 0; i<leafs.size(); i++) {
            Leaf leaf = leafs.get(i);
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis > leaf.startTime) {
                // 绘制叶子－－根据叶子的类型和当前时间得出叶子的（x，y）
                getLeafLocation(leaf,currentTimeMillis);
                // 通过Matrix控制叶子旋转
                Matrix matrixLeaf = new Matrix();
                matrixLeaf.postTranslate(leaf.x,leaf.y);
                // 通过时间关联旋转角度，则可以直接通过修改LEAF_ROTATE_TIME调节叶子旋转快慢
                float rotateFraction = ((currentTimeMillis - leaf.startTime) % leafCircleTime) / (float) leafCircleTime;
                int angle = (int) (rotateFraction * 360);
                // 根据叶子旋转方向确定叶子旋转角度
                int rotate = leaf.rotateDirection == 0 ? angle + leaf.rotateAngle : -angle + leaf.rotateAngle;
                matrixLeaf.postRotate(rotate,leaf.x + leafBitmapWidth/2,leaf.y + leafBitmapHeight/2);
                canvas.drawBitmap(leafBitmap,matrixLeaf,mPaint);
            }
        }
    }

    private void getLeafLocation(Leaf leaf, long currentTime) {
        long intervalTime = currentTime - leaf.startTime;
        if (intervalTime > leafDintanceTime) {
            leaf.startTime = System.currentTimeMillis()
                    + random.nextInt((int) leafDintanceTime);
        }

        float fraction = (float) intervalTime / leafDintanceTime;
        int leafDistance = leafBGBitmapWidth - fengshanBitmapWidth - leafBitmapWidth;
        leaf.x = (int) leafDistance * (1 - fraction);
        leaf.y = getLocationY(leaf);
    }

    // 通过叶子信息获取当前叶子的Y值
    private int getLocationY(Leaf leaf) {
        // y = A(wx+Q)+h
        float w = (float) ((float) 2 * Math.PI / leafBGBitmapWidth);
        float a = 0;
        switch (leaf.type) {
            case LITTLE:
                // 小振幅 ＝ 中等振幅 － 振幅差
                a = MIDDLE_AMPLITUDE - AMPLITUDE_DISPARITY;
                break;
            case MIDDLE:
                a = MIDDLE_AMPLITUDE;
                break;
            case BIG:
                // 小振幅 ＝ 中等振幅 + 振幅差
                a = MIDDLE_AMPLITUDE + AMPLITUDE_DISPARITY;
                break;
        }
        return (int) (a * Math.sin(w * leaf.x)) + leafBGBitmapHeight/2 * 2 / 3;
    }

}
