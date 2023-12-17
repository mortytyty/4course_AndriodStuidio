package com.example.lb02;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    final String LifeCycleTag = "ActivityLifecycleState";
    EditText nameText;
    EditText passText;
    Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        Log.d(LifeCycleTag,"LoginActivity created");

        nameText = findViewById(R.id.et1name);
        passText = findViewById(R.id.et2pass);
        loginButton = findViewById(R.id.btn1login);

        Intent intent = new Intent(this, ListActivity.class);

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
        Log.d(LifeCycleTag,"LoginActivity stopped");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LifeCycleTag,"LoginActivity destroyed");
    }
}
