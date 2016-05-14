package net.etfbl.prs.prs112212_z2;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        final TunerScale tunerScale = (TunerScale) findViewById(R.id.scale);
        final TunerKnob tunerKnob = (TunerKnob) findViewById(R.id.knob);

        if (tunerScale != null)
            tunerScale.setFreq(80);
        if (tunerKnob != null)
            tunerKnob.setOnTouchEventListener(new TunerKnob.MyOnTouchEventListener() {
                @Override
                public boolean onTouchEvent(View v, MotionEvent event, float diff) {

                    return tunerScale.setFreq(80+diff*30);
                }
            });

    }
}
