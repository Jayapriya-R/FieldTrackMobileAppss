package com.mitosis.salesmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import utils.Utils;

/**
 * Created by mitosis on 9/3/17.
 */

public class ResetpasswordFragment extends Fragment {

    EditText oldPassword, newPassword,username;
    TextView submit;

    JSONObject jsonObject;
    String registerUserURL="http://202.61.120.46:9081/FieldTracking/users/changePassword";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.resetpassword, container, false);


        username = (EditText)view.findViewById(R.id.edit_username);
        oldPassword = (EditText)view.findViewById(R.id.edit_oldpass);
        newPassword = (EditText)view.findViewById(R.id.edit_newpass);
        submit = (TextView)view.findViewById(R.id.text_donepass);


        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                jsonObject = new JSONObject();
                try {
                    jsonObject.put("userName", username.getText().toString());
                    jsonObject.put("oldPassword", oldPassword.getText().toString());
                    jsonObject.put("newPassword", newPassword.getText().toString());

                    new MyAsyncTask(getActivity()).execute();

                } catch (Exception e) {

                }
            }
        });

return view;
    }

    class MyAsyncTask extends AsyncTask<String, String, String> {
        Activity mContex;

        public MyAsyncTask(Activity context) {
            this.mContex = context;
        }

        protected String doInBackground(String... params) {

            String WEB_RESULT = Utils.WebCall(registerUserURL, jsonObject.toString());
            return WEB_RESULT;
        }

        @Override
        protected void onPostExecute(String result) {

            System.out.println("result="+result);

            if (result.equals("Password has reset successfully\n")) {

                Toast.makeText(getContext(),"Password has reset successfully",Toast.LENGTH_SHORT).show();

                Intent main=new Intent(getContext(),LoginPageActivity.class);
                startActivity(main);
            }

            else{
                Toast.makeText(getContext(),"User Name/Password is incorrect",Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        getActivity().setTitle("CHANGE PASSWORD");
/*
        MenuItem item = menu.findItem(R.id.action_mainMenu3);
        item.setVisible(false);
        MenuItem item2 = menu.findItem(R.id.action_mainMenu2);
        item2.setVisible(false);
        MenuItem item3 = menu.findItem(R.id.action_search);
        item3.setVisible(false);
        MenuItem item4 = menu.findItem(R.id.action_details);
        item4.setVisible(false);*/
        super.onCreateOptionsMenu(menu, inflater);
    }
}