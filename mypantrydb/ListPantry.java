package com.example.henry.mypantrydb;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Henry on 10/21/2017.
 */

public class ListPantry extends AppCompatActivity {
    private static final String TAG = "ListPantry";
    DatabaseHelper myDb;
    private ListView PantryView;
    private ListView PantryView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantry_layout);
        PantryView = (ListView) findViewById(R.id.ListView);
        PantryView2 = (ListView) findViewById(R.id.ListView2);
        myDb = new DatabaseHelper(this);
        
        populateListView();
    }

    private void populateListView() {
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }
        ArrayList<String> listData = new ArrayList<>();
        ArrayList<String> listData2 = new ArrayList<>();
        while (res.moveToNext()){
            listData.add(res.getString(0));
            listData2.add(res.getString(1));

        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData);
        ListAdapter adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData2);
        PantryView.setAdapter(adapter);
        PantryView2.setAdapter(adapter2);
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
