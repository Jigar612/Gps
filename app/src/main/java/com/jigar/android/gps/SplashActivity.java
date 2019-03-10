package com.jigar.android.gps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SplashActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageView =(ImageView)findViewById(R.id.splashimgLogo);

        Thread timer = new Thread() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                try {
//                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_right);
//                    RelativeLayout relativeLayout =(RelativeLayout)findViewById(R.id.relative_splash_screen);
//                    relativeLayout.clearAnimation();
//                    relativeLayout.startAnimation(animation);

                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_to_right);
                    imageView.clearAnimation();
                    imageView.startAnimation(animation1);

                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        };
        timer.start();
    }
}
