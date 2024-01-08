package com.example.lb02;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class ListActivity extends AppCompatActivity {

    final String LifeCycleTag = "ActivityLifecycleState";
    final String ShPrefEdit = "SharedPreferencesEdit";
    EditText editText;
    Button addButton;
    Button removeButton;
    Button clearButton;
    ListView listView;
    ArrayList<String>notes;
    ArrayAdapter<String> textAdapter;
    ArrayList<String>selectedRows;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor spEditor;
    BottomNavigationView bottomNavigationView;
    String login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Log.d(LifeCycleTag,"ListActivity created");

        login = Objects.requireNonNull(getIntent().getExtras()).getString("login");

        editText = findViewById(R.id.et1);
        addButton = findViewById(R.id.btn1add);
        removeButton = findViewById(R.id.btn2rem);
        clearButton = findViewById(R.id.btn3clr);
        listView = findViewById(R.id.lw1);

        notes = new ArrayList<>();
        selectedRows = new ArrayList<>();
        textAdapter = new ArrayAdapter<>(this,R.layout.list_item_layout, notes);

        listView.setAdapter(textAdapter);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_list);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.bottom_list)
                return true;
            else if (id == R.id.bottom_profile)
            {
                Intent accountIntent = new Intent(getApplicationContext(), AccountActivity.class);
                accountIntent.putExtra("login", login);
                startActivity(accountIntent);
                finish();
                return true;
            }
            return false;
        });

        loadList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String note = notes.get(position);
                if(listView.isItemChecked(position)){
                    selectedRows.add(note);
                }else{
                    selectedRows.remove(note);
                }
            }
        });

        addButton.setOnClickListener(v -> {
            String note = editText.getText().toString();
            addRow(note);
        });

        removeButton.setOnClickListener(v -> removeRow());

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
        saveList();
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
    private void saveList(){
        try {
            sharedPreferences = getPreferences(MODE_PRIVATE);
            spEditor = sharedPreferences.edit();

            StringBuilder line = new StringBuilder();
            for (String i : notes) {
                line.append(i).append(":");
            }

            spEditor.putString("list", line.toString());
            spEditor.apply();

            Log.d(ShPrefEdit, "List saved, items:" + notes.size());

        }catch(Exception ex){
            Log.e("LoadListError",ex.toString());
        }
    }
    private void loadList(){
        try {
            sharedPreferences = getPreferences(MODE_PRIVATE);
            String line = sharedPreferences.getString("list", "");
            if(line.isEmpty())return;

            String[] arr = line.split(":");
            Collections.addAll(notes, arr);
            textAdapter.notifyDataSetChanged();
            Log.d(ShPrefEdit, "List loaded, items:" + notes.size());
        }catch(Exception ex){
            Log.e("SaveListError",ex.toString());
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