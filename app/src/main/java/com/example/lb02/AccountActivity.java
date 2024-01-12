package com.example.lb02;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lb02.Handlers.DataBaseHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener{
    Button bLogOut;
    Button bDeleteAccount;
    Button bChangePassword;
    TextView twLogin;
    TextView twName;
    EditText etNewPass;
    DataBaseHandler dbHandler;
    BottomNavigationView bottomNavigationView;
    String login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_layout);

        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN,0,0);

        bDeleteAccount= findViewById(R.id.btn2delete);
        bChangePassword= findViewById(R.id.btn1change);
        bLogOut = findViewById(R.id.btn3logout);

        twLogin= findViewById(R.id.tw2login);
        twName= findViewById(R.id.tw1name);

        bChangePassword.setOnClickListener(this);
        bDeleteAccount.setOnClickListener(this);
        bLogOut.setOnClickListener(this);

        etNewPass= findViewById(R.id.et1newpass);

        dbHandler = new DataBaseHandler(this);

        login = Objects.requireNonNull(getIntent().getExtras()).getString("login");

        twLogin.setText(login);

        new Thread(new Runnable() {
            @Override
            public void run() {
                twName.post(new Runnable() {
                    @Override
                    public void run() {
                        twName.setText(dbHandler.getName(login));
                    }
                });
            }
        }).start();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.bottom_profile)
                return true;
            else if (id == R.id.bottom_list)
            {
                Intent listIntent = new Intent(getApplicationContext(), ListActivity.class);
                listIntent.putExtra("login", login);
                startActivity(listIntent);
                overridePendingTransition(0,0);
                finish();
                return true;
            }
            return false;
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btn1change) {
            String newPass = etNewPass.getText().toString();
            etNewPass.setText("");
            etNewPass.clearFocus();
            if(newPass.isEmpty()) {
                Toast.makeText(AccountActivity.this,
                        "The password cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if(newPass.length()<5) {
                Toast.makeText(AccountActivity.this,
                        "Password must be longer than 5 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dbHandler.changePassword(login,newPass);
                }
            }).start();

            Toast.makeText(AccountActivity.this,
                    "Password successfully changed", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.btn2delete){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Delete your account?");
            builder.setMessage("The account will be permanently deleted and cannot be restored");
            builder.setIcon(R.drawable.delete_acc_icon);
            builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            dbHandler.removeUser(login);
                        }
                    }).start();
                    finish();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else if(id == R.id.btn3logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Log Out");
            builder.setMessage("Are you sure you want to log out of the account?");
            builder.setIcon(R.drawable.logout_icon);
            builder.setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
