package com.mitosis.fieldtracking.regionalmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mitosis.fieldtracking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.mitosis.fieldtracking.regionalmanager.RMConstants.paddressLine1;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.paddressLine2;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.pappointmentDate;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.pcity;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.pcontactName;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.pemail;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.pendingleadListOfRepresentative;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.pimageUrl;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.plandMark;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.platitude;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.pleadDetailsId;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.pleadname;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.plongitude;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.pmobileNumber;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.pnotes;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.prepId;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.pstate;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.pstatus;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.ptelephonenumber;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.pzipCode;

/**
 * Created by mitosis on 20/2/17.
 */

public class RMPendingLeadFragment extends Fragment {
    ListView pendinglist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rmpendinglead_list, container, false);
        setHasOptionsMenu(true);
        pendinglist = (ListView) view.findViewById(R.id.pending_listview);
        pending(pendingleadListOfRepresentative + 45);



        pendinglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = pcontactName.get(position);
                String statuz = pstatus.get(position);
                String Addressline1 = paddressLine1.get(position);
                String Addressline2 = paddressLine2.get(position);
                String citi = pcity.get(position);
                String statE = pstate.get(position);
                String zipcode = pzipCode.get(position);
                String mobnum = pmobileNumber.get(position);
              //  String mile=pdistanceArr.get(position);
                String lat=platitude.get(position);
                String lng = plongitude.get(position);
                String leadid = pleadDetailsId.get(position);
                String date=pappointmentDate.get(position);
                String image=pimageUrl.get(position);
                String notesss=pnotes.get(position);
                Bundle args = new Bundle();
                Fragment fragment = new RMRepresentativeDetails();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                args.putString("Name", name);
                args.putString("Status", statuz);
                args.putString("AddressLine1", Addressline1);
                args.putString("AddressLine2", Addressline2);
                args.putString("City", citi);
                args.putString("State", statE);
                args.putString("ZipCode", zipcode);
              //  args.putString("Mile", mile);
                args.putString("mobile", mobnum);
                args.putString("latitude", lat);
                args.putString("longitude", lng);
                args.putString("idLead", leadid);
                args.putString("appointmentDate", date);
                args.putString("imageUrl", image);
                args.putString("NOtes", notesss);

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
        return view;
    }

    void pending(String s) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String registerUserURL = s;
        StringRequest registerRequest = new StringRequest(Request.Method.GET, registerUserURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

pcontactName.clear();pstatus.clear();pleadname.clear();ptelephonenumber.clear();pmobileNumber.clear();pemail.clear();
                paddressLine1.clear();paddressLine2.clear();pcity.clear();pstate.clear();pzipCode.clear();plandMark.clear();
                pleadDetailsId.clear();pimageUrl.clear();prepId.clear();platitude.clear();plongitude.clear();pappointmentDate.clear();pnotes.clear();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        pcontactName.add(jsonObject.getString("contactName"));
                        pstatus.add(jsonObject.getString("status"));
                        pleadname.add(jsonObject.getString("leadName"));
                        ptelephonenumber.add(jsonObject.getString("mobileNumber"));
                        pmobileNumber.add(jsonObject.getString("mobileNumber"));
                        pemail.add(jsonObject.getString("email"));
                        paddressLine1.add(jsonObject.getString("addressLine1"));
                        paddressLine2.add(jsonObject.getString("addressLine2"));
                        pcity.add(jsonObject.getString("city"));
                        pstate.add(jsonObject.getString("state"));
                        pzipCode.add(jsonObject.getString("zipCode"));
                        plandMark.add(jsonObject.getString("landMark"));
                        pleadDetailsId.add(jsonObject.getString("leadDetailsId"));
                        pimageUrl.add(jsonObject.getString("imageUrl"));
                        prepId.add(jsonObject.getString("repId"));
                        platitude.add(jsonObject.getString("latitide"));
                        plongitude.add(jsonObject.getString("longitude"));
                        pappointmentDate.add(jsonObject.getString("appointmentDate"));
                        pnotes.add(jsonObject.getString("notes"));
                        //lat.add(json.getString("latitude"));
                        // longtitue.add(json.getString("longitude"));


                        RMPendingadapter adapter=new RMPendingadapter(getContext(),pcontactName,pstatus,pleadname,ptelephonenumber,pmobileNumber,pemail,paddressLine1,paddressLine2,
                                pcity,pstate,pzipCode,plandMark,pleadDetailsId,pimageUrl,prepId,platitude,plongitude,pappointmentDate,pnotes);
                        pendinglist.setAdapter( adapter);
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
    }
}