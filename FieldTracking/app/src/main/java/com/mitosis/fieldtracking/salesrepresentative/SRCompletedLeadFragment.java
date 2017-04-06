package com.mitosis.fieldtracking.salesrepresentative;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mitosis.fieldtracking.R;
import com.mitosis.fieldtracking.integrated.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.mitosis.fieldtracking.R.id.dateasc;
import static com.mitosis.fieldtracking.R.id.sortatoz;
import static com.mitosis.fieldtracking.R.id.sortztoa;

/**
 * Created by mitosis on 20/2/17.
 */

public class SRCompletedLeadFragment extends Fragment  implements LocationListener {
    ListView list;
    RadioButton alphaa,distance,atoz,ztoa;
    RadioGroup sort;
    LocationManager lm;
    String provider;
    Location l;
    double distances;
    String ascendings,descendings,datesort,completedTab;
    int selectedRadiobutton= R.id.dateasc;

    public static ArrayList<String> distanceArrs=new ArrayList<>();
    List<Integer> integersArray = new ArrayList<>();

    public static ArrayList<String> contactName=new ArrayList<>();
    public static ArrayList<String> status=new ArrayList<>();
    public static ArrayList<String> addressLine1=new ArrayList<>();
    public static ArrayList<String> addressLine2=new ArrayList<>();
    public static ArrayList<String> city=new ArrayList<>();
    public static ArrayList<String> state=new ArrayList<>();
    public static ArrayList<String> zipCode=new ArrayList<>();
    public static ArrayList<String> mobileNumber=new ArrayList<>();
    public static ArrayList<String> leadDetailsId=new ArrayList<>();
    public static ArrayList<String> latitude=new ArrayList<>();
    public static ArrayList<String> longitude=new ArrayList<>();



    public SRCompletedLeadFragment() {
        // Required empty public constructor
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
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.srcompletelead_list, container, false);
        list = (ListView) view.findViewById(R.id.complete_listview);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);




        String []  ascending= SRConstants.sortaz.split("26");
        String ascenpart1=ascending[0];
        String ascenpart2=ascending[1];
        ascendings=ascenpart1+ LoginActivity.userID+ascenpart2;

        String []  dec= SRConstants.sortza.split("26");
        String dec1=dec[0];
        String dec2=dec[1];
        descendings=dec1+ LoginActivity.userID+dec2;

        String []  date= SRConstants.sortappointdateasc.split("26");
        String date1=date[0];
        String date2=date[1];
        datesort=date1+ LoginActivity.userID+date2;


        String []  compl= SRConstants.comlpete.split("26");
        String compl1=compl[0];
        String compl2=compl[1];
        completedTab=compl1+ LoginActivity.userID+compl2;


        new RegisterTask().execute(completedTab);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String name=contactName.get(position);
                String statuz=status.get(position);
                String Addressline1=addressLine1.get(position);
                String Addressline2=addressLine2.get(position);
                String citi=city.get(position);
                String statE=state.get(position);
                String zipcode=zipCode.get(position);
                String mobnum=mobileNumber.get(position);
                String mile= SRTotalLeadFragment.distanceArr.get(position);
                String lat=latitude.get(position);
                String lng=longitude.get(position);
                String date= SRConstants.appointmentDate.get(position);

                Bundle args = new Bundle();
                Fragment fragment = new SRLeadDetailsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                args.putString("Name", name);
                args.putString("Status", statuz);
                args.putString("AddressLine1", Addressline1);
                args.putString("AddressLine2", Addressline2);
                args.putString("City", citi);
                args.putString("State", statE);
                args.putString("ZipCode", zipcode);
                args.putString("mobile", mobnum);
                args.putString("Mile", mile);
                args.putString("latitude", lat);
                args.putString("longitude", lng);
                args.putString("appointmentDate", date);

                fragment.setArguments(args);
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
                SRConstants.lng = l.getLongitude();
                SRConstants.lat = l.getLatitude();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.srmain, menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.srdailog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Sort By");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                sort= (RadioGroup)dialogView.findViewById(R.id.sort);
                sort.check(selectedRadiobutton);
                int selectedId = sort.getCheckedRadioButtonId();
                alphaa = (RadioButton) dialogView.findViewById(dateasc);
                atoz = (RadioButton) dialogView.findViewById(sortatoz);
                ztoa = (RadioButton) dialogView.findViewById(sortztoa);
                distance = (RadioButton) dialogView.findViewById(R.id.distance);
                selectedRadiobutton=selectedId;
                switch (selectedId) {
                    case R.id.dateasc:
                        new RegisterTask().execute(datesort);

                        break;
                    case R.id.distance:
                        for (int i = 0; i < SRTotalLeadFragment.distanceArr.size(); i++) {
                            integersArray.add(Integer.parseInt(SRTotalLeadFragment.distanceArr.get(i)));
                            //  Toast.makeText(getContext(), distanceArr.get(i), Toast.LENGTH_SHORT).show();
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
                        SRCompleteAdapter adapter = new SRCompleteAdapter(getContext(), contactName, status, leadDetailsId, addressLine1, addressLine2, city, state, zipCode, latitude, longitude,distanceArrs, SRConstants.leadImage);
                        list.setAdapter(adapter);
                        break;
                    case R.id.sortatoz:

                        new RegisterTask().execute(ascendings);

                        break;
                    case R.id.sortztoa:
                        new RegisterTask().execute(descendings);

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
        SRConstants.lng=l.getLongitude();
        SRConstants.lat=l.getLatitude();
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
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait..");
            progressDialog.setTitle("Loading..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            String registerUserURL = params[0];
            contactName .clear();
            status .clear();
            addressLine1 .clear();
            addressLine2 .clear();
            city .clear();
            state .clear();
            zipCode .clear();

            StringRequest registerRequest = new StringRequest(Request.Method.GET, registerUserURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray=new JSONArray(response);

                        for(int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject=jsonArray.getJSONObject(i);
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
                            SRConstants.appointmentDate.add(jsonObject.getString("appointmentDate"));
                            SRConstants.leadImage.add(jsonObject.getString("imageUrl"));

                            SRCompleteAdapter adapter = new SRCompleteAdapter(getContext(),contactName,status,leadDetailsId,addressLine1,addressLine2,city,state,zipCode,latitude,longitude, SRTotalLeadFragment.distanceArr, SRConstants.leadImage);
                            list.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                    }
                    try {
                        Location location1 = new Location("locationA");
                        location1.setLatitude(SRConstants.lat);
                        location1.setLongitude(SRConstants.lng);
                        Location location2 = new Location("locationB");
                        for (int i = 0; i < latitude.size(); i++) {
                            location2.setLatitude(Double.valueOf(latitude.get(i)));
                            location2.setLongitude(Double.valueOf(longitude.get(i)));
                            distances = location1.distanceTo(location2);
                            SRTotalLeadFragment.distanceArr.add(String.valueOf((int) Math.round(distances)));
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

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }
    }
}