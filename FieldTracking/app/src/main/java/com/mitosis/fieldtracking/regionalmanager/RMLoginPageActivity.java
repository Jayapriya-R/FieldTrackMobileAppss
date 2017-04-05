package com.mitosis.fieldtracking.regionalmanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mitosis.fieldtracking.R;

import org.json.JSONException;
import org.json.JSONObject;

import utils.Utils;

/**
 * Created by mitosis on 6/3/17.
 */

public class RMLoginPageActivity extends Activity {

    public JSONObject jsonObject;
    public static EditText Email;
    EditText Password;
    Button Login;
    TextView forgot;
    Context context;
    String status;
    String loginUrll;
    private CheckBox saveDetailsCheck;
    private SharedPreferences savePreferences;
    private SharedPreferences.Editor savePrefsEditor;
    private Boolean saveLogin;
    String serverresponse;
    public static String loginuserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rmlogin);

        Email = (EditText) findViewById(R.id.editText);
        Password = (EditText) findViewById(R.id.editText1);
        forgot = (TextView) findViewById(R.id.textView12);
        Login = (Button) findViewById(R.id.text_login);
        saveDetailsCheck = (CheckBox) findViewById(R.id.checkBox);

        savePreferences = getSharedPreferences("savePrefs", 0);
        savePrefsEditor = savePreferences.edit();

        saveLogin = savePreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            Email.setText(savePreferences.getString("email", ""));
            Password.setText(savePreferences.getString("password", ""));
            saveDetailsCheck.setChecked(true);
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //  if(myeditText.getText().toString().trim().length() == 0)

                if (Email.getText().toString().isEmpty()) {
                    Email.setError("Enter the UserName");
                    Email.requestFocus();
                } else if (Password.getText().toString().isEmpty()) {
                    Password.setError("Enter the Password");
                    Password.requestFocus();
                } else {
                    try {

                        loginUrll = "http://202.61.120.46:9081/FieldTracking/users/rmlogin?userName=" + Email.getText().toString() + "&password=" + Password.getText().toString();
                        jsonObject = new JSONObject();
                        new MyAsyncTask().execute();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                if (saveDetailsCheck.isChecked()) {
                    savePrefsEditor.putBoolean("saveLogin", true);
                    savePrefsEditor.putString("email", Email.getText().toString());
                    savePrefsEditor.putString("password", Password.getText().toString());
                    savePrefsEditor.commit();

                } else {

                    savePrefsEditor.clear();
                    savePrefsEditor.commit();
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(RMLoginPageActivity.this, RMForgotpageActivity.class);
                startActivity(i);
            }
        });
    }

    public class MyAsyncTask extends AsyncTask<String, String, String> {
        Activity mContex;
        String loginresponse;
        ProgressDialog progressDialog = new ProgressDialog(RMLoginPageActivity.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please for the server validation process..");
            progressDialog.setTitle("Loading..");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        protected String doInBackground(String... params) {
            String WEB_RESULT;
            WEB_RESULT = Utils.WebCall(loginUrll, jsonObject.toString());
            return WEB_RESULT;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("IO Exception")) {
                progressDialog.dismiss();
                Toast.makeText(RMLoginPageActivity.this, "Please check Internet connection", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject resultobj = new JSONObject(result);


                    resultobj = new JSONObject(result);
                    serverresponse = resultobj.getString("role");
                    String username = resultobj.getString("userName");
                    loginuserid = resultobj.getString("userId");
                    String message = resultobj.getString("rmmessage");
                    if (resultobj.getString("role").equals("Regional Manager")) {
                        progressDialog.dismiss();
                        Intent sign = new Intent(RMLoginPageActivity.this, RMMainActivity.class);
                        startActivity(sign);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(RMLoginPageActivity.this, "Username or Password Invalid.", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(RMLoginPageActivity.this, "Server under maintenance.Please try after sometimes.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }
    }
}