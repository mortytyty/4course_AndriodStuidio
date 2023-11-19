package com.example.lb02;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected int selectedItemPos = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        EditText editText = findViewById(R.id.et1);

        Button addButton = findViewById(R.id.btn1add);
        Button removeButton = findViewById(R.id.btn2rem);
        Button clearButton = findViewById(R.id.btn3clr);

        ListView listView = findViewById(R.id.lw1);

        ArrayList<String>notes = new ArrayList<>();

        ArrayAdapter<String> textAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, notes);

        listView.setAdapter(textAdapter);

        listView.setOnItemClickListener((parent, v, position, id) -> selectedItemPos = position);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = editText.getText().toString();
                if(!note.isEmpty()){
                    notes.add(note);
                    editText.setText("");
                    textAdapter.notifyDataSetChanged();
                }
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedItemPos!=-1){
                    notes.remove(selectedItemPos);
                    selectedItemPos= -1;
                    listView.clearChoices();
                    textAdapter.notifyDataSetChanged();
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!notes.isEmpty()){
                    notes.clear();
                    textAdapter.notifyDataSetChanged();
                }
            }
        });





    }
}