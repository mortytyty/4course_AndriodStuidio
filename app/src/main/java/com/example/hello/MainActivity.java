package com.example.hello;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity {

    TextView textView1;
    TextView textView2;
    TextView textView3;

    TextView t1,t2,t3;

    ImageView img1;
    ImageView img2;
    ImageView img3;


    Button b1;
    Button b2;
    SensorManager sensorManager;
    Sensor sensorAccel;
    Sensor sensorMagnet;
    Timer timer;
    float[] r = new float[9];
    float[] valuesResult= new float[3];
    float[] valuesAccel = new float[3];
    float[] valuesMagnet = new float[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.tw1);
        textView2 = findViewById(R.id.tw2);
        textView3 = findViewById(R.id.tw3);

        img1 = findViewById(R.id.im1);
        img2 = findViewById(R.id.im2);
        img3 = findViewById(R.id.im3);


        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener, sensorMagnet, SensorManager.SENSOR_DELAY_NORMAL);

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getDeviceOrientation();
                        showText();
                    }
                });
            }
        };
        timer.schedule(task, 0, 50);
    }



    void showText(){
        if(isEdge(valuesResult[0])){
            textView1.setTextColor(Color.GREEN);
            t1.setTextColor(Color.GREEN);
            img1.setColorFilter(Color.GREEN);
        }else{
            textView1.setTextColor(Color.BLACK);
            t1.setTextColor(Color.BLACK);
            img1.setColorFilter(Color.BLACK);
        }

        if(isEdge(valuesResult[1])){
            textView2.setTextColor(Color.GREEN);
            t2.setTextColor(Color.GREEN);
            img2.setColorFilter(Color.GREEN);
        }else{
            textView2.setTextColor(Color.BLACK);
            t2.setTextColor(Color.BLACK);
            img2.setColorFilter(Color.BLACK);
        }

        if(isEdge(valuesResult[2])){
            textView3.setTextColor(Color.GREEN);
            t3.setTextColor(Color.GREEN);
            img3.setColorFilter(Color.GREEN);
        }else{
            textView3.setTextColor(Color.BLACK);
            t3.setTextColor(Color.BLACK);
            img3.setColorFilter(Color.BLACK);
        }

        textView1.setText(String.format("%.1f",valuesResult[0]));
        textView2.setText(String.format("%.1f",valuesResult[1]));
        textView3.setText(String.format("%.1f",valuesResult[2]));


    }


    boolean isEdge(float val){
        val = Math.abs(val);
        if((val<=91&&val>=89)||(val<=1)||(val>=179)){
            return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);
        timer.cancel();
    }

    void getDeviceOrientation() {
        SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet);
        SensorManager.getOrientation(r, valuesResult);

        valuesResult[0] = (float)Math.toDegrees(valuesResult[0]);
        valuesResult[1] = (float)Math.toDegrees(valuesResult[1]);
        valuesResult[2] = (float)Math.toDegrees(valuesResult[2]);
    }

    SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    for (int i = 0 ;i < 3;i++){
                        valuesAccel[i] = event.values[i];
                    }
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    for (int i = 0 ;i < 3;i++){
                        valuesMagnet[i] = event.values[i];
                    }
                    break;
            }
        }
    };

}