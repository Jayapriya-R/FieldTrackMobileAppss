package com.mitosis.salesmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mitosis on 27/3/17.
 */
public class SaleLeadTrackAdapter  extends ArrayAdapter<String> {
    private Context context;
    TextView name;
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

    public static String repid;
    public SaleLeadTrackAdapter(Context context, ArrayList<String> contactName,ArrayList<String> status,
                                ArrayList<String> leadname, ArrayList<String> mobileNumber,
                                ArrayList<String> addressLine1, ArrayList<String> addressLine2,
                                ArrayList<String> city, ArrayList<String> state,
                                ArrayList<String> zipCode,
                                ArrayList<String> country, ArrayList<String> landMark,
                                ArrayList<String> leadDetailsId,
                                ArrayList<String> imageUrl, ArrayList<String> repId,ArrayList<String> appointmentDate,
                                ArrayList<String> latitide, ArrayList<String> longitude) {
        super(context, R.layout.saleleadtrackadapter, contactName);
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
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.saleleadtrackadapter, parent, false);
        name=(TextView)rowView.findViewById(R.id.doconame) ;
        name.setText(contactName.get(position));
        startimage=(ImageView) rowView.findViewById(R.id.imagestart);
        startimage.setVisibility(View.GONE);
        startyet = (ImageView) rowView.findViewById(R.id.yetstart);
        startyet.setVisibility(View.VISIBLE);
        attend = (ImageView) rowView.findViewById(R.id.attend);
        attend.setVisibility(View.GONE);
        reached = (ImageView) rowView.findViewById(R.id.reached);
        reached.setVisibility(View.GONE);
        imageLead = (ImageView) rowView.findViewById(R.id.image_lead);
        repid=repId.get(position);

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
        }

        Picasso.with(context)
                .load(imageUrl.get(position))
                .placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.error)     // optional
                .resize(400,400)                        // optional
                .into(imageLead);
        return rowView;

    } }
