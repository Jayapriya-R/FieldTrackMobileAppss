package com.mitosis.fieldtracking.salesrep;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import utils.Utils;


/**
 * Created by mitosis on 21/2/17.
 */

public class LeadDetailsFragment extends Fragment implements OnMapReadyCallback,LocationListener {
    Marker m1;
    MapView mMapView;
    GoogleMap mGoogleMap;
    ImageView call, leadImage;
    EditText description;
    String leadid;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";

    JSONObject jsonObject;
    public String lat1, lng1;
    RelativeLayout submit;
    TextView docname, docstatus, addl1, addl2, addstate, kilometer, assignDate;
    public String mobnum, assigndate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.maps, container, false);
        setHasOptionsMenu(true);

        mMapView = (MapView) v.findViewById(R.id.map);
        submit = (RelativeLayout) v.findViewById(R.id.rel_submit);
        call = (ImageView) v.findViewById(R.id.call);
        docname = (TextView) v.findViewById(R.id.doc_name);
        docstatus = (TextView) v.findViewById(R.id.doc_status);
        addl1 = (TextView) v.findViewById(R.id.addl_1);
        addl2 = (TextView) v.findViewById(R.id.addl_2);
        addstate = (TextView) v.findViewById(R.id.add_state);
        kilometer = (TextView) v.findViewById(R.id.text_km);
        description = (EditText) v.findViewById(R.id.hname);
        assignDate = (TextView) v.findViewById(R.id.text_assigntime);
        leadImage = (ImageView) v.findViewById(R.id.image_detail);

        sharedpreferences = getContext().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Name)) {
            description.setText(sharedpreferences.getString(Name, ""));
        }

        String name = getArguments().getString("Name");
        String statuz = getArguments().getString("Status");
        String Addressline1 = getArguments().getString("AddressLine1");
        String Addressline2 = getArguments().getString("AddressLine2");
        String citi = getArguments().getString("City");
        String statE = getArguments().getString("State");
        String zipcode = getArguments().getString("ZipCode");
        String mile = getArguments().getString("Mile");
        mobnum = getArguments().getString("mobile");
        lat1 = getArguments().getString("latitude");
        lng1 = getArguments().getString("longitude");
        leadid = getArguments().getString("idLead");
        assigndate = getArguments().getString("appointmentDate");
        String images = getArguments().getString("imageUrl");

        docname.setText(name);
        docstatus.setText(statuz);
        addl1.setText(Addressline1);
        addl2.setText(Addressline2 + "," + citi);
        addstate.setText(statE + "," + zipcode + ",");
        kilometer.setText(mile + " " + "Meters Away");
        assignDate.setText(assigndate);

        Picasso.with(getContext())
                .load(images)
                .placeholder(R.drawable.placeholder)   // optional
                .error(R.drawable.error)     // optional
                .resize(400, 400)                        // optional
                .into(leadImage);

        mMapView.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                call();
            }
        });
        mMapView.getMapAsync(this); //this is important

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jsonObject = new JSONObject();
                try {

                    jsonObject.put("appointmentDate", assigndate);
                    jsonObject.put("leadDetailsId", leadid);
                    jsonObject.put("status", "ATTENDED");
                    jsonObject.put("repId", "26");
                    jsonObject.put("notes", description.getText().toString());
                    new MyAsyncTask((Activity) getContext()).execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        description.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction (TextView v,int actionId, KeyEvent event){
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                   // sendMessage();
                    handled = true;
                }
                return handled;
            }
        });

        return v;
    }
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    class MyAsyncTask extends AsyncTask<String, String, String> {
        Activity mContex;

        public MyAsyncTask(Activity context) {
            this.mContex = context;
        }
        protected String doInBackground(String... params) {
            String registerUserURL;
            registerUserURL =Constants.update;
            String WEB_RESULT = Utils.WebCall(registerUserURL, jsonObject.toString());
            return WEB_RESULT;
        }

        @Override
        protected void onPostExecute(String result) {

            System.out.println("Output: "+result);
            if(result.equals("")) {
            Intent main = new Intent(getContext(), MainActivity.class);
                startActivity(main);

    Toast.makeText(getContext(), " attended ", Toast.LENGTH_SHORT).show();
                submit.setVisibility(View.GONE);
}
else{
                Toast.makeText(getContext(), " Try Again ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void call() {

        Intent call = new Intent(Intent.ACTION_DIAL);
        call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        call.setData(Uri.parse("tel:" + mobnum));
        startActivity(call);
    }

    @Override

    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);

        m1 = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.valueOf(lat1),Double.valueOf(lng1)))
                .anchor(0.5f, 0.5f)
                .title("Title2")
                .snippet("Snippet2")
        );

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        builder.include(m1.getPosition());

        LatLngBounds bounds = builder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10); // offset from edges of the map 12% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

        mGoogleMap.animateCamera(cu);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void Save(View view) {
        String n = description.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Name, n);
        editor.commit();
    }
    public void Get(View view) {
        sharedpreferences = getContext().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        if (sharedpreferences.contains(Name)) {
            description.setText(sharedpreferences.getString(Name, ""));
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        getActivity().setTitle("DETAILS");

      MenuItem item = menu.findItem(R.id.action_mainMenu3);
        item.setVisible(false);
        MenuItem item2 = menu.findItem(R.id.action_mainMenu2);
        item2.setVisible(false);
        MenuItem item3 = menu.findItem(R.id.action_search);
        item3.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle bundle=new Bundle();
        bundle.putString("LatDes",lat1);
        bundle.putString("LngDes",lng1);
        startActivity(new Intent(getActivity(),MapsActivity.class).putExtras(bundle));
        Toast.makeText(getActivity(), "Loading Map...", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}