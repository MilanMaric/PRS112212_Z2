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


public class TunerScale extends View {

    private static final String TAG = "TunerScale";
    private Bitmap mBitmap;
    private Rect mSrcRect;
    private Rect dstRect;
    private Paint red=new Paint(Color.RED);


    private float freq = 110;

    public TunerScale(Context context) {
        super(context);
        setBitmap();
    }

    public TunerScale(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBitmap();
    }

    public TunerScale(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBitmap();
    }

    private void setBitmap() {
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.tuner_scale);
        mSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //  super.onDraw(canvas);
        Log.d(TAG, "onDraw" + canvas.getWidth() + " ," + canvas.getHeight());
        Rect dst = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.drawBitmap(mBitmap, mSrcRect, dst, new Paint(Color.GRAY));
        float pos=(mBitmap.getWidth() - 120) * (freq-80) / 30;
        int x = ScaleUtil.scale(canvas.getWidth(), mBitmap.getWidth(), 60+(int)pos);
        Log.d(TAG, "x:" + x +" pos:"+pos);
        canvas.drawLine(x, ScaleUtil.scale(canvas.getHeight(),mBitmap.getHeight(),20), x, canvas.getHeight()-ScaleUtil.scale(canvas.getHeight(), mBitmap.getHeight(), 20), red);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout(changed:" + changed + " l:" + left + " t:" + top + " r:" + right + " b:" + bottom + ")");
    }

    public void setDstRect(Rect dstRect) {
        this.dstRect = dstRect;
    }


    public float getFreq() {
        return freq;
    }

    public void setFreq(float freq) {
        if (freq >= 80.0 && freq <= 110.0) {
            this.freq = freq;
            Log.d(TAG, "frequency set to: " + freq);
            invalidate();
        }
    }


}
