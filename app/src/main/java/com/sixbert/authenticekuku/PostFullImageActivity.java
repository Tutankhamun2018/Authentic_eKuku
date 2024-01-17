package com.sixbert.authenticekuku;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PostFullImageActivity extends AppCompatActivity {
    private ScaleGestureDetector gestureDetector;
    private float mScaleFactor = 1.0f;
    String postImageUrl;
    ImageView postFullScreenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_full_image);
        Window win = getWindow();
        overridePendingTransition(0, 0);
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_image_full_size);

        postImageUrl = getIntent().getStringExtra("postImageUrl");

        postFullScreenImage = findViewById(R.id.postFullImage);
        Glide.with(this).load(postImageUrl).into(postFullScreenImage);

        gestureDetector = new ScaleGestureDetector(this, new ScaleListener());

    }

    public  boolean onTouchEvent(MotionEvent motionEvent){
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector gestureDetector){
            mScaleFactor *=gestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            postFullScreenImage.setScaleX(mScaleFactor);
            postFullScreenImage.setScaleY(mScaleFactor);
            return true;
        }
    }

}
