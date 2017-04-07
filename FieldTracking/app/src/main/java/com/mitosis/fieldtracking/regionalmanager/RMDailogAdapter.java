package com.mitosis.fieldtracking.regionalmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mitosis.fieldtracking.R;

import java.util.ArrayList;

/**
 * Created by mitosis on 17/3/17.
 */

public class RMDailogAdapter extends ArrayAdapter<String> {

    private final Context context;
    ArrayList<String> userIds = new ArrayList<String>();

    ArrayList<String> firstname = new ArrayList<String>();
    //ArrayList<String> lastnamee = new ArrayList<String>();

    ArrayList<String> userIds = new ArrayList<String>();
   public static String assigns;
    public RMDailogAdapter(Context context, ArrayList<String> contactName, ArrayList<String> usersId,ArrayList<String> imageurl) {
        super(context, R.layout.rmdailogadap, contactName);
        this.getContext();
        this.context = context;
        this.firstname = contactName;
        this.imageurl=imageurl;

        //  this.lastnamee=lastnamee;
        this.userIds=usersId;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.rmdailogadap, parent, false);
        TextView txt=(TextView)rowView.findViewById(R.id.txtdailog);
        ImageView aa=(ImageView)rowView.findViewById(R.id.saleimage);

        txt.setText(firstname.get(position));
        assigns=userIds.get(position);
        return  rowView;
    }
}