package com.example.mitosis.salemanager;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;

/**
 * Created by mitosis on 21/2/17.
 */

public class MapViewFragment extends Fragment implements OnMapReadyCallback {
    Marker m1,m2,m3;
    MapView mMapView;
    GoogleMap mGoogleMap;
    TextView submit;
    ImageView call;
    Context pref;
    double lat1 ;
    double lng1;
    double lat2 = 12.91628063011457;
    double lng2 = 80.1523217372;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.maps, container, false);

        mMapView= (MapView)v.findViewById(R.id.map);
        submit = (TextView)v.findViewById(R.id.text_submit);
        call   = (ImageView) v.findViewById(R.id.call);
        Context context =this.getContext();
        GPSService mGPSService = new GPSService(context);
        mGPSService.getLocation();

        if (mGPSService.isLocationAvailable == false) {

        } else {

            // Getting location co-ordinates
            lat1 = mGPSService.getLatitude();
            lng1 = mGPSService.getLongitude();

        }

        mMapView.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(getActivity(),new String[]{
                android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                call();
            }
        });
        mMapView.getMapAsync(this); //this is important
        // lat1 and lng1 are the values of a previously stored location

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CalculationByDistance();

            }
        });


        //compare the distance
        if (distance(lat1, lng1, lat2, lng2) < 0.1) {
            // if distance < 0.1 miles we take locations as equal

            Toast.makeText(getContext(),"Sale Person Reached",Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    private void call() {

        Intent call = new Intent(Intent.ACTION_DIAL);
        call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        call.setData(Uri.parse("tel:" + ""));
        startActivity(call);
    }
    private double  CalculationByDistance() {
        int Radius = 6371;// radius of earth in Km

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }
    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;
        return dist; // output distance, in MILES


    }
    @Override

    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

        m1 = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat1,lng1))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .anchor(0.5f, 0.5f)
                .title("Title1")
                .snippet("Snippet1"));


        m2 = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat2,lng2))
                .anchor(0.5f, 0.5f)
                .title("Title2")
                .snippet("Snippet2")
        );
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

}

