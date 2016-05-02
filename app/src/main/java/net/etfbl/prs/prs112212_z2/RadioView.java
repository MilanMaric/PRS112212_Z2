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

/**
 * Created by milan on 28.4.2016.
 */
public class RadioView extends ViewGroup {
    private static final String TAG = "RadioView";
    private static final int scaleLeft = 160;
    private static final int scaleTop = 180;
    private static final int scaleRight = 454;
    private static final int scaleBottom = 289;
    private static final int knobLeft = 192;
    private static final int knobTop = 321;
    private static final int knobRight = 423;
    private static final int knobBottom = 380;
    private Bitmap mBitmap;
    private Rect mSrcRect;
    private TunerKnob mKnob;
    private TunerScale mScale;
    private Rect knobRect = new Rect();
    private Rect scaleRect = new Rect();

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


        scaleRect.set(
                ScaleUtil.scale(width, srcWidth, scaleLeft),//left
                ScaleUtil.scale(height, srcHeight, scaleTop),//top
                ScaleUtil.scale(width, srcWidth, scaleRight),//
                ScaleUtil.scale(height, srcHeight, scaleBottom)
        );


        knobRect.set(
                ScaleUtil.scale(width, srcWidth, knobLeft),//left
                ScaleUtil.scale(height, srcHeight, knobTop),//top
                ScaleUtil.scale(width, srcWidth, knobRight),//
                ScaleUtil.scale(height, srcHeight, knobBottom)
        );


        Log.d(TAG, "width: " + width + " height" + height);
        setMeasuredDimension(width, height);

    }

    /**
     * Position all children within this layout.
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout(changed:" + changed + " l:" + l + " t:" + t + " r:" + r + " b:" + b + ")");
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            if (child instanceof TunerScale) {

                child.layout(scaleRect.left, scaleRect.top, scaleRect.right, scaleRect.bottom);
            } else {
                child.layout(knobRect.left, knobRect.top, knobRect.right, knobRect.bottom);
            }

        }

    }

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

        Rect dst = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.drawBitmap(mBitmap, mSrcRect, dst, new Paint(Color.TRANSPARENT));
        super.dispatchDraw(canvas);
    }

    public void setTunerScale(TunerScale scale) {
        mScale = scale;
    }

    public void setTunerKnob(TunerKnob knob) {
        mKnob = knob;
    }
}
