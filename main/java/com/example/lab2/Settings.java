package com.example.lab2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        EditText login = findViewById(R.id.login);
        EditText oldPassword = findViewById(R.id.oldPswd);
        EditText newPassword = findViewById(R.id.newPswd);
        Button changeBtn = findViewById(R.id.chngBtn);
        EditText loginDel = findViewById(R.id.loginDel);
        Button deleteBtn = findViewById(R.id.delBtn);

        DataBase db = new DataBase(this);

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String log = login.getText().toString();
                String oldPswd = oldPassword.getText().toString();
                String newPswd = newPassword.getText().toString();
                db.open();
                DataBase.User change = db.changePswd(log, oldPswd, newPswd);
                if (change == null) {
                    login.setText("");
                    oldPassword.setText("");
                    newPassword.setText("");

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Пароль изменен!", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Не удалось изменить пароль!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                db.close();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userToDel = loginDel.getText().toString();

                db.open();
                DataBase.User userDel = db.getUserByUsername(userToDel);

                if (userDel != null) {
                    db.deleteUser(userToDel);
                    loginDel.setText("");

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Пользователь удален!", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Пользователь НЕ удален!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                db.close();
            }
        });
    }
}
