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

/**
 * Created by milan on 1.5.2016.
 */
public class TunerScale extends View {

    private Bitmap mBitmap;
    private Rect mSrcRect;
    private static final String TAG="TunerScale";

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

    private  void setBitmap(){
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.tuner_scale);
        mSrcRect = new Rect(0,0, mBitmap.getWidth(), mBitmap.getHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG,"onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Log.d(TAG,"onMeasure(width:"+width+",height"+height+"\n\t)");

        int srcWidth = mBitmap.getWidth();
        int srcHeight = mBitmap.getHeight();

        if(widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(ScaleUtil.scale(width, srcWidth, srcHeight), height);
        } else if((widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)
                && heightMode == MeasureSpec.UNSPECIFIED) {
            height = ScaleUtil.scale(width, srcWidth, srcHeight);
        } else if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.EXACTLY) {
            width = Math.min(ScaleUtil.scale(height, srcHeight, srcWidth), width);
        } else if(widthMode == MeasureSpec.UNSPECIFIED &&
                (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.AT_MOST)) {
            width = ScaleUtil.scale(height, srcHeight, srcWidth);
        } else if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            float scaleFactor = ScaleUtil.factor(srcWidth, srcHeight,
                    Math.min(width, srcWidth), Math.min(height, srcHeight));
            width = (int) (scaleFactor * srcWidth);
            height = (int) (scaleFactor * srcHeight);
        } else if(widthMode == MeasureSpec.UNSPECIFIED && heightMode == MeasureSpec.UNSPECIFIED) {
            width = srcWidth;
            height = srcHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
      //  super.onDraw(canvas);
        Log.d(TAG,"onDraw"+canvas.getWidth()+" ,"+canvas.getHeight());
        Rect dst=new Rect(0,0,canvas.getWidth(),canvas.getHeight());
        canvas.drawBitmap(mBitmap, mSrcRect, dst, new Paint(Color.GRAY));

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout(changed:" + changed + " l:" + left + " t:" + top + " r:" + right + " b:" + bottom + ")");
    }
}
