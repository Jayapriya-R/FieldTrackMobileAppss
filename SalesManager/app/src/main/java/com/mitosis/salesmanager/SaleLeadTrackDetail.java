package com.mitosis.salesmanager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;
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

import static com.mitosis.salesmanager.Constants.addressLine1;
import static com.mitosis.salesmanager.Constants.addressLine2;
import static com.mitosis.salesmanager.Constants.appointmentDate;
import static com.mitosis.salesmanager.Constants.city;
import static com.mitosis.salesmanager.Constants.contactName;
import static com.mitosis.salesmanager.Constants.country;
import static com.mitosis.salesmanager.Constants.email;
import static com.mitosis.salesmanager.Constants.imageUrl;
import static com.mitosis.salesmanager.Constants.landMark;
import static com.mitosis.salesmanager.Constants.latitide;
import static com.mitosis.salesmanager.Constants.leadDetailsId;
import static com.mitosis.salesmanager.Constants.leadName;
import static com.mitosis.salesmanager.Constants.longitude;
import static com.mitosis.salesmanager.Constants.mobileNumber;
import static com.mitosis.salesmanager.Constants.repId;
import static com.mitosis.salesmanager.Constants.sortappointdateasc;
import static com.mitosis.salesmanager.Constants.sortaz;
import static com.mitosis.salesmanager.Constants.sortza;
import static com.mitosis.salesmanager.Constants.state;
import static com.mitosis.salesmanager.Constants.status;
import static com.mitosis.salesmanager.Constants.telephoneNumber;
import static com.mitosis.salesmanager.Constants.zipCode;
import static com.mitosis.salesmanager.SaleLeadTrackAdapter.repid;

/**
 * Created by mitosis on 27/3/17.
 */

public class SaleLeadTrackDetail extends Fragment {

 private TextView  name,total, complete,pending;
 ListView leadlist;
    SaleLeadTrackAdapter adapter;
    RadioButton alphaa, distance;
    RadioGroup sort;
     private String date,ascedingorder,descendingorder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.saleleadtrackdetail, container, false);
        setHasOptionsMenu(true);
        name=(TextView)view.findViewById(R.id.name);
        total=(TextView)view.findViewById(R.id.total);
        complete=(TextView)view.findViewById(R.id.completed);
        pending=(TextView)view.findViewById(R.id.pending);
        leadlist=(ListView)view.findViewById(R.id.leadlist);
        String firstnamee = getArguments().getString("firstname");
        String lastnamee = getArguments().getString("lastname");
        String pendingcountt = getArguments().getString("pendingcount");
        String completecountt = getArguments().getString("completecount");
        String totalcountt = getArguments().getString("totalcount");
        String useridd = getArguments().getString("userid");
        String usernamee = getArguments().getString("usernamee");
        name.setText(firstnamee);
        total.setText(totalcountt);
        complete.setText(completecountt);
        pending.setText(pendingcountt);
        //asynctaskmethod(Constants.leadlist);
       // new Tracktask().execute(Constants.leadlist);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String registerUserURL = Constants.leadlist+useridd;
        contactName.clear();leadName.clear();status.clear();telephoneNumber.clear();mobileNumber.clear();
        email.clear();addressLine1.clear();addressLine2.clear();city.clear();state.clear();zipCode.clear();landMark.clear();leadDetailsId.clear();imageUrl.clear();
        repId.clear();appointmentDate.clear();latitide.clear();longitude.clear();
        StringRequest registerRequest = new StringRequest(Request.Method.GET, registerUserURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                objectparsing(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RegisterActivity", error.getMessage() != null ? error.getMessage() : "");
            }
        });
        requestQueue.add(registerRequest);
        leadlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = contactName.get(position);
                String statuz = status.get(position);
                String Addressline1 = addressLine1.get(position);
                String Addressline2 = addressLine2.get(position);
                String citi = city.get(position);
                String statE = state.get(position);
                String zipcode = zipCode.get(position);
                String mobnum = mobileNumber.get(position);
                String lng = longitude.get(position);
                String leadid = leadDetailsId.get(position);
                String date=appointmentDate.get(position);
                String image=imageUrl.get(position);
                Bundle args = new Bundle();
                Fragment fragment = new RepresentativeDetails();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                args.putString("Name", name);
                args.putString("Status", statuz);
                args.putString("AddressLine1", Addressline1);
                args.putString("AddressLine2", Addressline2);
                args.putString("City", citi);
                args.putString("State", statE);
                args.putString("ZipCode", zipcode);
               // args.putString("Mile", mile);
                args.putString("mobile", mobnum);
               // args.putString("latitude", lat);
                args.putString("longitude", lng);
                args.putString("idLead", leadid);
                args.putString("appointmentDate", date);
                args.putString("imageUrl", image);
                fragment.setArguments(args);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                        R.anim.fragment_slide_left_exit,
                        R.anim.fragment_slide_right_enter,
                        R.anim.fragment_slide_right_exit);
                fragmentTransaction.replace(R.id.fragment_layout_for_activity_navigation, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        String[] items = sortappointdateasc.split("26");
        String part1 = items[0];
        String part2 = items[1];
        date=part1+useridd+part2;
        String[] sortazzz = sortaz.split("26");
        String azpart1 = sortazzz[0];
        String azpart2 = sortazzz[1];
        ascedingorder  =azpart1+useridd+azpart2;
        String[] sortzaaa = sortza.split("26");
        String zapart1 = sortzaaa[0];
        String zapart2 = sortzaaa[1];
        descendingorder  =zapart1+useridd+zapart2;
        return view;
    }
public void objectparsing (String response){

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray =jsonObject.getJSONArray("leadList");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    contactName.add(json.getString("contactName"));
                    status.add(json.getString("status"));
                    leadName.add(json.getString("leadName"));
                    telephoneNumber.add(json.getString("telephoneNumber"));
                    mobileNumber.add(json.getString("mobileNumber"));
                    email.add(json.getString("email"));
                    addressLine1.add(json.getString("addressLine1"));
                    addressLine2.add(json.getString("addressLine2"));
                    city.add(json.getString("city"));
                    state.add(json.getString("state"));
                    zipCode.add(json.getString("zipCode"));
                    country.add(json.getString("country"));
                    landMark.add(json.getString("landMark"));
                    leadDetailsId.add(json.getString("leadDetailsId"));
                    imageUrl.add(json.getString("imageUrl"));
                    repId.add(json.getString("repId"));
                    appointmentDate.add(json.getString("appointmentDate"));
                    latitide.add(json.getString("latitide"));
                    longitude.add(json.getString("longitude"));
                }
            } catch (JSONException e) {
            }
            adapter=new SaleLeadTrackAdapter(getContext(),contactName,status,leadName, mobileNumber,addressLine1,addressLine2,city,state,zipCode,country,landMark,leadDetailsId,imageUrl,repId,appointmentDate,latitide,longitude);
            leadlist.setAdapter(adapter);
}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem item4 = menu.findItem(R.id.mapview);
        item4.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().setTitle("LEADLIST");
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
        final View dialogView = inflater.inflate(R.layout.menusort, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Sort By");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                SaleLeadTrackDetail yhbhyn=new SaleLeadTrackDetail();
                sort = (RadioGroup) dialogView.findViewById(R.id.sort);
                int selectedId = sort.getCheckedRadioButtonId();
                alphaa = (RadioButton) dialogView.findViewById(R.id.dateasc);
                distance = (RadioButton) dialogView.findViewById(R.id.distance);
                switch (selectedId) {
                    case R.id.dateasc:
                        //yhbhyn.asynctaskmethod(Constants.sortaz);
                        new  RegisterTask().execute(date);
                        break;
                    case R.id.distance:
                    case R.id.sortaz:
                         new RegisterTask().execute(ascedingorder);
                        break;
                    case R.id.sortza:
                        new RegisterTask().execute(descendingorder);
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
    private class RegisterTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String registerUserURL = params[0];
            contactName.clear();status.clear();addressLine1.clear();
            addressLine2.clear();city.clear();
            state.clear();zipCode.clear();
            StringRequest registerRequest = new StringRequest(Request.Method.GET, registerUserURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            contactName.add(json.getString("contactName"));
                            status.add(json.getString("status"));
                            leadName.add(json.getString("leadName"));
                            telephoneNumber.add(json.getString("telephoneNumber"));
                            mobileNumber.add(json.getString("mobileNumber"));
                            email.add(json.getString("email"));
                            addressLine1.add(json.getString("addressLine1"));
                            addressLine2.add(json.getString("addressLine2"));
                            city.add(json.getString("city"));
                            state.add(json.getString("state"));
                            zipCode.add(json.getString("zipCode"));
                            country.add(json.getString("country"));
                            landMark.add(json.getString("landMark"));
                            leadDetailsId.add(json.getString("leadDetailsId"));
                            imageUrl.add(json.getString("imageUrl"));
                            repId.add(json.getString("repId"));
                            appointmentDate.add(json.getString("appointmentDate"));
                            latitide.add(json.getString("latitide"));
                            longitude.add(json.getString("longitude"));
                            adapter=new SaleLeadTrackAdapter(getContext(),contactName,status,leadName, mobileNumber,addressLine1,addressLine2,city,state,zipCode,country,landMark,leadDetailsId,imageUrl,repId,appointmentDate,latitide,longitude);
                            leadlist.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
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


/* public class Tracktask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String registerUserURL = params[0];
            contactName.clear();leadName.clear();status.clear();telephoneNumber.clear();mobileNumber.clear();
            email.clear();addressLine1.clear();addressLine2.clear();city.clear();state.clear();zipCode.clear();landMark.clear();leadDetailsId.clear();imageUrl.clear();
            repId.clear();appointmentDate.clear();latitide.clear();longitude.clear();
            StringRequest registerRequest = new StringRequest(Request.Method.GET, registerUserURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray =jsonObject.getJSONArray("leadList");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            contactName.add(json.getString("contactName"));
                            status.add(json.getString("status"));
                            leadName.add(json.getString("leadName"));
                            telephoneNumber.add(json.getString("telephoneNumber"));
                            mobileNumber.add(json.getString("mobileNumber"));
                            email.add(json.getString("email"));
                            addressLine1.add(json.getString("addressLine1"));
                            addressLine2.add(json.getString("addressLine2"));
                            city.add(json.getString("city"));
                            state.add(json.getString("state"));
                            zipCode.add(json.getString("zipCode"));
                            country.add(json.getString("country"));
                            landMark.add(json.getString("landMark"));
                            leadDetailsId.add(json.getString("leadDetailsId"));
                            imageUrl.add(json.getString("imageUrl"));
                            repId.add(json.getString("repId"));
                            appointmentDate.add(json.getString("appointmentDate"));
                            latitide.add(json.getString("latitide"));
                            longitude.add(json.getString("longitude"));
                        }
                        adapter=new SaleLeadTrackAdapter(getContext(),contactName,status,leadName, mobileNumber,addressLine1,addressLine2,city,state,zipCode,country,landMark,leadDetailsId,imageUrl,repId,appointmentDate,latitide,longitude);
                        leadlist.setAdapter(adapter);
                    } catch (JSONException e) {
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
        protected void onPostExecute(String response) {

        }
    }*/

