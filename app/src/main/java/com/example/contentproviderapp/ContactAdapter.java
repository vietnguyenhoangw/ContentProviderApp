package com.example.contentproviderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {

    Context context;
    ArrayList<Contact> contactArrayList;
    int layout;


    public ContactAdapter(Context context, int layout, ArrayList<Contact> contactArrayList) {
        super(context, layout, contactArrayList);
        this.context = context;
        this.layout = layout;
        this.contactArrayList = contactArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(layout, null);

        TextView userName = convertView.findViewById(R.id.userName);
        TextView userPhoneNums = convertView.findViewById(R.id.userPhoneNumber);
        TextView userEmail = convertView.findViewById(R.id.userEmail);

        Contact contact = contactArrayList.get(position);

        userName.setText(contact.getName());
        userPhoneNums.setText(contact.getPhone());
        userEmail.setText(contact.getEmail());

        return convertView;
    }
}
