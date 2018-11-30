package com.zc.democoolwidget.water;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.zc.democoolwidget.R;

/**
 * Created by NEU on 2017/9/12.
 */

public class WaterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        final SignUpRelativeLayout surl_animal = (SignUpRelativeLayout) findViewById(R.id.surl_animal);
        surl_animal.startRippleAnimation();

        findViewById(R.id.rl_sign_up_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surl_animal.stopRippleAnimation();
                loadViewAnimal();
            }
        });
    }

    /**签到点击的动画效果*/
    public void loadViewAnimal () {
        View tv_sign_up_sign = findViewById(R.id.tv_sign_up_sign);
        ImageView iv_sign_already = (ImageView) findViewById(R.id.iv_sign_already);
        iv_sign_already.setBackgroundResource(R.drawable.icon_sign_already);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tv_sign_up_sign,"alpha",1f,0f);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.setDuration(1000);
        objectAnimator.start();

        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(iv_sign_already, "alpha", 0f,0f,0.5f,1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(iv_sign_already,"scaleY",0f,1f);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(iv_sign_already,"scaleX",0f,1f);
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.play(alphaAnimator).with(scaleYAnimator).with(scaleXAnimator);
        animatorSet.start();
    }
}
