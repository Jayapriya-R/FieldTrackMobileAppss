package com.mitosis.fieldtracking.regionalmanager;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mitosis.fieldtracking.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mitosis on 22/3/17.
 */

public class RMProfileFragment extends Fragment {

    TextView firstname,lastname,telenum,mobnum,email,role,username,changepassword,createdby,userid,Done;
     ImageView leadimage;

    public static String profileImage,firstName,lastName,teleNum,mobNum,eMail,Role,userName,changePassword,createdBy,userId;

    String registerUserURL;

    JSONObject jsonObject;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.rmprofile, container, false);

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
        Done = (TextView) view.findViewById(R.id.text_done);
        leadimage = (ImageView) view.findViewById(R.id.leadimage);

        new RegisterTask().execute();

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new RMResetpasswordFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_layout_for_activity_navigation, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent main=new Intent(getContext(),RMMainActivity.class);
                startActivity(main);
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

                 registerUserURL ="http://202.61.120.46:9081/FieldTracking/users/getProfileDetails?userName="+ RMLoginPageActivity.Email.getText().toString();

                StringRequest registerRequest = new StringRequest(Request.Method.GET, registerUserURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            jsonObject = new JSONObject(response);

                                firstName=(jsonObject.getString("firstName"));
                                lastName=(jsonObject.getString("lastName"));
                                teleNum=(jsonObject.getString("telephoneNumber"));
                                mobNum=(jsonObject.getString("mobileNumber"));
                                eMail=(jsonObject.getString("emailId"));
                                Role=(jsonObject.getString("role"));
                                userName=(jsonObject.getString("userName"));
                                changePassword=(jsonObject.getString("password"));
                                createdBy=(jsonObject.getString("createdBy"));
                                userId=(jsonObject.getString("userId"));
                               profileImage = jsonObject.getString("imageUrl");




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

                Picasso.with(getContext())
                        .load(profileImage)
                        .placeholder(R.drawable.placeholder)   // optional
                        .error(R.drawable.error)     // optional
                        .resize(400,400)                        // optional
                        .into(leadimage);
                progressDialog.dismiss();
            }
        }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.rmmain, menu);
        getActivity().setTitle("PROFILE");

        MenuItem item = menu.findItem(R.id.action_mainMenu3);
        item.setVisible(true);
        MenuItem item2 = menu.findItem(R.id.action_mainMenu2);
        item2.setVisible(false);
        MenuItem item3 = menu.findItem(R.id.action_search);
        item3.setVisible(false);
        MenuItem item4 = menu.findItem(R.id.action_details);
        item4.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mainMenu3:

                String fName = firstname.getText().toString();
                String lName = lastname.getText().toString();
                String telenumber = telenum.getText().toString();
                String mobilenumber = mobnum.getText().toString();
                String emaiL = email.getText().toString();
                String Username =username.getText().toString();
                String images =leadimage.getResources().toString();

                Bundle args = new Bundle();
                Fragment fragment = new RMProfileUpdateFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                args.putString("firstName", fName);
                args.putString("lastName", lName);
                args.putString("telephoneNumber", telenumber);
                args.putString("mobileNumber", mobilenumber);
                args.putString("emailId", emaiL);
                args.putString("userName",Username);
                args.putString("imageUrl",images);

                fragment.setArguments(args);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_layout_for_activity_navigation, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                return true;

                default:

                return super.onOptionsItemSelected(item);
        }
    }
}