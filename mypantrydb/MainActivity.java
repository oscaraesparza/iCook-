package com.example.henry.mypantrydb;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "PantryMain";
    DatabaseHelper myDb;
    EditText editItem, editQuantity;
    Button btnAddData;
    Button btnViewAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editItem = (EditText) findViewById(R.id.editText_Item);
        editQuantity = (EditText) findViewById(R.id.editText_Quantity);
        btnAddData = (Button) findViewById(R.id.btn_Add);
        btnViewAll = (Button) findViewById(R.id.btn_View);
        addData();
        viewAll();
    }
    public void addData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                       boolean isInserted = myDb.insertData(editItem.getText().toString(),
                               Integer.parseInt(editQuantity.getText().toString()));
                        if(isInserted = true){
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                            editItem.setText("");
                            editQuantity.setText("");
                        }else{
                            Toast.makeText(MainActivity.this,"Data Not Inserted",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void viewAll(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ListPantry.class);
                        startActivity(intent);
                    }
                        /*Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("Item :" + res.getString(0) + "\n");
                            buffer.append("Quantity :" + res.getString(1) + "\n\n");

                        }

                        // Show all data
                        showMessage("Data",buffer.toString());
                    }*/
                }
        );
    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
