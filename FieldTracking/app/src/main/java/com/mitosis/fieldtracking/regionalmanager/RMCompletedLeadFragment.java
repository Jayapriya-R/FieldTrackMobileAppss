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

import static com.mitosis.fieldtracking.regionalmanager.RMConstants.caddressLine1;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.caddressLine2;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.cappointmentDate;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.ccity;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.ccontactName;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.cemail;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.cimageUrl;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.clandMark;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.clatitude;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.cleadDetailsId;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.cleadname;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.clongitude;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.cmobileNumber;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.cnotes;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.completedleadListOfRepresentative;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.crepId;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.cstate;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.cstatus;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.ctelephonenumber;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.czipCode;


/**
 * Created by mitosis on 20/2/17.
 */

public class RMCompletedLeadFragment extends Fragment {


    ListView complete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rmcompletelead_list, container, false);
        setHasOptionsMenu(true);
        complete = (ListView) view.findViewById(R.id.complete_listview);
        completedd(completedleadListOfRepresentative + 45);
        complete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = ccontactName.get(position);
                String statuz = cstatus.get(position);
                String Addressline1 = caddressLine1.get(position);
                String Addressline2 = caddressLine2.get(position);
                String citi = ccity.get(position);
                String statE = cstate.get(position);
                String zipcode = czipCode.get(position);
                String mobnum = cmobileNumber.get(position);
                //  String mile=pdistanceArr.get(position);
                String lat = clatitude.get(position);
                String lng = clongitude.get(position);
                String leadid = cleadDetailsId.get(position);
                String date = cappointmentDate.get(position);
                String image = cimageUrl.get(position);
                String notesss = cnotes.get(position);
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

    void completedd(String sss) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String registerUserURL = sss;
        StringRequest registerRequest = new StringRequest(Request.Method.GET, registerUserURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ccontactName.clear();cstate.clear();cleadname.clear();ctelephonenumber.clear();  cmobileNumber.clear();cemail.clear();caddressLine1.clear();
                caddressLine2.clear();cstate.clear();czipCode.clear();clandMark.clear();cimageUrl.clear();crepId.clear();
                clatitude.clear();cappointmentDate.clear();cnotes.clear();

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ccontactName.add(jsonObject.getString("contactName"));
                        cstatus.add(jsonObject.getString("status"));
                        cleadname.add(jsonObject.getString("leadName"));
                        ctelephonenumber.add(jsonObject.getString("mobileNumber"));
                        cmobileNumber.add(jsonObject.getString("mobileNumber"));
                        cemail.add(jsonObject.getString("email"));
                        caddressLine1.add(jsonObject.getString("addressLine1"));
                        caddressLine2.add(jsonObject.getString("addressLine2"));
                        ccity.add(jsonObject.getString("city"));
                        cstate.add(jsonObject.getString("state"));
                        czipCode.add(jsonObject.getString("zipCode"));
                        clandMark.add(jsonObject.getString("landMark"));
                        cleadDetailsId.add(jsonObject.getString("leadDetailsId"));
                        cimageUrl.add(jsonObject.getString("imageUrl"));
                        crepId.add(jsonObject.getString("repId"));
                        clatitude.add(jsonObject.getString("latitide"));
                        clongitude.add(jsonObject.getString("longitude"));
                        cappointmentDate.add(jsonObject.getString("appointmentDate"));
                        cnotes.add(jsonObject.getString("notes"));
                        //lat.add(json.getString("latitude"));
                        // longtitue.add(json.getString("longitude"));
                        RMCompleteAdapter adapter= new RMCompleteAdapter(getContext(),ccontactName,cstatus,cleadname,ctelephonenumber,cmobileNumber,cemail,caddressLine1,caddressLine2, ccity,cstate,czipCode,clandMark,cleadDetailsId,cimageUrl,crepId,clatitude,clongitude,cappointmentDate,cnotes);
                        complete.setAdapter( adapter);
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
