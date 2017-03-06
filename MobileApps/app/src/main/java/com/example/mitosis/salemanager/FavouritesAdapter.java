package com.example.mitosis.salemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mitosis on 2/2/17.
 */




public class FavouritesAdapter extends ArrayAdapter<String> {
    private final Context context;

    ImageView start;
    TextView text_new;


    ArrayList<String> name=new ArrayList<String>();
    ArrayList<String> hospital=new ArrayList<String>();


    public FavouritesAdapter(Context context, ArrayList<String> hospital, ArrayList<String> name) {
        super(context, R.layout.today,name);
        this.getContext();
        this.context = context;
        this.name = name;
        this.hospital=hospital;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.today, parent, false);

        start = (ImageView) rowView.findViewById(R.id.start_image);
        text_new = (TextView) rowView.findViewById(R.id.start_new);
        text_new.setVisibility(View.GONE);
        TextView text2=(TextView)rowView.findViewById(R.id.doconame);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setVisibility(View.GONE);
                text_new.setVisibility(View.VISIBLE);

            }
        });

        text_new.setText(name.get(position));
        text2.setText(hospital.get(position));

        return rowView;


    }

}

