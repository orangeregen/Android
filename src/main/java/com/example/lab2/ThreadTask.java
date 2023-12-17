package com.example.lab2;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import android.os.Message;
import android.os.Handler;

public class ThreadTask {
    Handler thr_handler;
    DataBase db;
    final Message message = Message.obtain();

    ThreadTask(Handler main_handler, DataBase db){
        this.thr_handler = main_handler;
        this.db = db;
    }

    public void loginTask(String login, String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (db.checkUser(login, password)) {

                    message.obj = TRUE;

                } else message.obj = FALSE;
                message.sendingUid = 1;
                thr_handler.sendMessage(message);
            }
        }).start();
    }

    public void registrationTask(String login, String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (db.getUserByUsername(login)) {
                    message.obj = FALSE;
                } else {
                    db.addUser(login, password);
                    message.obj = TRUE;
                }
                message.sendingUid = 2;
                thr_handler.sendMessage(message);
            }
        }).start();
    }

    public void deleteTask(String login) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!db.getUserByUsername(login)) {
                    message.obj = FALSE;
                } else {
                    db.deleteUser(login);
                    message.obj = TRUE;
                }
                message.sendingUid = 3;
                thr_handler.sendMessage(message);
            }
        }).start();
    }

    public void changePswdTask(String login, String password, String oldPassword) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!db.checkUser(login, password)) {
                    message.obj = FALSE;
                } else {
                    db.changePswd(login, password, oldPassword);
                    message.obj = TRUE;
                }
                message.sendingUid = 4;
                thr_handler.sendMessage(message);
            }
        }).start();
    }
}