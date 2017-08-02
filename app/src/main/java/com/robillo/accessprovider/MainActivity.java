package com.robillo.accessprovider;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

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

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getContacts();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteId = idDelete.getText().toString();
                long id = contentResolver.delete(CONTENT_URI, "id = ?", new String[]{deleteId});
//                getContacts();
            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addId = idDelete.getText().toString();
                String[] projection = {"id", "name"};
                Cursor cursor = contentResolver.query(CONTENT_URI, projection,  "id = ?", new String[]{addId}, null);
                String mContact = "";
                //noinspection ConstantConditions
                if(cursor.moveToFirst()){
                    do{
                        String id = cursor.getString(cursor.getColumnIndex("id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        mContact = mContact + id + " : " + name + "\n";
                    }while (cursor.moveToFirst());
                }
                cursor.close();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameToAdd = nameAdd.getText().toString();
                ContentValues values = new ContentValues();
                values.put("name", nameToAdd);
                contentResolver.insert(CONTENT_URI, values);

//                getContacts();
            }
        });

//        getLoaderManager().initLoader(1, null, this);
//        getContacts();
    }

    public void getContacts(){
        String[] projection = new String[]{"id", "name"};
        Cursor cursor = contentResolver.query(CONTENT_URI, projection, null, null, null);
        String mContactsList = "";
        //noinspection ConstantConditions
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                mContactsList = mContactsList + id + " : " + name + "\n";
            }while (cursor.moveToFirst());
        }
        cursor.close();
        results.setText(mContactsList);
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {
        String mContactsList = "";
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                mContactsList = mContactsList + id + " : " + name + "\n";
            }while (cursor.moveToFirst());
        }
        cursor.close();
        results.setText(mContactsList);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }
}
