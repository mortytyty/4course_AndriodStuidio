package com.example.lb02.Handlers;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.lb02.Models.User;


public class ThreadTask {
    Handler thr_handler;
    final Message message = Message.obtain();

    private final DataBaseHandler dbHandler;

    public ThreadTask(Handler main_handler, Context context){
        this.thr_handler = main_handler;
        dbHandler = new DataBaseHandler(context);
    }

    public void checkValid(String login, String pass) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                if(dbHandler.checkDataValidation(login, pass))
                {
                    message.sendingUid = 1;
                    message.obj = login;
                }else
                {
                    message.sendingUid = 2;
                    message.obj = "block";
                }
                thr_handler.sendMessage(message);
            }
        }).start();
    }

    public void checkExistAndReg(String login,String pass, String fName) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                message.sendingUid = 3;

                if(!dbHandler.checkUserLoginExist(login))
                {
                    dbHandler.addUser(new User(fName,login,pass));
                    message.obj = "success";
                }else
                    message.obj = "fail";

                thr_handler.sendMessage(message);
            }
        }).start();
    }

}
