/****************************************************************************
 * Copyright (c) 2016 Elektrotehnicki fakultet
 * Patre 5, Banja Luka
 * <p/>
 * All Rights Reserved
 * <p/>
 * \file RadioView.java
 * \brief
 * This file contains a source code for RadioView - view group that contains TunerScale and TunerKnob.
 *
 * <p/>
 * Created on 27.04.2016
 *
 * @Author Milan Maric
 * <p/>
 * \notes
 * <p/>
 * <p/>
 * \history
 * <p/>
 **********************************************************************/
package net.etfbl.prs.prs112212_z2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class RadioView extends ViewGroup {
    private static final String TAG = "RadioView";
    private Rect originScaleRect;
    private Rect originKnobRect;

    private Bitmap mBitmap;
    private Rect mSrcRect;
    private Rect knobRect = new Rect();
    private Rect scaleRect = new Rect();
    private Rect dst = new Rect();

    public RadioView(Context context) {
        super(context);
        setBitmap();
    }

    public RadioView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBitmap();
    }

    public RadioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBitmap();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure(" + widthMeasureSpec + ", " + heightMeasureSpec + ");");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int srcWidth = mBitmap.getWidth();
        int srcHeight = mBitmap.getHeight();

        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(ScaleUtil.scale(width, srcWidth, srcHeight), height);
        } else if ((widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)
                && heightMode == MeasureSpec.UNSPECIFIED) {
            height = ScaleUtil.scale(width, srcWidth, srcHeight);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.EXACTLY) {
            width = Math.min(ScaleUtil.scale(height, srcHeight, srcWidth), width);
        } else if (widthMode == MeasureSpec.UNSPECIFIED &&
                (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.AT_MOST)) {
            width = ScaleUtil.scale(height, srcHeight, srcWidth);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            float scaleFactor = ScaleUtil.factor(srcWidth, srcHeight,
                    Math.min(width, srcWidth), Math.min(height, srcHeight));
            width = (int) (scaleFactor * srcWidth);
            height = (int) (scaleFactor * srcHeight);
        } else if (widthMode == MeasureSpec.UNSPECIFIED && heightMode == MeasureSpec.UNSPECIFIED) {
            width = srcWidth;
            height = srcHeight;
        }

        if (originKnobRect == null || originScaleRect == null) {
            Log.e(TAG, "please set the rects");
            throw new RuntimeException("knobRect and scaleRect are not set!");
        } else {
            scaleRect.set(
                    ScaleUtil.scale(width, srcWidth, originScaleRect.left),//left
                    ScaleUtil.scale(height, srcHeight, originScaleRect.top),//top
                    ScaleUtil.scale(width, srcWidth, originScaleRect.right),//
                    ScaleUtil.scale(height, srcHeight, originScaleRect.bottom)
            );


            knobRect.set(
                    ScaleUtil.scale(width, srcWidth, originKnobRect.left),//left
                    ScaleUtil.scale(height, srcHeight, originKnobRect.top),//top
                    ScaleUtil.scale(width, srcWidth, originKnobRect.right),//
                    ScaleUtil.scale(height, srcHeight, originKnobRect.bottom)
            );
        }


        Log.d(TAG, "width: " + width + " height" + height);
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout(changed:" + changed + " l:" + l + " t:" + t + " r:" + r + " b:" + b + ")");
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            if (child instanceof TunerScale) {
                child.layout(scaleRect.left, scaleRect.top, scaleRect.right, scaleRect.bottom);
            }
            if (child instanceof TunerKnob) {
                child.layout(knobRect.left, knobRect.top, knobRect.right, knobRect.bottom);
            }

        }

    }

    /**
     * This method is used to set bitmap of background.
     */
    private void setBitmap() {
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.background);
        mSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw width:" + canvas.getWidth() + " height:" + canvas.getHeight());
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.d(TAG, "dispatchDraw width:" + canvas.getWidth() + " height:" + canvas.getHeight());

        dst.set(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.drawBitmap(mBitmap, mSrcRect, dst, new Paint(Color.TRANSPARENT));
        super.dispatchDraw(canvas);
    }

    /**
     * This method is used to set the position of tuner scale.
     * @param rect Rect object that contains cords where tuner scale should be drawn.
     */
    public void setScaleRect(Rect rect) {
        originScaleRect = rect;
    }

    /**
     * This method is used to set the position of tuner knob.
     * @param rect Rect object that contains cords where tuner knob should be drawn.
     */
    public void setKnobRect(Rect rect) {
        originKnobRect = rect;
    }
}
