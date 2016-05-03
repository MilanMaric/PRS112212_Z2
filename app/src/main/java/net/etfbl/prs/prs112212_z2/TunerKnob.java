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
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by milan on 1.5.2016.
 */
public class TunerKnob extends View {


    private static final String TAG = "TunerKnob";
    private Bitmap rotatingPart;
    private Rect mSrcRotatingRect;
    private Bitmap staticPart;
    private Rect mSrcStaticRect;
    private Paint paint = new Paint(Color.GRAY);
    private OnTouchEventListener listener;
    private Rect dst = new Rect();


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
        Log.d(TAG, "onDraw");
        super.onDraw(canvas);
        dst.set(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.drawBitmap(rotatingPart, mSrcRotatingRect, dst, paint);
        canvas.drawBitmap(staticPart, mSrcStaticRect, dst, paint);
    }

    private void setBitmap() {
        rotatingPart = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.knob_rotating_part);
        staticPart = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.knob_static_part);
        mSrcRotatingRect = new Rect(0, 0, rotatingPart.getWidth(), rotatingPart.getHeight());
        mSrcStaticRect = new Rect(0, 0, staticPart.getWidth(), staticPart.getHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public boolean onDragEvent(DragEvent event) {
        if (listener != null) {
            listener.onTouchEvent(this, event);
        }
        return true;
    }



    public void setOnTouchEventListener(OnTouchEventListener listener) {
        this.listener = listener;
    }


    public interface OnTouchEventListener {
        void onTouchEvent(View v, DragEvent event);
    }


}
