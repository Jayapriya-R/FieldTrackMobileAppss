package com.mitosis.fieldtracking.salesrep;

/**
 * Created by mitosis on 18/2/17.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.mitosis.fieldtracking.salesrep.Constants.addressLine1;
import static com.mitosis.fieldtracking.salesrep.Constants.addressLine2;
import static com.mitosis.fieldtracking.salesrep.Constants.appointmentDate;
import static com.mitosis.fieldtracking.salesrep.Constants.city;
import static com.mitosis.fieldtracking.salesrep.Constants.contactName;
import static com.mitosis.fieldtracking.salesrep.Constants.lat;
import static com.mitosis.fieldtracking.salesrep.Constants.latitude;
import static com.mitosis.fieldtracking.salesrep.Constants.leadDetailsId;
import static com.mitosis.fieldtracking.salesrep.Constants.leadImage;
import static com.mitosis.fieldtracking.salesrep.Constants.lng;
import static com.mitosis.fieldtracking.salesrep.Constants.longitude;
import static com.mitosis.fieldtracking.salesrep.Constants.mobileNumber;
import static com.mitosis.fieldtracking.salesrep.Constants.state;
import static com.mitosis.fieldtracking.salesrep.Constants.status;
import static com.mitosis.fieldtracking.salesrep.Constants.zipCode;
import static com.mitosis.fieldtracking.salesrep.R.id.dateasc;



public class TotalLeadFragment extends Fragment implements LocationListener {
    ListView list;
    RadioButton alphaa, distance,atoz,ztoa;
    RadioGroup sort;
    LocationManager lm;
    String provider,lat2,lng2;
    Location l;
    double distances;
    FloatingActionButton fab;
    List<Integer> integersArray = new ArrayList<>();
    public static ArrayList<String> distanceArrs=new ArrayList<>();
    public static ArrayList<String> distanceArr=new ArrayList<>();

    public TotalLeadFragment() {

    }

    public static TotalLeadFragment newInstance(String tabSelected) {
        TotalLeadFragment fragment = new TotalLeadFragment();
        Bundle args = new Bundle();
        args.putString(Constants.FRAG_B, tabSelected);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repview, container, false);
        setHasOptionsMenu(true);

        list = (ListView) view.findViewById(R.id.replist);
         fab = (FloatingActionButton)view. findViewById(R.id.fab);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        new RegisterTask().execute(Constants.loginUrl);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String name = contactName.get(position);
                String statuz = status.get(position);
                String Addressline1 = addressLine1.get(position);
                String Addressline2 = addressLine2.get(position);
                String citi = city.get(position);
                String statE = state.get(position);
                String zipcode = zipCode.get(position);
                String mobnum = mobileNumber.get(position);
                String mile = distanceArr.get(position);
                String lat = latitude.get(position);
                String lng = longitude.get(position);
                String leadid = leadDetailsId.get(position);
                String date=appointmentDate.get(position);
                String image=leadImage.get(position);

                Bundle args = new Bundle();
                Fragment fragment = new LeadDetailsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                args.putString("Name", name);
                args.putString("Status", statuz);
                args.putString("AddressLine1", Addressline1);
                args.putString("AddressLine2", Addressline2);
                args.putString("City", citi);
                args.putString("State", statE);
                args.putString("ZipCode", zipcode);
                args.putString("Mile", mile);
                args.putString("mobile", mobnum);
                args.putString("latitude", lat);
                args.putString("longitude", lng);
                args.putString("idLead", leadid);
                args.putString("appointmentDate", date);
                args.putString("imageUrl", image);

                fragment.setArguments(args);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_layout_for_activity_navigation, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Fragment fragment = new CreateLeadFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_layout_for_activity_navigation, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        try {

            lm=(LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
            Criteria c=new Criteria();

            provider=lm.getBestProvider(c, false);

            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }

            l=lm.getLastKnownLocation(provider);
            if(l!=null) {
                //get latitude and longitude of the location
                lng = l.getLongitude();
                lat = l.getLatitude();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem item4 = menu.findItem(R.id.action_details);
        item4.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().setTitle("DASHBOARD");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mainMenu3:
                final Dialog dialog = new Dialog(getContext());
                showChangeLangDialog();
                return true;
            case R.id.action_search:
                Toast.makeText(getActivity(), "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dailog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Sort By");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                sort = (RadioGroup) dialogView.findViewById(R.id.sort);
                int selectedId = sort.getCheckedRadioButtonId();
                alphaa = (RadioButton) dialogView.findViewById(dateasc);
                distance = (RadioButton) dialogView.findViewById(R.id.distance);
                switch (selectedId) {
                    case R.id.dateasc:
                        new RegisterTask().execute(Constants.sortappointdateasc);

                        alphaa.setChecked(true);
                        break;
                    case R.id.distance:
                        distance.setChecked(true);
                        for (int i = 0; i < distanceArr.size(); i++) {
                            integersArray.add(Integer.parseInt(distanceArr.get(i)));
                        }
                        for (Integer integer: integersArray) {
                            System.out.println(integer);
                        }
                        for (Integer integer: integersArray) {
                            System.out.println(integer);
                        }
                        for(int i=0; i < integersArray.size()-1; i++ ) {
                            for (int j = 0; j < integersArray.size() - 1; j++) {
                                if (integersArray.get(j) > integersArray.get(j + 1)) {
                                    int temp = integersArray.get(j);
                                    integersArray.set(j, integersArray.get(j + 1));
                                    integersArray.set(j + 1, temp);
                                }
                            }
                        }
                        for (int i = 0; i < integersArray.size(); i++){
                            distanceArrs.add(String.valueOf(integersArray.get(i)));
                        }
                        for (String i: distanceArrs) {
                            System.out.println(i);
                        }

                        for (Integer integer: integersArray) {
                            System.out.println(integer);
                        }
                        FavouritesAdapter adapter = new FavouritesAdapter(getContext(), contactName, status, leadDetailsId, addressLine1, addressLine2, city, state, zipCode, latitude, longitude,distanceArrs,leadImage);
                        list.setAdapter(adapter);
                        break;
                    case R.id.sortaz:


                        new RegisterTask().execute(Constants.sortaz);

                        break;
                    case R.id.sortza:
                        new RegisterTask().execute(Constants.sortza);
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        lng=l.getLongitude();
        lat=l.getLatitude();
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
    private class RegisterTask extends AsyncTask<String, Void, Void> {

        public RegisterTask() {
        }

        @Override
        protected Void doInBackground(String... params) {

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            String registerUserURL = params[0];
            contactName.clear();
            status.clear();
            addressLine1.clear();
            addressLine2.clear();
            city.clear();
            state.clear();
            zipCode.clear();

            StringRequest registerRequest = new StringRequest(Request.Method.GET, registerUserURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            contactName.add(jsonObject.getString("contactName"));
                            status.add(jsonObject.getString("status"));
                            addressLine1.add(jsonObject.getString("addressLine1"));
                            addressLine2.add(jsonObject.getString("addressLine2"));
                            city.add(jsonObject.getString("city"));
                            state.add(jsonObject.getString("state"));
                            zipCode.add(jsonObject.getString("zipCode"));
                            mobileNumber.add(jsonObject.getString("mobileNumber"));
                            leadDetailsId.add(jsonObject.getString("leadDetailsId"));
                            latitude.add(jsonObject.getString("latitide"));
                            longitude.add(jsonObject.getString("longitude"));
                            appointmentDate.add(jsonObject.getString("appointmentDate"));
                            leadImage.add(jsonObject.getString("imageUrl"));

                            FavouritesAdapter adapter = new FavouritesAdapter(getContext(), contactName, status, leadDetailsId, addressLine1, addressLine2, city, state, zipCode, latitude, longitude,distanceArr,leadImage);
                            list.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                    }

                    try {
                        Location location1 = new Location("locationA");
                        location1.setLatitude(lat);
                        location1.setLongitude(lng);
                        Location location2 = new Location("locationB");
                        for (int i = 0; i < latitude.size(); i++) {
                            location2.setLatitude(Double.valueOf(latitude.get(i)));
                            location2.setLongitude(Double.valueOf(longitude.get(i)));
                            distances = location1.distanceTo(location2);
                            distanceArr.add(String.valueOf((int) Math.round(distances)));
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("RegisterActivity", error.getMessage() != null ? error.getMessage() : "");
                }
            });
            requestQueue.add(registerRequest);
            return null;
        }
    }
}