package com.mitosis.fieldtracking.salesrep;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mitosis on 22/3/17.
 */

public class ProfileFragment extends Fragment {

    TextView firstname,lastname,telenum,mobnum,email,role,username,changepassword,createdby,userid;

    public static ArrayList<String> firstName=new ArrayList<>();
    public static ArrayList<String> lastName=new ArrayList<>();
    public static ArrayList<String> teleNum=new ArrayList<>();
    public static ArrayList<String> mobNum=new ArrayList<>();
    public static ArrayList<String> eMail=new ArrayList<>();
    public static ArrayList<String> Role=new ArrayList<>();
    public static ArrayList<String> userName=new ArrayList<>();
    public static ArrayList<String> changePassword=new ArrayList<>();
    public static ArrayList<String> createdBy=new ArrayList<>();
    public static ArrayList<String> userId=new ArrayList<>();

    JSONObject jsonObject;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.profile, container, false);


        firstname = (TextView) view.findViewById(R.id.text_firstname);
        lastname = (TextView) view.findViewById(R.id.text_lastname);
        telenum = (TextView) view.findViewById(R.id.text_telenum);
        mobnum = (TextView) view.findViewById(R.id.text_mobilenum);
        email = (TextView) view.findViewById(R.id.text_email);
        role = (TextView) view.findViewById(R.id.text_role);
        username = (TextView) view.findViewById(R.id.text_username);
        changepassword = (TextView) view.findViewById(R.id.text_changepass);
        createdby = (TextView) view.findViewById(R.id.text_created);
        userid = (TextView) view.findViewById(R.id.text_userid);

        new RegisterTask().execute(Constants.profile);

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new ResetpasswordFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_layout_for_activity_navigation, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

        private class RegisterTask extends AsyncTask<String, Void, Void> {
          ProgressDialog progressDialog=new ProgressDialog(getActivity());

            public RegisterTask() {
            }

            @Override
            protected Void doInBackground(String... params) {

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                String registerUserURL = params[0];

                StringRequest registerRequest = new StringRequest(Request.Method.GET, registerUserURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            jsonObject = new JSONObject(response);

                                 firstName.add(jsonObject.getString("firstName"));
                                lastName.add(jsonObject.getString("lastName"));
                                teleNum.add(jsonObject.getString("telephoneNumber"));
                                mobNum.add(jsonObject.getString("mobileNumber"));
                                eMail.add(jsonObject.getString("emailId"));
                                Role.add(jsonObject.getString("role"));
                                userName.add(jsonObject.getString("userName"));
                                changePassword.add(jsonObject.getString("password"));
                                createdBy.add(jsonObject.getString("createdBy"));
                                userId.add(jsonObject.getString("userId"));


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
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               progressDialog.setMessage("Please wait..");
                progressDialog.setTitle("Loading..");
                progressDialog.setCancelable(false);
                progressDialog.show();
                firstName.clear();
                lastName.clear();
                teleNum.clear();
                mobNum.clear();
                eMail.clear();
                Role.clear();
                userName.clear();
                changePassword.clear();
                createdBy.clear();
                userId.clear();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                firstname.setText(firstName.toString().replace("[","").replace("]",""));
                lastname.setText(lastName.toString().replace("[","").replace("]",""));
                telenum.setText(teleNum.toString().replace("[","").replace("]",""));
                mobnum.setText(mobNum.toString().replace("[","").replace("]",""));
                email.setText(eMail.toString().replace("[","").replace("]",""));
                role.setText(Role.toString().replace("[","").replace("]",""));
                username.setText(userName.toString().replace("[","").replace("]",""));
                createdby.setText(createdBy.toString().replace("[","").replace("]",""));
                userid.setText(userId.toString().replace("[","").replace("]",""));
                progressDialog.dismiss();
            }
        }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        getActivity().setTitle("PROFILE");

        MenuItem item = menu.findItem(R.id.action_mainMenu3);
        item.setVisible(false);
        MenuItem item2 = menu.findItem(R.id.action_mainMenu2);
        item2.setVisible(false);
        MenuItem item3 = menu.findItem(R.id.action_search);
        item3.setVisible(false);
        MenuItem item4 = menu.findItem(R.id.action_details);
        item4.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
