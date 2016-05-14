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

/**
 * Created by milan on 1.5.2016.
 */
public class TunerKnob extends View {


    private static final String TAG = "TunerKnob";
    private static final int leftRotatingMargin = 14;
    private static final int topRotatingMargin = 5;
    private static final int rightRotatingMargin = 14;
    private static final int bottomRotatingMargin = 4;
    private Bitmap rotatingPart;
    private Rect mSrcRotatingRect;
    private Rect dst1 = new Rect();
    private Bitmap staticPart;
    private Rect mSrcStaticRect;
    private Paint paint = new Paint(Color.GRAY);
    private MyOnTouchEventListener listener;
    private Rect dst = new Rect();
    private int x = 0;
    private int index = 0;


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
        super.onDraw(canvas);
        dst.set(0, 0, canvas.getWidth(), canvas.getHeight());
        mSrcRotatingRect.set(x, 0, x + canvas.getWidth(), rotatingPart.getHeight());
        dst1.set(dst.left + ScaleUtil.scale(canvas.getWidth(), staticPart.getWidth(), leftRotatingMargin), dst.top + ScaleUtil.scale(canvas.getHeight(), staticPart.getHeight(), topRotatingMargin), dst.right - ScaleUtil.scale(canvas.getWidth(), staticPart.getWidth(), rightRotatingMargin), dst.bottom - ScaleUtil.scale(canvas.getHeight(), staticPart.getHeight(), bottomRotatingMargin));
        canvas.drawBitmap(rotatingPart, mSrcRotatingRect, dst1, paint);
        canvas.drawBitmap(staticPart, mSrcStaticRect, dst, paint);
    }

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
                Log.d(TAG, "move: " + move);
                Log.d(TAG, "x+move:" + (x + move));
                if (x + move > 0 && x + move + dst1.width() < rotatingPart.getWidth()) {
                    if (listener != null) {
                        float percentage = (x+move) / (rotatingPart.getWidth()-dst1.width());
                        Log.d(TAG, "Precentage: " + percentage);
                        listener.onTouchEvent(this, event, percentage);
                    }
                    x += (int) locx - lastX;
                    invalidate();
                }
                index++;
            }
        }
        return true;
    }

    public void setOnTouchEventListener(MyOnTouchEventListener listener) {
        this.listener = listener;
    }


    public interface MyOnTouchEventListener {
        boolean onTouchEvent(View v, MotionEvent event, float diff);
    }


}
