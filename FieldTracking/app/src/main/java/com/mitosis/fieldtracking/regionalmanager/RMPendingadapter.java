package com.mitosis.fieldtracking.regionalmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mitosis.fieldtracking.R;

import java.util.ArrayList;

/**
 * Created by mitosis on 3/4/17.
 */

public class RMPendingadapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> contactName = new ArrayList<String>();
    private ArrayList<String> statusArr = new ArrayList<String>();
    private ArrayList<String> leadname = new ArrayList<String>();
    private ArrayList<String> telephonenumber = new ArrayList<String>();
    private ArrayList<String> mobileNumber = new ArrayList<String>();
    private ArrayList<String> email = new ArrayList<String>();
    private ArrayList<String> addressLine1 = new ArrayList<String>();
    private ArrayList<String> addressLine2 = new ArrayList<String>();
    private ArrayList<String> landMark = new ArrayList<String>();
    private ArrayList<String> pcity = new ArrayList<String>();
    private ArrayList<String> pstate = new ArrayList<String>();
    private ArrayList<String> pzipCode = new ArrayList<String>();
    private ArrayList<String> leadDetailsIdd = new ArrayList<String>();
    private ArrayList<String> imageUrll = new ArrayList<String>();
    private ArrayList<String> repIdd = new ArrayList<String>();
    private ArrayList<String> latitude = new ArrayList<String>();
    private ArrayList<String> longitude = new ArrayList<String>();
    private ArrayList<String> pappointmentDate = new ArrayList<String>();
    private ArrayList<String> notes = new ArrayList<String>();
TextView doconame,milez,tyetstarted,tstarted,treacheded ,tattened;
    ImageView startimage,startyet,attend,reached,imageLead;

    public RMPendingadapter(Context context, ArrayList<String> pcontactName, ArrayList<String> status, ArrayList<String> leadname,
                            ArrayList<String> telephonenumber, ArrayList<String> mobileNumber, ArrayList<String> email, ArrayList<String> addressLine1,
                            ArrayList<String> addressLine2, ArrayList<String> pcity, ArrayList<String> pstate, ArrayList<String> pzipCode,
                            ArrayList<String> landMark, ArrayList<String> leadDetailsIdd, ArrayList<String> imageUrll, ArrayList<String> repIdd,
                            ArrayList<String> latitude, ArrayList<String> longitude, ArrayList<String> pappointmentDate, ArrayList<String> notes) {

        super(context, R.layout.rmpendingtab, pcontactName);


        this.context = context;
        this.contactName = pcontactName;
        this.statusArr = status;
        this.leadname = leadname;
        this.telephonenumber = telephonenumber;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.pcity = pcity;
        this.pstate = pstate;
        this.pzipCode = pzipCode;
        this.landMark = landMark;
        this.leadDetailsIdd = leadDetailsIdd;
        this.imageUrll = imageUrll;
        this.repIdd = repIdd;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pappointmentDate=pappointmentDate;
        this.notes = notes;


    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.rmpendingtab, parent, false);
        doconame=(TextView)rowView.findViewById(R.id.doconame);
        doconame.setText(contactName.get(position));
        milez = (TextView) rowView.findViewById(R.id.text_miles);

        tyetstarted=(TextView)rowView.findViewById(R.id.tyetstarted);
        tstarted=(TextView)rowView.findViewById(R.id.tstarted);
        treacheded=(TextView)rowView.findViewById(R.id.treacheded);
        tattened=(TextView)rowView.findViewById(R.id.tattened);

        startimage=(ImageView) rowView.findViewById(R.id.imagestart);
        startyet = (ImageView) rowView.findViewById(R.id.yetstart);
        attend = (ImageView) rowView.findViewById(R.id.attend);
        reached = (ImageView) rowView.findViewById(R.id.reached);
        imageLead = (ImageView) rowView.findViewById(R.id.image_lead);
        startyet.setVisibility(View.VISIBLE);
        attend.setVisibility(View.GONE);
        reached.setVisibility(View.GONE);
        startimage.setVisibility(View.GONE);
        tstarted.setVisibility(View.GONE);
        treacheded.setVisibility(View.GONE);
        tattened.setVisibility(View.GONE);
        tyetstarted.setVisibility(View.VISIBLE);
        if(statusArr.get(position).equals("Yet to Start"))
        {
            attend = (ImageView) rowView.findViewById(R.id.attend);
            reached = (ImageView) rowView.findViewById(R.id.reached);
            startyet = (ImageView) rowView.findViewById(R.id.yetstart);
            startimage=(ImageView) rowView.findViewById(R.id.imagestart);
            attend.setVisibility(View.GONE);
            reached.setVisibility(View.GONE);
            startimage.setVisibility(View.GONE);
            startyet.setVisibility(View.VISIBLE);
            tyetstarted=(TextView)rowView.findViewById(R.id.tyetstarted);
            tstarted=(TextView)rowView.findViewById(R.id.tstarted);
            treacheded=(TextView)rowView.findViewById(R.id.treacheded);
            tattened=(TextView)rowView.findViewById(R.id.tattened);
            tstarted.setVisibility(View.GONE);
            treacheded.setVisibility(View.GONE);
            tattened.setVisibility(View.GONE);
            tyetstarted.setVisibility(View.VISIBLE);
        }
        if(statusArr.get(position).equals("STARTED"))
        {
            attend = (ImageView) rowView.findViewById(R.id.attend);
            reached = (ImageView) rowView.findViewById(R.id.reached);
            startyet = (ImageView) rowView.findViewById(R.id.yetstart);
            startimage=(ImageView) rowView.findViewById(R.id.imagestart);
            startyet.setVisibility(View.GONE);
            attend = (ImageView) rowView.findViewById(R.id.attend);
            reached = (ImageView) rowView.findViewById(R.id.reached);
            attend.setVisibility(View.GONE);
            reached.setVisibility(View.GONE);
            startimage=(ImageView) rowView.findViewById(R.id.imagestart);
            startimage.setVisibility(View.VISIBLE);
            tyetstarted=(TextView)rowView.findViewById(R.id.tyetstarted);
            tstarted=(TextView)rowView.findViewById(R.id.tstarted);
            treacheded=(TextView)rowView.findViewById(R.id.treacheded);
            tattened=(TextView)rowView.findViewById(R.id.tattened);
            tstarted.setVisibility(View.VISIBLE);
            treacheded.setVisibility(View.GONE);
            tattened.setVisibility(View.GONE);
            tyetstarted.setVisibility(View.GONE);

        }
        if(statusArr.get(position).equals("ATTENDED"))
        {
            attend = (ImageView) rowView.findViewById(R.id.attend);
            reached = (ImageView) rowView.findViewById(R.id.reached);
            startyet = (ImageView) rowView.findViewById(R.id.yetstart);
            attend.setVisibility(View.VISIBLE);
            reached.setVisibility(View.GONE);
            startyet.setVisibility(View.GONE);
            startimage=(ImageView) rowView.findViewById(R.id.imagestart);
            startimage.setVisibility(View.GONE);
            tyetstarted=(TextView)rowView.findViewById(R.id.tyetstarted);
            tstarted=(TextView)rowView.findViewById(R.id.tstarted);
            treacheded=(TextView)rowView.findViewById(R.id.treacheded);
            tattened=(TextView)rowView.findViewById(R.id.tattened);
            tstarted.setVisibility(View.GONE);
            treacheded.setVisibility(View.GONE);
            tattened.setVisibility(View.VISIBLE);
            tyetstarted.setVisibility(View.GONE);

        }
        if(statusArr.get(position).equals("REACHED"))
        {
            attend = (ImageView) rowView.findViewById(R.id.attend);
            reached = (ImageView) rowView.findViewById(R.id.reached);
            startyet = (ImageView) rowView.findViewById(R.id.yetstart);
            attend.setVisibility(View.GONE);
            reached.setVisibility(View.VISIBLE);
            startyet.setVisibility(View.GONE);
            startimage=(ImageView) rowView.findViewById(R.id.imagestart);
            startimage.setVisibility(View.GONE);
            tyetstarted=(TextView)rowView.findViewById(R.id.tyetstarted);
            tstarted=(TextView)rowView.findViewById(R.id.tstarted);
            treacheded=(TextView)rowView.findViewById(R.id.treacheded);
            tattened=(TextView)rowView.findViewById(R.id.tattened);
            tstarted.setVisibility(View.GONE);
            treacheded.setVisibility(View.GONE);
            tattened.setVisibility(View.VISIBLE);
            tyetstarted.setVisibility(View.GONE);

        }
        return rowView;
    }
}
