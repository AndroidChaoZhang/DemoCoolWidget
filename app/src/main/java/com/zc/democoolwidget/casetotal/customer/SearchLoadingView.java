package com.zc.democoolwidget.casetotal.customer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by NEU on 2018/4/11.
 */

public class SearchLoadingView extends View {
    private int centerX;
    private int centerY;
    private Paint paint = new Paint();

    public SearchLoadingView(Context context) {
        this(context,null);
    }

    public SearchLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w /2;
        centerY = h /2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(centerX,centerY);

        Path pathSearch = new Path();
        pathSearch.addArc(new RectF(-50,-50,50,50),45,359.9f);
        Path pathCircle = new Path();
        pathCircle.addArc(new RectF(-100,-100,100,100),45,359.9f);

        float[] pos = new float[2];
        PathMeasure pathMeasure = new PathMeasure(pathCircle,false);
        pathMeasure.getPosTan(0,pos,null);
        pathSearch.lineTo(pos[0],pos[1]);

        canvas.drawPath(pathSearch,paint);
        canvas.drawPath(pathCircle,paint);

    }

    private Handler mHandler = new Handler() {
    		@Override
    		public void handleMessage(Message msg) {
    			super.handleMessage(msg);
    			switch (msg.what) {
    				case 0:
    					break;
    			}
    		}
    	};
}
