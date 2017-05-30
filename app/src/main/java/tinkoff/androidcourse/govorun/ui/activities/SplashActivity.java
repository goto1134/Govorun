package tinkoff.androidcourse.govorun.ui.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.graphics.drawable.Animatable2Compat;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import tinkoff.androidcourse.govorun.R;
import tinkoff.androidcourse.govorun.login.LoginActivity;


public class SplashActivity extends AppCompatActivity {

    private AnimatedVectorDrawableCompat drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        //Почему-то работает только так. Если просто указатьв srcCompat у ImageView, имеет другой тип.
        drawable = AnimatedVectorDrawableCompat.create(this, R.drawable.gororun_animation);
        imageView.setImageDrawable(drawable);
        drawable.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationStart(Drawable drawable) {
                super.onAnimationStart(drawable);
            }

            @Override
            public void onAnimationEnd(Drawable drawable) {
                super.onAnimationEnd(drawable);
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawable.start();
    }
}
