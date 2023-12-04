package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Метод onCreate","Создание или перезапуск активити");
        setContentView(R.layout.authorization);

        DataBase db = new DataBase(this);

        EditText login = findViewById(R.id.login);
        EditText password = findViewById(R.id.passwordd);
        Button loginbtn = findViewById(R.id.loginbtn);
        Button regbtn = findViewById(R.id.regbtn);
        ImageButton gear = findViewById(R.id.settings);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredLogin = login.getText().toString();
                String enteredPassword = password.getText().toString();

                Intent openIntent = new Intent(MainActivity.this, SecondActivity.class);

                db.open();
                //Проверка записи на существование в БД и авторизация
                if(db.checkUser(enteredLogin, enteredPassword) && !enteredLogin.isEmpty() && !enteredPassword.isEmpty()) {
                    //openIntent.putExtra("login", enteredLogin);
                    //openIntent.putExtra("password", enteredPassword);
                    openIntent.putExtra("hello", "Hello!");
                    startActivity(openIntent);
                    db.close();
                    Log.i("Авторизация", "Авторизация успешна");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Авторизация успешна!", Toast.LENGTH_SHORT);
                } else {
                    db.close();
                    Log.i("Ошибка авторизации", "Ошибка авторизации!");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Необходимо зарегистрироваться!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(MainActivity.this, Registration.class);
                startActivity(regIntent);
            }
        });

        gear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(MainActivity.this, Settings.class);
                startActivity(settingsIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Метод onStart","Работа приложения возобновлена");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Метод onResume","Приложение работает");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Метод onPause","Работа приложения приостановлена");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Метод onStop","Работа приложения завершена");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Метод onRestart","Приложение перезапущено");
        EditText login = findViewById(R.id.login);
        EditText password = findViewById(R.id.passwordd);
        login.setText("");
        password.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Метод onDestroy","Окончание работы активити");
    }

}