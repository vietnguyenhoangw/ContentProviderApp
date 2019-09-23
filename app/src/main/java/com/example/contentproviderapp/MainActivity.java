package com.example.contentproviderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Contact> contactArrayList;
    ContactAdapter contactAdapter;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        contactArrayList = new ArrayList<>();
        contactAdapter = new ContactAdapter(MainActivity.this, R.layout.contact_item, contactArrayList);

        listView.setAdapter(contactAdapter);

        readContact();
    }

    private void readContact() {


        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        ContentResolver cr = getContentResolver();

        Cursor cursor = cr.query(uri,null,null,null,null);

        while (cursor.moveToNext()){
            String id = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts._ID));

            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            Cursor cursor1 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" +id,
                    null, null);

            // get contact by id, add to phone
            ArrayList<String> phone = new ArrayList<>();

            while (cursor1.moveToNext()){
                String number  = cursor1.getString(
                        cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phone.add(number);
            }

            // get item in phone array list and add to Phone string
            String Phone = "";
            for (String item: phone) {
                Phone += item + "\n";
            }
            Cursor cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" +id,
                    null, null);

            ArrayList<String> email = new ArrayList<>();

            while (cursor2.moveToNext()){
                String address  = cursor2.getString(
                        cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                email.add(address);
            }

            // add phone, email, name to Contact Object and add to to arraylist
            contactArrayList.add(new Contact(name, Phone, email.get(0).toString()));
        }
        contactAdapter.notifyDataSetChanged();
    }
}
