package com.robillo.accessprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button show, delete, find, add;
    EditText idDelete, idFind, nameAdd;
    TextView results;
    ContentResolver contentResolver;

    static final Uri CONTENT_URI = Uri.parse("content://com.robillo.contentprovider.MyProvider/mcontacts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentResolver = getContentResolver();

        show = (Button) findViewById(R.id.show);
        delete = (Button) findViewById(R.id.delete);
        find = (Button) findViewById(R.id.find);
        add = (Button) findViewById(R.id.add);
        idDelete = (EditText) findViewById(R.id.id_delete);
        idFind = (EditText) findViewById(R.id.id_find);
        nameAdd = (EditText) findViewById(R.id.name_add);
        results = (TextView) findViewById(R.id.results);

//        getContacts();
    }

    public void getContacts(){
        String[] projection = new String[]{"id", "name"};
        Cursor cursor = contentResolver.query(CONTENT_URI, projection, null, null, null);
        String mContactsList = "";
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                mContactsList = id + " : " + name + "\n";
            }while (cursor.moveToFirst());
        }
        cursor.close();
        results.setText(mContactsList);
    }
}
