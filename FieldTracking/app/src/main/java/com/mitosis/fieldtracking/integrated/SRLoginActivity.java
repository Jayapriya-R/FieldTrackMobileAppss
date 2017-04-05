package com.mitosis.fieldtracking.integrated;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mitosis.fieldtracking.R;
import com.mitosis.fieldtracking.salesrepresentative.SRForgotFragment;
import com.mitosis.fieldtracking.salesrepresentative.SRMainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import utils.Utils;

/**
 * Created by mitosis on 6/3/17.
 */

public class SRLoginActivity extends AppCompatActivity {

    public JSONObject jsonObject;
    EditText Password;
    public static EditText Email;
    Button Login;
    TextView forgot;
    Context context;
    String registerUserURL;
    String status;
    private CheckBox saveDetailsCheck;
    private SharedPreferences savePreferences;
    private SharedPreferences.Editor savePrefsEditor;
    private Boolean saveLogin;
    public static String loginuserid;
    public static String loginUsername;
    public static String loginPassword;
    public static String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.srlogin);
        context = this;

        Email = (EditText) findViewById(R.id.editText);
        Password = (EditText) findViewById(R.id.editText1);
        forgot = (TextView) findViewById(R.id.textView12);
        Login = (Button) findViewById(R.id.text_login);
        saveDetailsCheck = (CheckBox) findViewById(R.id.checkBox);

        savePreferences = getSharedPreferences("savePrefs", 0);
        savePrefsEditor = savePreferences.edit();

        saveLogin = savePreferences.getBoolean("saveLogin", false);
        if (saveLogin == true)

        {
            Email.setText(savePreferences.getString("email", ""));
            Password.setText(savePreferences.getString("password", ""));
            saveDetailsCheck.setChecked(true);
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUsername= Email.getText().toString();
                loginPassword=  Password.getText().toString();
                if (loginUsername.isEmpty()){
                    Email.setError("Invalid Username");
                    Email.requestFocus();
                }
                else if (loginPassword.isEmpty()) {
                    Password.setError("Invalid Password");
                    Password.requestFocus();
                }
                else {
                    try {
                        registerUserURL = "http://202.61.120.46:9081/FieldTracking/users/login?userName=" + loginUsername + "&password=" + loginPassword;
                        jsonObject = new JSONObject();
                        new MyAsyncTask().execute();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SRLoginActivity.this, SRForgotFragment.class);
                startActivity(intent);

            }
        });
    }

    public class MyAsyncTask extends AsyncTask<String, String, String> {
        Activity mContex;
        String loginresponse;
        ProgressDialog progressDialog=new ProgressDialog(SRLoginActivity.this);

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
            WEB_RESULT= Utils.WebCall(registerUserURL, jsonObject.toString());
            return WEB_RESULT;
        }
        @Override
        protected void onPostExecute(String result) {
            if (result.equals("IO Exception")) {
                progressDialog.dismiss();
                Toast.makeText(SRLoginActivity.this, "Please check Internet connection", Toast.LENGTH_LONG).show();
            }else{
                try {
                    JSONObject resultobj = new JSONObject(result);
                      userID=resultobj.getString("userId");

                    if (resultobj.getString("message").equals("Login sucessfull")) {
                        if (saveDetailsCheck.isChecked()) {
                            savePrefsEditor.putBoolean("saveLogin", true);
                            savePrefsEditor.putString("email", loginUsername);
                            savePrefsEditor.putString("password", loginPassword);
                            savePrefsEditor.commit();
                        } else {
                            savePrefsEditor.clear();
                            savePrefsEditor.commit();
                        }
                        progressDialog.dismiss();
                        Intent sign = new Intent(SRLoginActivity.this, SRMainActivity.class);
                        startActivity(sign);
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(SRLoginActivity.this, "Username or Password Invalid.", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(SRLoginActivity.this, "Server under maintenance.Please try after sometimes.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }
    }
}