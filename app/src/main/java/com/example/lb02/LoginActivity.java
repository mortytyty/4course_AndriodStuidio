package com.example.lb02;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.lb02.Handlers.DataBaseHandler;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    final String LifeCycleTag = "ActivityLifecycleState";

    EditText nameText;
    EditText passText;
    Button loginButton;
    Button registerButton;
    DataBaseHandler dataBaseHandler;

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

        dataBaseHandler = new DataBaseHandler(this);

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
        Log.d(LifeCycleTag,"LoginActivity stopped");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LifeCycleTag,"LoginActivity destroyed");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btn_log_login) {

            String login = nameText.getText().toString();
            String pass = passText.getText().toString();

            if(!(login.isEmpty())&&!(pass.isEmpty())) {
                if(dataBaseHandler.checkDataValidation(login,pass)) {
                    Intent listIntent = new Intent(this, ListActivity.class);
                    listIntent.putExtra("login", login);
                    startActivity(listIntent);
                }else{
                    Toast.makeText(LoginActivity.this,
                            "Invalid login or password", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if(id == R.id.btn_log_register){
            Intent regIntent = new Intent(this, RegistrationActivity.class);
            startActivity(regIntent);
        }
    }
}
