package com.mitosis.adminapp;

import android.app.Dialog;
import android.content.DialogInterface;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.mitosis.adminapp.Constants.createdBy;
import static com.mitosis.adminapp.Constants.emailId;
import static com.mitosis.adminapp.Constants.firstName;
import static com.mitosis.adminapp.Constants.lastName;
import static com.mitosis.adminapp.Constants.mobileNumber;
import static com.mitosis.adminapp.Constants.password;
import static com.mitosis.adminapp.Constants.role;
import static com.mitosis.adminapp.Constants.telephoneNumber;
import static com.mitosis.adminapp.Constants.userName;

/**
 * Created by mitosis on 15/3/17.
 */

public class EmployeeListActivity extends Fragment {

    public String listEmployee_URL = "http://202.61.120.46:9081/FieldTracking/users/getEmployeeList";
    ListView list;
    TextView addNew;
    JSONObject jsonObject;

    RadioButton alphaa,alphza;
    RadioGroup sortname;
    FloatingActionButton fab;

    public EmployeeListActivity() {

    }

    public static EmployeeListActivity newInstance(String tabSelected) {
        EmployeeListActivity fragment = new EmployeeListActivity();
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
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.employee_list, container, false);

        list = (ListView) view.findViewById(R.id.employee_listview);
        new RegisterTask().execute(listEmployee_URL);


         fab = (FloatingActionButton)view. findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Fragment fragment = new CreateRoleActivity();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_layout_for_activity_navigation, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String firstname = firstName.get(position);
                String lastname = lastName.get(position);
                String mobnum = mobileNumber.get(position);
                String mail = emailId.get(position);
                String Role = role.get(position);
                String username = userName.get(position);


                Bundle args = new Bundle();
                Fragment fragment = new EmployeeDetailsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                args.putString("firstName", firstname);
                args.putString("lastName", lastname);
                args.putString("mobileNumber", mobnum);
                args.putString("emailId", mail);
                args.putString("role", Role);
                args.putString("userName", username);

                fragment.setArguments(args);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_layout_for_activity_navigation, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        });

        return view;
    }

    private class RegisterTask extends AsyncTask<String, Void, Void> {

        public RegisterTask() {
        }

        @Override
        protected Void doInBackground(String... params) {

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            String registerUserURL = params[0];
            firstName.clear();
            lastName.clear();
            telephoneNumber.clear();
            mobileNumber.clear();
            emailId.clear();
            role.clear();
            userName.clear();
            password.clear();
            createdBy.clear();

            StringRequest registerRequest = new StringRequest(Request.Method.GET, registerUserURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            jsonObject = jsonArray.getJSONObject(i);

                            firstName.add(jsonObject.getString("firstName"));
                            lastName.add(jsonObject.getString("lastName"));
                            telephoneNumber.add(jsonObject.getString("telephoneNumber"));
                            mobileNumber.add(jsonObject.getString("mobileNumber"));
                            emailId.add(jsonObject.getString("emailId"));
                            role.add(jsonObject.getString("role"));
                            userName.add(jsonObject.getString("userName"));
                            password.add(jsonObject.getString("password"));
                            createdBy.add(jsonObject.getString("createdBy"));

                            FavouritesAdapter adapter = new FavouritesAdapter(getContext(), firstName, lastName, role);
                            list.setAdapter(adapter);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().setTitle("DASHBOARD");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
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
        final View dialogView = inflater.inflate(R.layout.sort, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Sort By");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                sortname = (RadioGroup) dialogView.findViewById(R.id.sortnotify);
                int selectedId = sortname.getCheckedRadioButtonId();
                alphaa = (RadioButton) dialogView.findViewById(R.id.sortaz);
                alphza = (RadioButton) dialogView.findViewById(R.id.sortza);
                switch (selectedId) {

                    case R.id.sortaz:

                   //     new RegisterTask().execute(Constants.sortaz);

                        break;
                    case R.id.sortza:
                      //  new RegisterTask().execute(Constants.sortza);

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