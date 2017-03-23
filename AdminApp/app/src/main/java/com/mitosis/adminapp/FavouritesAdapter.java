package com.mitosis.adminapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mitosis on 2/2/17.
 */

public class FavouritesAdapter extends ArrayAdapter<String> {

    private final Context context;

    TextView milez;
    TextView firstName,lastName,Role;

    ArrayList<String> templist=new ArrayList<String>();


    ArrayList<String> firstnameArr = new ArrayList<String>();
    ArrayList<String> lastnameArr = new ArrayList<String>();
    ArrayList<String> roleArr = new ArrayList<String>();

    public FavouritesAdapter(Context context, ArrayList<String> firstname, ArrayList<String> lastname, ArrayList<String> role) {
        super(context, R.layout.employee, firstname);
        this.getContext();
        this.context = context;
        this.firstnameArr = firstname;
        this.lastnameArr = lastname;
        this.roleArr = role;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.employee, parent, false);

        firstName = (TextView) rowView.findViewById(R.id.doconame);
        Role = (TextView) rowView.findViewById(R.id.text_role);

        firstName.setText(firstnameArr.get(position)+" "+lastnameArr.get(position));
        Role.setText(roleArr.get(position));

        return rowView;

    }
}
