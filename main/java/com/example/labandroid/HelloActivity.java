package com.example.labandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class HelloActivity extends Activity {
    int pressCount1 = 0;
    int pressCount2 = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloact);

        TextView first = findViewById(R.id.text_b1);
        TextView second = findViewById(R.id.text_b2);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pressCount1++;
                String message1 = "Число нажатий на первую кнопку: " + pressCount1;
                //first.setText(Integer.toString(pressCount1));
                first.setText(message1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pressCount2++;
                String message2 = "Число нажатий на вторую кнопку: " + pressCount2;
                second.setText(message2);

            }
        });
    }
}

