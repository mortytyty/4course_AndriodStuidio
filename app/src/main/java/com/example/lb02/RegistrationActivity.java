package com.example.lb02;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lb02.Handlers.DataBaseHandler;
import com.example.lb02.Models.User;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    EditText fNameText;
    EditText loginText;
    EditText passText;
    Button regButton;
    Button cancelButton;
    DataBaseHandler dataBaseHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        fNameText = findViewById(R.id.et_reg_name);
        loginText = findViewById(R.id.et_reg_login);
        passText = findViewById(R.id.et_reg_pass);
        regButton = findViewById(R.id.btn_reg_register);
        cancelButton = (Button)findViewById(R.id.btn_reg_cancel);
        regButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        dataBaseHandler = new DataBaseHandler(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btn_reg_register) {
            String fName, login, pass;
            fName = fNameText.getText().toString();
            login = loginText.getText().toString();
            pass = passText.getText().toString();
            if (fName.isEmpty() || login.isEmpty() || pass.isEmpty()) {
                Toast.makeText(RegistrationActivity.this,
                        "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pass.length() < 5) {
                Toast.makeText(RegistrationActivity.this,
                        "Password must be longer than 5 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            if(dataBaseHandler.checkUserLoginExist(login)){
                Toast.makeText(RegistrationActivity.this,
                        "A user with this login already exists", Toast.LENGTH_SHORT).show();
                return;
            }
            dataBaseHandler.addUser(new User(fName, login, pass));
            finish();
        }
        else if(id == R.id.btn_reg_cancel){
            finish();
        }
    }
}

