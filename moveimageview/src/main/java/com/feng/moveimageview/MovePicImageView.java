package com.feng.moveimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;

/**
 * 使用前请传入id
 * Created by yufeng on 17-10-8.
 */

public class MovePicImageView extends ImageView {

    private static final String TAG = "MovePicImageView";

    private Matrix matrix = new Matrix();

    private MoveImageAnimator moveImageAnimator;

    private boolean back = false;

    private int imageWidth;
    private int imageHeight;

    private int bgId = 0;

    private class MoveImageAnimator extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);

            if (interpolatedTime == 1) {
                back = true;
            }
            if (interpolatedTime == 0) {
                back = false;
            }

            if (back) {
                matrix.postTranslate(interpolatedTime * 2, 0);
                setImageMatrix(matrix);
            } else {
                matrix.postTranslate(-interpolatedTime * 2, 0);
                setImageMatrix(matrix);
            }
            //Log.i(TAG,"applyTransformation: " + interpolatedTime);
        }
    }

    public MovePicImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovePicImageView(Context context) {
        this(context, null);
    }

    public MovePicImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(ScaleType.MATRIX);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MovePicImageView);
        bgId = array.getResourceId(R.styleable.MovePicImageView_move_pic_bg,0);
        array.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (bgId != 0) {

            //获取图片宽高
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), bgId, options);
            imageWidth = options.outWidth;
            imageHeight = options.outHeight;
            int inSampleSize = 1;
            //防止图片过大，出现OOM
            if (imageHeight > h) {
                final int halfHeight = imageHeight / 2;

                while ((halfHeight / inSampleSize) > h) {
                    inSampleSize *= 2;
                }
            }
            options.inSampleSize = inSampleSize;
            options.inJustDecodeBounds = false;

            Bitmap background = BitmapFactory.decodeResource(getResources(), bgId, options);

            setImageBitmap(background);

            Drawable d = getDrawable();
            if (d == null) return;

            //获取图片的宽高
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();

            Log.i(TAG, "onPostExecute: dw: " + dw + " dh :" + dh);

            float scale; //缩放值

            scale = getHeight() * 1f / dh;

            matrix.postScale(scale, scale);
            matrix.postTranslate(-100, 0);
            setImageMatrix(matrix);
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    public void setBG(int resId) {
        bgId = resId;
    }

    public void startAnimation() {
        if (moveImageAnimator != null) {
            moveImageAnimator.cancel();
        }

        moveImageAnimator = new MoveImageAnimator();
        moveImageAnimator.setDuration(10000);

        moveImageAnimator.setInterpolator(new LinearInterpolator());
        moveImageAnimator.setRepeatCount(Animation.INFINITE);
        moveImageAnimator.setRepeatMode(Animation.REVERSE);
        startAnimation(moveImageAnimator);
    }

    public void stopAnimation() {
        moveImageAnimator.cancel();
        moveImageAnimator = null;
    }
}
