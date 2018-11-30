package com.zc.democoolwidget.imagegaosi;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zc.democoolwidget.R;
import com.zc.democoolwidget.imagegaosi.stackblur.StackBlurManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * https://github.com/kikoso/android-stackblur
 * 高斯模糊图片
 */
public class ImageGaosiActivity extends AppCompatActivity implements View.OnClickListener {
    private Button screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gaosi);
        screen = (Button) findViewById(R.id.screen);
        screen.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //获取当前的屏幕视图
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();

//        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_welcome_0);//获取资源文件里面的图片，返回immutable的Bitmap
        Bitmap mBitmap = getBitmapFromResId(R.drawable.content_films,w,h);//获取资源文件里面的图片，返回immutable的Bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(mBitmap, 0, 0, paint);
        StackBlurManager stackBlurManager = new StackBlurManager(bitmap);
        Bitmap bitmap1 = stackBlurManager.processNatively(10);//从1开始
        showDialoge(bitmap1);
    }

    private void showDialoge(Bitmap img) {
        Dialog dialoge = new Dialog(this);
        View view = View.inflate(this, R.layout.dialoge_gaosi, null);
        dialoge.addContentView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dialoge.show();
        ImageView imageView = (ImageView) dialoge.findViewById(R.id.img);
        imageView.setImageBitmap(img);
    }

    private Bitmap overlay = null;

    /**
     * 对拿到的背景图片进行模糊处理
     *
     * @return
     */
    private Bitmap blur() {
        if (null != overlay) {
            return overlay;
        }
        //获取当前的屏幕视图
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();
        View view = getWindow().getDecorView();//截取当前view界面的视图
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap mBitmap = view.getDrawingCache();

        float radius = 4;
        overlay = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(mBitmap, 0, 0, paint);
        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        view.setDrawingCacheEnabled(false);
        return overlay;
    }

    private Bitmap getBitmapFromResId(int resId, int width, int height) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getResources().openRawResource(resId), null, options);
            options.inSampleSize = calculateInSampleSize(options,width,height);
            options.inJustDecodeBounds = false;
//            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            Bitmap tempBitmap = BitmapFactory.decodeStream(getResources().openRawResource(resId), null, options);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            tempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            int per = 100;
            while (outputStream.toByteArray().length / 1024 > 300) {
                outputStream.reset();
                per -= 10;
                tempBitmap.compress(Bitmap.CompressFormat.JPEG, per, outputStream);
            }
            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            outputStream.close();
            inputStream.close();
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }

}
