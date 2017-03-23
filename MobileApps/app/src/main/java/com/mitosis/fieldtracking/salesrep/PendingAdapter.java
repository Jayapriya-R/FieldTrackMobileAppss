package com.mitosis.fieldtracking.salesrep;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import utils.Utils;

import static com.mitosis.fieldtracking.salesrep.Constants.appointmentDate;
import static com.mitosis.fieldtracking.salesrep.Constants.leadDetailsId;

/**
 * Created by mitosis on 2/2/17.
 */

public class PendingAdapter extends ArrayAdapter<String> {

    private final Context context;
    private TextView start;

    TextView milez;
    TextView text_name;
    String registerUserURL;

    ImageView startimage,startyet,attend,reached,imageLead;

    ArrayList<String> nameArr = new ArrayList<String>();
    ArrayList<String> statusArr = new ArrayList<String>();
    ArrayList<String> dinstances = new ArrayList<String>();
    ArrayList<String> imagesArr = new ArrayList<String>();
    public static ArrayList<String> latitudeArr = new ArrayList<String>();
    public static ArrayList<String> longitudeArr = new ArrayList<String>();

    ArrayList<String> leadid = new ArrayList<String>();

    JSONObject jsonObject;

    public PendingAdapter(Context context, ArrayList<String> contactName, ArrayList<String> status, ArrayList<String> leadid,ArrayList<String> address1,ArrayList<String> address2, ArrayList<String> city, ArrayList<String> state,ArrayList<String> zipcode,ArrayList<String> latitude,ArrayList<String> longitude,ArrayList<String> distance,ArrayList<String>images) {
        super(context, R.layout.pendinglead, contactName);
        this.getContext();
        this.context = context;
        this.nameArr = contactName;
        this.statusArr = status;
        this.leadid = leadid;
        this.latitudeArr = latitude;
        this.longitudeArr = longitude;
        this.dinstances = distance;
        this.imagesArr = images;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.pendinglead, parent, false);

        start = (TextView) rowView.findViewById(R.id.start);
        text_name = (TextView) rowView.findViewById(R.id.doconame);
        milez = (TextView) rowView.findViewById(R.id.text_miles);

        startimage=(ImageView) rowView.findViewById(R.id.imagestart);
        start = (TextView) rowView.findViewById(R.id.start);
        startyet = (ImageView) rowView.findViewById(R.id.yetstart);

        attend = (ImageView) rowView.findViewById(R.id.attend);

        reached = (ImageView) rowView.findViewById(R.id.reached);
        imageLead = (ImageView) rowView.findViewById(R.id.image_lead);

        attend.setVisibility(View.GONE);
        reached.setVisibility(View.GONE);
        startimage.setVisibility(View.GONE);
        startyet.setVisibility(View.VISIBLE);

        if(statusArr.get(position).equals("Yet to Start"))
        {
            start = (TextView) rowView.findViewById(R.id.start);
            attend = (ImageView) rowView.findViewById(R.id.attend);
            reached = (ImageView) rowView.findViewById(R.id.reached);
            startyet = (ImageView) rowView.findViewById(R.id.yetstart);
            startimage=(ImageView) rowView.findViewById(R.id.imagestart);

            start.setVisibility(View.GONE);
            attend.setVisibility(View.GONE);
            reached.setVisibility(View.GONE);
            startimage.setVisibility(View.GONE);
            startyet.setVisibility(View.VISIBLE);

        }
        if(statusArr.get(position).equals("STARTED"))
        {
            startyet = (ImageView) rowView.findViewById(R.id.yetstart);
            startyet.setVisibility(View.GONE);


            start = (TextView) rowView.findViewById(R.id.start);
            attend = (ImageView) rowView.findViewById(R.id.attend);
            reached = (ImageView) rowView.findViewById(R.id.reached);
            start.setVisibility(View.GONE);
            attend.setVisibility(View.GONE);
            reached.setVisibility(View.GONE);

            startimage=(ImageView) rowView.findViewById(R.id.imagestart);
            startimage.setVisibility(View.VISIBLE);
        }

        if(statusArr.get(position).equals("ATTENDED"))
        {
            start = (TextView) rowView.findViewById(R.id.start);
            attend = (ImageView) rowView.findViewById(R.id.attend);
            reached = (ImageView) rowView.findViewById(R.id.reached);
            startyet = (ImageView) rowView.findViewById(R.id.yetstart);

            start.setVisibility(View.GONE);
            attend.setVisibility(View.VISIBLE);
            reached.setVisibility(View.GONE);
            startyet.setVisibility(View.GONE);

            startimage=(ImageView) rowView.findViewById(R.id.imagestart);
            startimage.setVisibility(View.GONE);
        }

        if(statusArr.get(position).equals("REACHED"))
        {
            start = (TextView) rowView.findViewById(R.id.start);
            attend = (ImageView) rowView.findViewById(R.id.attend);
            reached = (ImageView) rowView.findViewById(R.id.reached);
            startyet = (ImageView) rowView.findViewById(R.id.yetstart);


            start.setVisibility(View.GONE);
            attend.setVisibility(View.GONE);
            reached.setVisibility(View.VISIBLE);

            startyet.setVisibility(View.GONE);

            startimage=(ImageView) rowView.findViewById(R.id.imagestart);
            startimage.setVisibility(View.GONE);
        }


        startyet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                jsonObject=new JSONObject();
                try {

                    jsonObject.put("appointmentDate",appointmentDate.get(position));
                    jsonObject.put("leadDetailsId",leadDetailsId.get(position));
                    jsonObject.put("status","started");
                    jsonObject.put("repId","26");
                    jsonObject.put("notes","aaaaaaa");

                    new  MyAsyncTask((Activity) getContext()).execute();
                    startyet = (ImageView) rowView.findViewById(R.id.yetstart);
                    startimage=(ImageView) rowView.findViewById(R.id.imagestart);
                    attend = (ImageView) rowView.findViewById(R.id.attend);
                    reached = (ImageView) rowView.findViewById(R.id.reached);
                    startyet.setVisibility(View.GONE);
                    reached.setVisibility(View.GONE);

                    attend.setVisibility(View.GONE);
                    startimage=(ImageView) rowView.findViewById(R.id.imagestart);
                    startimage.setVisibility(View.VISIBLE);

                    context.startService(new Intent(context, BackgroundService.class));
                }
                catch (Exception e) {
                }
            }
        });

        Picasso.with(getContext())
                .load(imagesArr.get(position))
                .placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.error)     // optional
                .resize(400,400)                        // optional
                .into(imageLead);


        final String name=leadDetailsId.get(position);

        start.setText(statusArr.get(position));
        text_name.setText(nameArr.get(position));
        milez.setText(dinstances.get(position)+" "+"Meters Away");

        return rowView;

    }

    class MyAsyncTask extends AsyncTask<String, String, String> {
        Activity mContex;

        public MyAsyncTask(Activity context) {
            this.mContex = context;
        }

        protected String doInBackground(String... params) {

            registerUserURL = Constants.update;
            String WEB_RESULT = Utils.WebCall(registerUserURL, jsonObject.toString());
            return WEB_RESULT;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println("Output: " + result);
            attend.setVisibility(View.GONE);
            startimage.setVisibility(View.VISIBLE);

        }
    }
}