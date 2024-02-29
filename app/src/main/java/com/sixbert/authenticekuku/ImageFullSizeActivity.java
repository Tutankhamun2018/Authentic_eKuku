package com.sixbert.authenticekuku;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ImageFullSizeActivity extends AppCompatActivity {

    private ScaleGestureDetector gestureDetector;
    private float mScaleFactor = 1.0f;

    String url;
    ImageView fullScreenImage;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the phone's status bar transparent ie clock, charge visible;
        Window win = getWindow();
        overridePendingTransition(0, 0);
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_image_full_size);

        url = getIntent().getStringExtra("imageUrl");

        fullScreenImage = findViewById(R.id.selectedImage);
        Glide.with(this).load(url).into(fullScreenImage);

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
            fullScreenImage.setScaleX(mScaleFactor);
            fullScreenImage.setScaleY(mScaleFactor);
            return true;
        }
    }

}
