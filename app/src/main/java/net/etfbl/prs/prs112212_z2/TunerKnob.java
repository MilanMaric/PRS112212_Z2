/****************************************************************************
 * Copyright (c) 2016 Elektrotehnicki fakultet
 * Patre 5, Banja Luka
 * <p/>
 * All Rights Reserved
 * <p/>
 * \file TunerKnob.java
 * \brief
 * This file contains a source code for TunerKnob - view that is rendered inside of RadioView,
 * and it is used to make on move animation.
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
import android.view.MotionEvent;
import android.view.View;

public class TunerKnob extends View {


    public static final int ROTATING_PART_SCALE = 5;
    private static final String TAG = "TunerKnob";
    private static final int LEFT_ROTATING_MARGIN = 14;
    private static final int TOP_ROTATING_MARGIN = 5;
    private static final int RIGHT_ROTATING_MARGIN = 14;
    private static final int BOTTOM_ROTATING_MARGIN = 4;
    private Bitmap rotatingPart;
    private Rect mSrcRotatingRect;
    private Rect dstRotatingPart = new Rect();
    private Bitmap staticPart;
    private Rect mSrcStaticRect;
    private Paint paint = new Paint(Color.GRAY);
    private TunerKnobOnTouchEventListener listener;
    private Rect dst = new Rect();
    private int x = 0;


    public TunerKnob(Context context) {
        super(context);
        setBitmap();
    }

    public TunerKnob(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBitmap();
    }

    public TunerKnob(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw x:" + x);
        //   super.onDraw(canvas);
        dst.set(0, 0, canvas.getWidth(), canvas.getHeight());
        mSrcRotatingRect.set(x, 0, x + rotatingPart.getWidth() / ROTATING_PART_SCALE, rotatingPart.getHeight());
        dstRotatingPart.set(dst.left + ScaleUtil.scale(canvas.getWidth(), staticPart.getWidth(), LEFT_ROTATING_MARGIN), dst.top + ScaleUtil.scale(canvas.getHeight(), staticPart.getHeight(), TOP_ROTATING_MARGIN), dst.right - ScaleUtil.scale(canvas.getWidth(), staticPart.getWidth(), RIGHT_ROTATING_MARGIN), dst.bottom - ScaleUtil.scale(canvas.getHeight(), staticPart.getHeight(), BOTTOM_ROTATING_MARGIN));
        canvas.drawBitmap(rotatingPart, mSrcRotatingRect, dstRotatingPart, paint);
        canvas.drawBitmap(staticPart, mSrcStaticRect, dst, paint);
    }

    /**
     * This method is used to set bitmap of images.
     */
    private void setBitmap() {
        rotatingPart = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.knob_rotating_part);
        staticPart = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.knob_static_part);
        mSrcRotatingRect = new Rect(0, 0, rotatingPart.getWidth(), rotatingPart.getHeight());
        mSrcStaticRect = new Rect(0, 0, staticPart.getWidth(), staticPart.getHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int size = event.getHistorySize();
            if (size > 0) {
                float locx = event.getX();
                float lastX = event.getHistoricalX(size - 1);
                float move = locx - lastX;
                Log.d(TAG, "x+move:" + (x + move) + " rotating part width: " + rotatingPart.getWidth());
                if (x + move > 0 && x + move + mSrcRotatingRect.width() < rotatingPart.getWidth()) {
                    if (listener != null) {
                        float percentage = (x + move) / (rotatingPart.getWidth() - mSrcRotatingRect.width());
                        Log.d(TAG, "Precentage: " + percentage);
                        listener.onTouchEvent(this, event, percentage);
                    }
                    x += (int) locx - lastX;
                    invalidate();
                }

            }
        }
        return true;
    }

    /**
     * This method is used to set custom touch listener
     *
     * @param listener instance of class that is implementing TunerKnobOnTouchEventListener
     */
    public void setOnTouchEventListener(TunerKnobOnTouchEventListener listener) {
        this.listener = listener;
    }


    public interface TunerKnobOnTouchEventListener {
        boolean onTouchEvent(View v, MotionEvent event, float diff);
    }


}
