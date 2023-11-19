package com.example.lb01;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class HelloActivity extends Activity{
    int button1ClickCount = 0;
    int button2ClickCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_helloact);
            Button button1 = findViewById(R.id.button1);
            Button button2 = findViewById(R.id.button2);
            TextView textViewButton1 = findViewById(R.id.tw1);
            TextView textViewButton2 = findViewById(R.id.tw2);



            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(button1ClickCount==0)button1.setText("ouch!");
                    if(button1ClickCount==5)button1.setText("Man, stop!");
                    if(button1ClickCount==10){
                        button1.setWidth(300);
                        button1.setText("Better click on the button on the right..");
                    }
                    if(button1ClickCount==20){
                        button1.setText("I said stop it, it's really annoying.");
                    }

                    if(button1ClickCount==30){
                        button1.setText("One more time and I'm out.");
                    }
                    if(button1ClickCount==31){
                        button1.setVisibility(View.GONE);
                        textViewButton1.setVisibility(View.GONE);

                    }

                    button1ClickCount++;
                    textViewButton1.setText("Clicks: "+button1ClickCount);
                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(button2ClickCount%50==10)button2.setText("Thank you!");
                    if(button2ClickCount%50==25)button2.setText("Wow!");
                    if(button2ClickCount%50==40){
                        button2.setText("I really appreciate it.");
                    }
                    button2ClickCount++;
                    textViewButton2.setText("Clicks: "+button2ClickCount);
                }
            });

    }
}
