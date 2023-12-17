package com.example.lb02;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    final String LifeCycleTag = "ActivityLifecycleState";
    EditText editText;
    Button addButton;
    Button removeButton;
    Button clearButton;
    ListView listView;
    ArrayList<String>notes;
    ArrayAdapter<String> textAdapter;
    ArrayList<String>selectedRows;
    Bundle arguments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Log.d(LifeCycleTag,"ListActivity created");

        arguments = getIntent().getExtras();

        editText = findViewById(R.id.et1);
        addButton = findViewById(R.id.btn1add);
        removeButton = findViewById(R.id.btn2rem);
        clearButton = findViewById(R.id.btn3clr);
        listView = findViewById(R.id.lw1);

        notes = new ArrayList<>();
        selectedRows = new ArrayList<>();
        textAdapter = new ArrayAdapter<>(this,R.layout.list_item_layout, notes);

        listView.setAdapter(textAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String note = notes.get(position);
                if(listView.isItemChecked(position)){
                    selectedRows.add(note);
                    //view.setBackgroundColor(Color.rgb(192,182,255));

                }else{
                    selectedRows.remove(note);
                    //view.setBackgroundColor(Color.WHITE);
                }
            }
        });

        addButton.setOnClickListener(v -> {
            String note = editText.getText().toString();
            addRow(note);
        });

        removeButton.setOnClickListener(v -> {
            removeRow();
        });

        clearButton.setOnClickListener(v -> clearList());
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LifeCycleTag,"ListActivity restarted");

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LifeCycleTag,"ListActivity started");
        Toast.makeText(ListActivity.this,
                "Welcome, "+arguments.getString("login")+"!", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LifeCycleTag,"ListActivity resumed");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LifeCycleTag,"ListActivity paused");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LifeCycleTag,"ListActivity stopped");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LifeCycleTag,"ListActivity destroyed");
    }
    private void addRow(String data){
        if(!data.isEmpty()){
            notes.add(data);
            editText.setText("");
            textAdapter.notifyDataSetChanged();
        }
    }
    private void removeRow(){
        if(!selectedRows.isEmpty()){
            for (String i : selectedRows){
                notes.remove(i);
            }
            selectedRows.clear();
            listView.clearChoices();
            textAdapter.notifyDataSetChanged();
        }
    }
    private void clearList(){
        if(!notes.isEmpty()){
            selectedRows.clear();
            notes.clear();
            listView.clearChoices();
            textAdapter.notifyDataSetChanged();
        }
    }
}