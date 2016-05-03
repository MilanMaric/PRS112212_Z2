package net.etfbl.prs.prs112212_z2;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final int scaleLeft = 160;
    private static final int scaleTop = 180;
    private static final int scaleRight = 454;
    private static final int scaleBottom = 289;
    private static final int knobLeft = 192;
    private static final int knobTop = 321;
    private static final int knobRight = 423;
    private static final int knobBottom = 380;
    private static final String TAG = "MainActivity";
    private float x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioView radioView = (RadioView) findViewById(R.id.radio);
        radioView.setScaleRect(new Rect(scaleLeft, scaleTop, scaleRight, scaleBottom));
        radioView.setKnobRect(new Rect(knobLeft, knobTop, knobRight, knobBottom));
        TunerScale tunerScale = (TunerScale) findViewById(R.id.scale);
        TunerKnob tunerKnob = (TunerKnob) findViewById(R.id.knob);
        radioView.setTunerScale(tunerScale);
        radioView.setTunerKnob(tunerKnob);
        if (tunerScale != null)
            tunerScale.setFreq(80);
        if (tunerKnob != null)
            tunerKnob.setOnTouchEventListener(new TunerKnob.OnTouchEventListener() {
                @Override
                public void onTouchEvent(View v, DragEvent event) {
                    if (event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                        x = event.getX();
                        Log.d(TAG, "x:" + x);
                    } else {
                        Log.d(TAG, "Dx:" + (event.getX() - x));
                    }
                }
            });

    }
}
