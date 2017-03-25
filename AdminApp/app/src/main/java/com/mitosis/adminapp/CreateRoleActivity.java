package com.mitosis.adminapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import utils.Utils;

/**
 * Created by mitosis on 13/3/17.
 */

public class CreateRoleActivity extends Fragment {

    TextView role;
    RadioButton regionalManager, natioalManager, sales;
    RadioGroup sort;
    TextView submit, Designation;
    ImageView leadimage;
    EditText firstName, lastName, userName, phoneNumber, email;
    JSONObject jsonObject;

    String registerUserURL = "http://202.61.120.46:9081/FieldTracking/users/createEmployee";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.createrole, container, false);

        role = (TextView) view.findViewById(R.id.text_designation);
        firstName = (EditText) view.findViewById(R.id.hname);
        lastName = (EditText) view.findViewById(R.id.edit_lastname);
        userName = (EditText) view.findViewById(R.id.edit_userName);
        phoneNumber = (EditText) view.findViewById(R.id.edit_phone);
        leadimage = (ImageView) view.findViewById(R.id.leadimage);
        submit = (TextView) view.findViewById(R.id.text_submit);
        Designation = (TextView) view.findViewById(R.id.text_designation);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String sFirstname= firstName.getText().toString();
                String sLastname=  lastName.getText().toString();
                String sUsername= userName.getText().toString();
                String sDesignation= Designation.getText().toString();
                String sPhone= phoneNumber.getText().toString();
                if (!sFirstname.isEmpty()||!sLastname.isEmpty()||!sUsername.isEmpty()||!sPhone.isEmpty()||!sDesignation.isEmpty()){
                 if (sUsername.contains("@")){
                     jsonObject = new JSONObject();
                     try {
                         jsonObject.put("firstName",sFirstname );
                         jsonObject.put("lastName", sLastname);
                         jsonObject.put("userName", sUsername);
                         jsonObject.put("emailId", "fieldtrackingadmin@yopmail.com");
                         jsonObject.put("role",sDesignation );
                         jsonObject.put("password", "admin123");
                         jsonObject.put("createdBy", "fieldtrackingadmin@yopmail.com");
                         jsonObject.put("telephoneNumber", "99161193");
                         jsonObject.put("mobileNumber",sPhone );
                         new MyAsyncTask(getActivity()).execute();
                     } catch (Exception e) {

                     }
                 }else {
                     Toast.makeText(getActivity(), "Invaild username.Please make sure username is email ID or not.", Toast.LENGTH_SHORT).show();
                 }
                }else{
                    Toast.makeText(getActivity(), "Please fill all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        role.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLangDialog();
            }
        });
        return view;
    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Choose Designation");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                sort = (RadioGroup) dialogView.findViewById(R.id.sort);
                int selectedId = sort.getCheckedRadioButtonId();
                natioalManager = (RadioButton) dialogView.findViewById(R.id.radio_nationalManager);
                regionalManager = (RadioButton) dialogView.findViewById(R.id.radio_regionalManager);
                sales = (RadioButton) dialogView.findViewById(R.id.radio_sales);

                switch (selectedId) {

                    case R.id.radio_nationalManager:
                        role.setText("National Manager");

                        break;

                    case R.id.radio_regionalManager:

                        role.setText("Regional Manager");

                        break;
                    case R.id.radio_sales:
                        role.setText("Representative");

                        break;
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

    class MyAsyncTask extends AsyncTask<String, String, String> {
        Activity mContex;
  ProgressDialog progressDialog=new ProgressDialog(getActivity());
        public MyAsyncTask(Activity context) {
            this.mContex = context;
        }

        protected String doInBackground(String... params) {

            String WEB_RESULT = Utils.WebCall(registerUserURL, jsonObject.toString());
            return WEB_RESULT;
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
        protected void onPostExecute(String result) {

            System.out.println("result=" + result);

            if (result.equals("")) {
                Toast.makeText(getContext(), "Created Successfully", Toast.LENGTH_SHORT).show();

                Fragment fragment = new EmployeeListActivity();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_layout_for_activity_navigation, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                firstName.setText("");
                lastName.setText("");
                userName.setText("");
                Designation.setText("");
                phoneNumber.setText("");
                progressDialog.dismiss();
            } else {

                Toast.makeText(getContext(), "Created Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().setTitle("ADD NEW MEMBER");
        MenuItem item4 = menu.findItem(R.id.action_settings);
        item4.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);

    }
}