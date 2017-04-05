package com.mitosis.fieldtracking.regionalmanager;

/**
 * Created by mitosis on 18/2/17.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

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

import static com.mitosis.fieldtracking.regionalmanager.RMConstants.completecount;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.firstname;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.lastName;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.pendingcount;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.totalcount;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.username;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.uuuserId;


public class RMTotalLeadFragment extends Fragment {
    private Context context;
    RadioButton alphaa, distance;
    RadioGroup sort;
    FloatingActionButton fab;
    ListView list;
    private static RMTotalLeadAdapter adapter;
    FloatingActionButton flat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rmrepview, container, false);
        setHasOptionsMenu(true);
        list = (ListView) view.findViewById(R.id.simple_expandable_listview);
        new LeadTask().execute(RMConstants.repviewtotal);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String firstnamee = firstname.get(position);
                String lastnamee = lastName.get(position);
                String pendingcountt = pendingcount.get(position);
                String completecountt = completecount.get(position);
                String totalcountt = totalcount.get(position);
                String useridd = uuuserId.get(position);
                String usernamee = username.get(position);
                Bundle args = new Bundle();
                Fragment fragment = new RMSaleLeadTrackDetail();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                args.putString("firstname", firstnamee);
                args.putString("lastname", lastnamee);
                args.putString("pendingcount", pendingcountt);
                args.putString("completecount", completecountt);
                args.putString("totalcount", totalcountt);
                args.putString("userid", useridd);
                args.putString("usernames", usernamee);
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
        flat=(FloatingActionButton)view.findViewById(R.id.fab);
        flat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new RMCreateLeadFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                        R.anim.fragment_slide_left_exit,
                        R.anim.fragment_slide_right_enter,
                        R.anim.fragment_slide_right_exit);
                //   fragmentTransaction.setCustomAnimations(R.anim.slide_in_up,R.anim.slide_in_down,R.anim.slide_out_up,R.anim.slide_out_down);
                fragmentTransaction.replace(R.id.fragment_layout_for_activity_navigation, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
    private class LeadTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String registerUserURL = params[0];
            firstname.clear();lastName.clear();totalcount.clear();
            completecount.clear();pendingcount.clear();
            StringRequest registerRequest = new StringRequest(Request.Method.GET, registerUserURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            firstname.add(jsonObject.getString("firstName"));
                            lastName.add(jsonObject.getString("lastName"));
                            totalcount.add(jsonObject.getString("totalCount"));
                            completecount.add(jsonObject.getString("completedCount"));
                            pendingcount.add(jsonObject.getString("pendingCount"));
                            username.add(jsonObject.getString("userName"));
                            uuuserId.add(jsonObject.getString("userId"));
                        }
                    } catch (JSONException e) {
                    }
                    adapter = new RMTotalLeadAdapter(getContext(),firstname,lastName,totalcount,completecount,pendingcount ,username,uuuserId);
                    list.setAdapter(adapter);
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
 /*   void alterbox() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rmmessage, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.rmmain, menu);
        MenuItem item2 = menu.findItem(R.id.action_mainMenu2);
        item2.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().setTitle("DASHBOARD");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_details:
                startActivity(new Intent(getActivity(),RMMapsActivity.class));
         return true;
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
        final View dialogView = inflater.inflate(R.layout.rmleadsort, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Sort By");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                sort = (RadioGroup) dialogView.findViewById(R.id.sort);
                int selectedId = sort.getCheckedRadioButtonId();
                switch (selectedId) {
                    case R.id.sortaz:
                        new LeadTask ().execute(RMConstants.repaz);
                        break;
                    case R.id.sortza:
                        new LeadTask ().execute(RMConstants.repza);
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






}

