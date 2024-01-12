package com.example.lb02;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.lb02.Handlers.ThreadTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    final String LifeCycleTag = "ActivityLifecycleState";
    EditText nameText;
    EditText passText;
    Button loginButton;
    Button registerButton;
    final Looper looper = Looper.getMainLooper();
    final Handler handler = new Handler(looper){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.sendingUid == 1){
                    Intent listIntent = new Intent(LoginActivity.this, ListActivity.class);
                    listIntent.putExtra("login", (String) msg.obj);
                    startActivity(listIntent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    Toast.makeText(LoginActivity.this,
                            "Welcome, "+ msg.obj, Toast.LENGTH_SHORT).show();
            }else if (msg.sendingUid == 2)
                    Toast.makeText(LoginActivity.this,
                            "Invalid login or password", Toast.LENGTH_SHORT).show();
            }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);


        nameText = findViewById(R.id.et1name);
        passText = findViewById(R.id.et2pass);

        loginButton = findViewById(R.id.btn_log_login);
        registerButton=findViewById(R.id.btn_log_register);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        Log.d(LifeCycleTag,"LoginActivity created");
    }

    @Override
    protected void onPause() {
        super.onPause();
        nameText.setText("");
        passText.setText("");
        Log.d(LifeCycleTag,"LoginActivity paused");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btn_log_login) {

            String login = nameText.getText().toString();
            String pass = passText.getText().toString();

            if(!(login.isEmpty())&&!(pass.isEmpty())) {
                new ThreadTask(handler,this).checkValid(login,pass);
            }
        }
        else if(id == R.id.btn_log_register){
            Intent regIntent = new Intent(this, RegistrationActivity.class);
            startActivity(regIntent);
        }
    }
}
