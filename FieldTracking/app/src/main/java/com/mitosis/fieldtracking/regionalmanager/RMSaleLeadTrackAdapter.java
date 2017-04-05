package com.mitosis.fieldtracking.regionalmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mitosis.fieldtracking.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mitosis on 27/3/17.
 */
public class RMSaleLeadTrackAdapter extends ArrayAdapter<String> {
    private Context context;
    TextView name,tyetstarted,tstarted,treacheded ,tattened,descript;
    private ArrayList<String> contactName=new ArrayList<String>();
    private ArrayList<String>  statusArr=new ArrayList<String>();
    ImageView startimage,startyet,attend,reached,imageLead;

    private ArrayList<String>  addressLine1=new ArrayList<String>();
    private ArrayList<String>  addressLine2=new ArrayList<String>();
    private ArrayList<String>  city=new ArrayList<String>();
    private ArrayList<String>  state=new ArrayList<String>();
    private ArrayList<String>  zipCode=new ArrayList<String>();
    private ArrayList<String>  country=new ArrayList<String>();
    private ArrayList<String>  landMark=new ArrayList<String>();
    private ArrayList<String>  leadDetailsId=new ArrayList<String>();
    private ArrayList<String>  imageUrl=new ArrayList<String>();
    private ArrayList<String>  repId=new ArrayList<String>();
    private ArrayList<String>  appointmentDate=new ArrayList<String>();
    private ArrayList<String>  latitide=new ArrayList<String>();
    private ArrayList<String>  longitude=new ArrayList<String>();
    private ArrayList<String>  notes=new ArrayList<String>();
    ImageView notification;
   // TextView alternotification;

    TextView milez;

    private ArrayList<String>  distanceArr=new ArrayList<String>();

    public static String repid;
    public RMSaleLeadTrackAdapter(Context context, ArrayList<String> contactName, ArrayList<String> status,
                                  ArrayList<String> leadname, ArrayList<String> mobileNumber,
                                  ArrayList<String> addressLine1, ArrayList<String> addressLine2,
                                  ArrayList<String> city, ArrayList<String> state,
                                  ArrayList<String> zipCode,
                                  ArrayList<String> country, ArrayList<String> landMark,
                                  ArrayList<String> leadDetailsId,
                                  ArrayList<String> imageUrl, ArrayList<String> repId, ArrayList<String> appointmentDate,
                                  ArrayList<String> latitide, ArrayList<String> longitude, ArrayList<String> notes, ArrayList<String> distanceArr )  {
        super(context, R.layout.rmsaleleadtrackadapter, contactName);
        this.context=context;
        this.contactName=contactName;
        this.statusArr=status;
        this.addressLine1=addressLine1;
        this.addressLine2=addressLine2;
        this.city=city;
        this.state=state;
        this.zipCode=zipCode;
        this.country=country;
        this.landMark=landMark;
        this.leadDetailsId=leadDetailsId;
        this.imageUrl=imageUrl;
        this.repId=repId;
        this.appointmentDate=appointmentDate;
        this.latitide=latitide;
        this.longitude=longitude;
        this.notes=notes;
        this.distanceArr=distanceArr;
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.rmsaleleadtrackadapter, parent, false);
        name=(TextView)rowView.findViewById(R.id.doconame) ;
        name.setText(contactName.get(position));
        notification=(ImageView)rowView.findViewById(R.id.notes);
        milez = (TextView) rowView.findViewById(R.id.text_miles);
        descript = (TextView) rowView.findViewById(R.id.hname);

        startimage=(ImageView) rowView.findViewById(R.id.imagestart);
        tyetstarted=(TextView)rowView.findViewById(R.id.tyetstarted);
        tstarted=(TextView)rowView.findViewById(R.id.tstarted);
        treacheded=(TextView)rowView.findViewById(R.id.treacheded);
        tattened=(TextView)rowView.findViewById(R.id.tattened);

        startimage=(ImageView) rowView.findViewById(R.id.imagestart);
        startyet = (ImageView) rowView.findViewById(R.id.yetstart);
        attend = (ImageView) rowView.findViewById(R.id.attend);
        reached = (ImageView) rowView.findViewById(R.id.reached);
        imageLead = (ImageView) rowView.findViewById(R.id.image_lead);
        //notification.setVisibility(View.GONE);
        startyet.setVisibility(View.VISIBLE);
        attend.setVisibility(View.GONE);
        reached.setVisibility(View.GONE);
        startimage.setVisibility(View.GONE);
        tstarted.setVisibility(View.GONE);
        treacheded.setVisibility(View.GONE);
        tattened.setVisibility(View.GONE);
        tyetstarted.setVisibility(View.VISIBLE);
        repid=repId.get(position);
     //   milez.setText(distanceArr.get(position));
        milez.setText(distanceArr.get(position)+" "+"Meters Away");


        if(distanceArr.size()!=0) {

milez.setText(distanceArr.get(position)+" "+"Meters Away");        }else{
            milez.setText("0");

        }
        notification.setVisibility(View.GONE);

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

            notification=(ImageView)rowView.findViewById(R.id.notes);

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
            notification.setVisibility(View.VISIBLE);

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
        Picasso.with(context)
                .load(imageUrl.get(position))
                .placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.doctor)     // optional
                .resize(400,400)                        // optional
                .into(imageLead);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alterbox(position);

            }
        });
        return rowView;

    }

   private void alterbox(final int a) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater =(LayoutInflater.from(getContext()));
        final View dialogView = inflater.inflate(R.layout.rmmessage, null);
      //  view = (ListView) dialogView.findViewById(R.id.lv);
       final TextView  alternotificationsss=(TextView) dialogView.findViewById(R.id.alternotification);
        dialogBuilder.setView(dialogView);
       alternotificationsss.setText(notes.get(a));
       dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //notes.get(a)
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
