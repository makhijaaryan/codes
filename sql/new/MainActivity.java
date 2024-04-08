package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText nameText, newNameText;
    TextView data;
    Button saveBtn, updatebtn;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = openOrCreateDatabase("db1",MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS CONTACTS(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");

        nameText=findViewById(R.id.nameEditText);
        newNameText=findViewById(R.id.editTextText2);

        saveBtn=findViewById(R.id.buttonSave);
        updatebtn=findViewById(R.id.buttonUpdate);

        data=findViewById(R.id.textView2);



        displayData();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameText.getText().toString().trim();
                database.execSQL("insert into CONTACTS (name) values ('"+name+"')");
//                database.insert("CONTACTS",null,name);
                displayData();
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName=newNameText.getText().toString().trim();
                int idUpdate=1;
                database.execSQL("update contacts set name='"+newName+"' where id="+idUpdate);
                displayData();
            }
        });



    }
    void displayData(){
        Cursor cursor = database.rawQuery("SELECT * FROM contacts", null);

        StringBuilder builder = new StringBuilder();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);


            builder.append("ID: ").append(id).append("\n");
            builder.append("Name: ").append(name).append("\n\n");

        }


        data.setText(builder.toString());
        cursor.close();
    }
}