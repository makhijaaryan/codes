package com.example.lab8q1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, emailEditText;
    private TextView displayTextView;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        displayTextView = findViewById(R.id.displayTextView);

        // Initialize the database
        database = openOrCreateDatabase("ContactBook", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS contacts (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT, email TEXT)");

        displayContacts();
    }

    public void saveContact(View view) {
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);

        database.insert("contacts", null, contentValues);

        nameEditText.setText("");
        phoneEditText.setText("");
        emailEditText.setText("");

        displayContacts();
    }

    private void displayContacts() {
        Cursor cursor = database.rawQuery("SELECT * FROM contacts", null);

        StringBuilder builder = new StringBuilder();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            String email = cursor.getString(3);

            builder.append("ID: ").append(id).append("\n");
            builder.append("Name: ").append(name).append("\n");
            builder.append("Phone: ").append(phone).append("\n");
            builder.append("Email: ").append(email).append("\n\n");
        }

        displayTextView.setText(builder.toString());
    }

    public void editContact(View view) {
        String idStr = ((EditText) findViewById(R.id.editIdEditText)).getText().toString().trim();
        String name = ((EditText) findViewById(R.id.editNameEditText)).getText().toString().trim();
        String phone = ((EditText) findViewById(R.id.editPhoneEditText)).getText().toString().trim();
        String email = ((EditText) findViewById(R.id.editEmailEditText)).getText().toString().trim();

        if (idStr.isEmpty() || name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(idStr);

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);

        database.update("contacts", contentValues, "id=?", new String[]{String.valueOf(id)});

        displayContacts();
    }
}
