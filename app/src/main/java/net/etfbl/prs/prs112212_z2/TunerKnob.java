package net.etfbl.prs.prs112212_z2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by milan on 1.5.2016.
 */
public class TunerKnob extends View {


    private Bitmap mBitmap;
    private Rect mSrcRect;

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
        super.onDraw(canvas);
    }

    private  void setBitmap(){
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.tuner_scale);
        mSrcRect = new Rect(0,0, mBitmap.getWidth(), mBitmap.getHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
