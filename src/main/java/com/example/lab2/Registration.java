package com.example.lab2;

import static java.lang.Boolean.TRUE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {
    DataBase db = new DataBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        EditText login = findViewById(R.id.login);
        EditText pswd = findViewById(R.id.pswd);
        EditText secPswd = findViewById(R.id.secPswd);
        Button regbtn = findViewById(R.id.regbtn);

        final Looper looper = Looper.getMainLooper();

        Handler handler = new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.sendingUid == 2) {
                    if (msg.obj == TRUE) {
                        login.setText("");
                        pswd.setText("");
                        secPswd.setText("");
                        Toast.makeText(getApplicationContext(),
                                "Регистрация успешна!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(),
                                "Пользователь уже существует!", Toast.LENGTH_SHORT).show();
                }
            }
        };

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regLogin = login.getText().toString();
                String regPswd = pswd.getText().toString();
                String secondPswd = secPswd.getText().toString();

                db.open();

                if(regLogin.isEmpty() || regPswd.isEmpty() || secondPswd.isEmpty()) {
                   Toast.makeText(getApplicationContext(),
                            "Необходимо заполнить поля: Логин, Пароль, Повтор пароля!", Toast.LENGTH_SHORT).show();
                } else if(!regPswd.equals(secondPswd)) {
                   Toast.makeText(getApplicationContext(),
                            "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
                } else {
                    new ThreadTask(handler, db).registrationTask(regLogin, regPswd);
                }

                /*else if(db.getCountUser(regLogin, regPswd) >= 1) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Пользователь уже существует!", Toast.LENGTH_SHORT);
                    toast.show();
                    db.close();
                } else{
                    long result = db.addUser(regLogin, regPswd);
                    db.close();

                    if (result != -1) {
                        login.setText("");
                        pswd.setText("");
                        secPswd.setText("");
                        Log.i("Регистрация", "Регистрация успешна");
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Регистрация успешна!", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Log.e("Ошибка регистрации", "Ошибка при добавлении пользователя");
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Ошибка при регистрации", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }*/
            }
        });
    }
}