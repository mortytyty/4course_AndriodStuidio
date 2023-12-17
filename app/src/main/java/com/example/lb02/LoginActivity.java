package com.example.lb02;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class LoginActivity extends AppCompatActivity {

    final String LifeCycleTag = "ActivityLifecycleState";

    EditText nameText;
    EditText passText;
    Button loginButton;
    ImageButton themeButton;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor spEditor;
    int themeFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        loadTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);


        nameText = findViewById(R.id.et1name);
        passText = findViewById(R.id.et2pass);
        loginButton = findViewById(R.id.btn1login);
        themeButton = findViewById(R.id.btn2theme);

        if(themeFlag==1){
            themeButton.setImageResource(R.drawable.moon);
            themeButton.setBackgroundResource(R.drawable.round_corner);
        }
        else {
            themeButton.setImageResource(R.drawable.sun);
            themeButton.setBackgroundResource(R.drawable.round_corner_night);
        }


        Intent intent = new Intent(this, ListActivity.class);

        themeButton.setOnClickListener(v -> changeTheme());
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String pass = passText.getText().toString();
                if(!(name.isEmpty())&&!(pass.isEmpty())) {
                    intent.putExtra("login",name);
                    startActivity(intent);
                }
            }
        });
        Log.d(LifeCycleTag,"LoginActivity created");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LifeCycleTag,"LoginActivity restarted");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LifeCycleTag,"LoginActivity started");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LifeCycleTag,"LoginActivity resumed");
    }
    @Override
    protected void onPause() {
        super.onPause();
        nameText.setText("");
        passText.setText("");
        Log.d(LifeCycleTag,"LoginActivity paused");
    }
    @Override
    protected void onStop() {
        super.onStop();
        saveTheme();
        Log.d(LifeCycleTag,"LoginActivity stopped");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LifeCycleTag,"LoginActivity destroyed");
    }
    private void saveTheme(){
        sharedPreferences = getSharedPreferences("AppTheme",MODE_PRIVATE);
        spEditor = sharedPreferences.edit();
        spEditor.putInt("theme", themeFlag);
        spEditor.apply();
        Log.d("ThemeDebug","Saved: "+themeFlag);
    }
    private void loadTheme(){
        sharedPreferences = getSharedPreferences("AppTheme",MODE_PRIVATE);
        themeFlag = sharedPreferences.getInt("theme",1);
        if(themeFlag==1){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        Log.d("ThemeDebug","Loaded: "+themeFlag);
    }
    private void changeTheme(){
        if(themeFlag==1){
            themeFlag=2;
        }
        else{
            themeFlag=1;
        }
        recreate();
        Log.d("ThemeDebug","Changed: "+themeFlag);
    }


}
