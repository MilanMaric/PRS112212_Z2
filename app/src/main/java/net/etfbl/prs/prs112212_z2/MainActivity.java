package net.etfbl.prs.prs112212_z2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioView radioView = (RadioView) findViewById(R.id.radio);
        TunerScale tunerScale = (TunerScale) findViewById(R.id.scale);
        TunerKnob tunerKnob = (TunerKnob) findViewById(R.id.knob);
        tunerScale.setFreq(80);
        tunerKnob.setOnTouchEventListener(new TunerKnob.OnTouchEventListener() {
            @Override
            public void onTouchEvent(View v, MotionEvent event) {

            }
        });

    }
}
