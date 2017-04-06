package com.mitosis.fieldtracking.salesrepresentative;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mitosis.fieldtracking.R;
import com.mitosis.fieldtracking.integrated.LoginActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class SRMainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    private CoordinatorLayout coordinatorLayout;
    private ActionBarDrawerToggle toggle;
    Context context;
    ImageView profile;
    TextView repname;
    JSONObject jsonObject;
    String registerUserURL;

    public static String profileImage,firstName,lastName,teleNum,mobNum,eMail,Role,userName,changePassword,createdBy,userId;


    public static Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sractivity_main);
        context=this;
        new RegisterTask().execute();
        LayoutInflater layoutInflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.srnav_header_main,null);
        profile=(ImageView)view.findViewById(R.id.profile_image);
        repname=(TextView)view.findViewById(R.id.profile_name);
        Intent locationserviceIntent=new Intent(this,SRLocationListenerService.class);
        startService(locationserviceIntent);
        act=this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        homeFragment();

    }

    private class RegisterTask extends AsyncTask<String, Void, Void> {
        Activity mContex;
        String createResponse;
        ProgressDialog progressDialog = new ProgressDialog(SRMainActivity.this);
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
            registerUserURL = "http://202.61.120.46:9081/FieldTracking/users/getProfileDetails?userName="+ LoginActivity.Email.getText().toString();
            RequestQueue requestQueue = Volley.newRequestQueue(context);

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
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

           repname.setText("Welcome"+","+firstName+" "+lastName);

            Picasso.with(context)
                    .load(profileImage)
                    .placeholder(R.drawable.placeholder)   // optional
                    .error(R.drawable.profile)     // optional
                    .resize(400,400)                        // optional
                    .into(profile);
            progressDialog.dismiss();
        }
    }

    public void setToolBar(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }
    }

    public void SnackBarMessage(String message){
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        Bundle args = new Bundle();
        String title = "";
        switch (id) {
            case R.id.nav_3:
                fragmentClass = SRTabFragment.class;
                args.putString(SRConstants.FRAG_D, "Gallery");

                break;
            case R.id.nav_6:
                fragmentClass = SRProfileFragment.class;
                args.putString(SRConstants.FRAG_F, "srprofile");

                break;
            case R.id.nav_8:
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);

                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage("       Do you want to signout?");
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setPositiveButton("Signout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alertDialog.show();
                args.putString(SRConstants.FRAG_F, "srprofile");

                break;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(args);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_layout_for_activity_navigation, fragment).addToBackStack(null).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void homeFragment() {
        try {
            Bundle args = new Bundle();
            Class fragmentClass = SRTabFragment.class;
            args.putString(SRConstants.FRAG_A, "Welcome");
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(args);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_layout_for_activity_navigation, fragment).commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}