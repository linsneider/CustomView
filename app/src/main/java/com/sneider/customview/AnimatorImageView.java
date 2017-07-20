package com.sneider.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class AnimatorImageView extends AppCompatImageView implements Runnable {

    private Bitmap mBitmap;
    private int mTimeUnit;
    private int mTempHeight;

    public AnimatorImageView(Context context) {
        super(context);
        init();
    }

    public AnimatorImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimatorImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            mBitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        start(2000);
    }

    private void start(int time) {
        int height = mBitmap.getHeight();
        int count = height / 10;
        mTimeUnit = time / count;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        for (int i = 0; i >= 0; i = i + 10) {
            mTempHeight = mBitmap.getHeight() - i;
            try {
                Thread.sleep(mTimeUnit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            postInvalidate();
            if (i > mBitmap.getHeight()) i = 0;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.save();
            Rect rect = new Rect(0, mTempHeight, mBitmap.getWidth(), mBitmap.getHeight());
            canvas.drawBitmap(mBitmap, rect, rect, null);
            canvas.restore();
        }
    }
}
