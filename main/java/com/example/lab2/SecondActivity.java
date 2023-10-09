package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);

        Button add = findViewById(R.id.addbtn);
        Button delete = findViewById(R.id.deletebtn);
        ListView textList = findViewById(R.id.textList);
        EditText set = findViewById(R.id.settxt);

        ArrayList<String> myStringArray = new ArrayList<String>();
        ArrayList<String> chDel = new ArrayList<String>();

        ArrayAdapter<String> TextAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, myStringArray);

        textList.setAdapter(TextAdapter);
        myStringArray.add("Кто тут?");
        TextAdapter.notifyDataSetChanged();

        myStringArray.add(getIntent().getStringExtra("hello"));
        myStringArray.add(getIntent().getStringExtra("login"));
        myStringArray.add(getIntent().getStringExtra("password"));


       // Intent openIntent = getIntent();
       //Bundle arguments = getIntent().getExtras();
        //String str = arguments.get("hello").toString();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = set.getText().toString();
                if (!text.isEmpty()) {
                    myStringArray.add(text);
                    TextAdapter.notifyDataSetChanged();
                    set.setText("");
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Данные не были введены!", Toast.LENGTH_SHORT);
                    toast.show();
                    TextAdapter.notifyDataSetChanged();
                }
            }
        });

        textList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String forDelete = TextAdapter.getItem(position);
                SparseBooleanArray chosen = ((ListView) parent).getCheckedItemPositions();

                for (int i = 0; i < chosen.size(); i++) {
                    if (chosen.valueAt(i)) {
                        chDel.add(forDelete);
                    }
                }

                //Вариант 1. Удаление нажатием на элемент в списке
                //Найти позицию элемента в списке
                //int positionInList = textList.getPositionForView(view);
                //Вариант 1. Удаление нажатием на элемент в списке
                //myStringArray.remove(position);

                //Вариант 1. Удаление нажатием на элемент в списке
                //TextAdapter.notifyDataSetChanged();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i =0; i<chDel.size(); i++) {
                    myStringArray.remove(chDel.get(i));
                    TextAdapter.notifyDataSetChanged();
                }

                textList.clearChoices();
                chDel.clear();

               /* //Вариант2. Удаление с помощью кнопки.
                //Надо ввести текст удаляемого элемента в поле
                for (int i =0; i<myStringArray.size(); i++) {
                    String name = set.getText().toString();
                    if (myStringArray.get(i).equals(name)) {
                        myStringArray.remove(i);
                        TextAdapter.notifyDataSetChanged();
                        set.setText("");
                    }
                }*/
            }
        });
    }
}