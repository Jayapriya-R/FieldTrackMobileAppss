package com.mitosis.fieldtracking.regionalmanager;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mitosis.fieldtracking.R;
import com.mitosis.fieldtracking.mapanimation.LatLngInterpolator;
import com.mitosis.fieldtracking.mapanimation.MarkerAnimation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.Utils;

public class RMMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    RequestQueue requestQueue;
    StringRequest stringRequest;
    String result;
    RelativeLayout hiddenlayout;
    TextView nameText,phoneText;
    FloatingActionButton fab;
    private GoogleMap mMap;
    Marker mMarker;
    SharedPreferences sharedPreferences;
    JSONObject allrepInfoJsonObj;
    final String[] userName = new String[1];
    Boolean locationtrackingbool=false;int j=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rmactivity_maps);
        allrepInfoJsonObj=new JSONObject();
        hiddenlayout= (RelativeLayout) findViewById(R.id.hiddenlayout);
        nameText=(TextView)findViewById(R.id.name);
        phoneText=(TextView)findViewById(R.id.phone);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        sharedPreferences=this.getSharedPreferences("lastLocation",MODE_PRIVATE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        requestQueue = Volley.newRequestQueue(RMMapsActivity.this);
        stringRequest=new StringRequest(Request.Method.GET, RMConstants.getAllRepresentativesLocation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(RMMapsActivity.this, response, Toast.LENGTH_SHORT).show();
                Log.e("Locations",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    Log.e("Locations",""+jsonArray);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject= (JSONObject) jsonArray.get(i);
                        Log.e("Locations",""+jsonObject);
                        String username=jsonObject.getString("userName");
                        String firstname=jsonObject.getString("firstName");
                        String latitude=jsonObject.getString("latitude");
                        String longitude=jsonObject.getString("longitude");
                        allrepInfoJsonObj.put(firstname,jsonObject);
                        LatLng axis = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                        mMap.addMarker(new MarkerOptions().position(axis).title(firstname).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(axis,10));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RMMapsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Locations error",error.toString());
            }
        });
        requestQueue.add(stringRequest);

        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final String firstName;
        final JSONObject[] clickedinfo = new JSONObject[1];
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                try {
                    clickedinfo[0] =new JSONObject(allrepInfoJsonObj.get(marker.getTitle()).toString());
                    userName[0] =clickedinfo[0].get("userName").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mMarker=marker;
                hiddenlayout.setVisibility(View.VISIBLE);
                nameText.setText(marker.getTitle());
                phoneText.setText("+91-"+userName[0]);
                return false;
            }
        });
//        Toast.makeText(RMMapsActivity.this,getIntent().getExtras().getString("locValues"),Toast.LENGTH_SHORT).show();
    }
    public void onfloatbuttonpress(View view) throws JSONException {
        Toast.makeText(this, "Tracking..", Toast.LENGTH_SHORT).show();
        hiddenlayout.setVisibility(View.GONE);
        JSONObject clickedmarkerJsonObj=new JSONObject();
        clickedmarkerJsonObj.put("markertitle",mMarker.getTitle())
                .put("latitude",mMarker.getPosition().latitude)
                .put("longitude",mMarker.getPosition().longitude);
        sharedPreferences.edit().putString("lastknownlocLatlng",mMarker.getPosition().latitude+":"+mMarker.getPosition().longitude).commit();
        mMap.clear();
        mMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(clickedmarkerJsonObj.getString("latitude")),
                Double.parseDouble(clickedmarkerJsonObj.getString("longitude"))))
                .title(clickedmarkerJsonObj.getString("markertitle"))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        new StartTracking().execute(userName[0]);
    }
    public class StartTracking extends AsyncTask<String,String,String>{
        String receivedLocation;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            locationtrackingbool=true;
//        mMap.clear();
        }

        @Override
        protected String doInBackground(String... params) {
            for (int i=0;i<1 &&locationtrackingbool;i++){
                String WEB_RESULT = Utils.WebCall(RMConstants.getUserLocation + params[0], "GET");
                try {
                    JSONObject jsonObject=new JSONObject(WEB_RESULT);
                    receivedLocation=jsonObject.getString("latitude")+":"+jsonObject.getString("longitude");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                publishProgress(""+receivedLocation);
                i--;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();e.printStackTrace();
                }
            }

            return "";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Log.e("Published",values[0]);
            String[] oldLoc=sharedPreferences.getString("lastknownlocLatlng","0.00:0.00").split(":");
            String[] newLoc=values[0].split(":");
            LatLng oldLatLng=new LatLng(Double.parseDouble(oldLoc[0]),Double.parseDouble(oldLoc[1]));
            LatLng newLatLng=new LatLng(Double.parseDouble(newLoc[0]),Double.parseDouble(newLoc[1]));
            if (oldLatLng!=newLatLng){
                LatLngInterpolator latLngInterpolator = new LatLngInterpolator.Spherical();
                MarkerAnimation.animateMarkerToGB(mMarker,newLatLng, latLngInterpolator);
                j++;
                if (j==5){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng,16));
                    j=0;
                }
                sharedPreferences.edit().putString("lastknownlocLatlng",values[0]);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Response",s);
        }
    }
    @Override
    public void onBackPressed() {
        if (hiddenlayout.getVisibility()==View.VISIBLE){
            hiddenlayout.setVisibility(View.GONE);
        }else{
            locationtrackingbool=false;
            super.onBackPressed();
        }
    }

}
