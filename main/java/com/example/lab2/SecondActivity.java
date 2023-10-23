package com.example.lab2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.Arrays;

public class SecondActivity extends AppCompatActivity {
    private ArrayList<String> myStringArray;
    private ArrayList<String> chDel;
    private ArrayAdapter<String> TextAdapter;
    private String dataToString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.data);
        Button add = findViewById(R.id.addbtn);
        Button delete = findViewById(R.id.deletebtn);
        ListView textList = findViewById(R.id.textList);
        EditText set = findViewById(R.id.settxt);
        Button deleteAll = findViewById(R.id.deleteAll);

        myStringArray = new ArrayList<String>();
        chDel = new ArrayList<String>();

        TextAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, myStringArray);

        textList.setAdapter(TextAdapter);
        //myStringArray.add("Кто тут?");
        TextAdapter.notifyDataSetChanged();

        //myStringArray.add(getIntent().getStringExtra("hello"));
        myStringArray.add(getIntent().getStringExtra("login"));
        myStringArray.add(getIntent().getStringExtra("password"));

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

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStringArray.clear();
                TextAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Метод onPause","OnPause SecondActivity");

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        dataToString = TextUtils.join(",", myStringArray);
        editor.putString("dataList", dataToString);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Метод onResume","onResume SecondActivity");

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        dataToString = sharedPref.getString("dataList", "");

        if (!TextUtils.isEmpty(dataToString)) {
            String[] dataToArray = dataToString.split(",");
            myStringArray.addAll(Arrays.asList(dataToArray));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Метод onResume","onStop SecondActivity");
        myStringArray.clear();
        TextAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Метод onResume","onDestroy SecondActivity");

        myStringArray.clear();
        TextAdapter.notifyDataSetChanged();
    }
}