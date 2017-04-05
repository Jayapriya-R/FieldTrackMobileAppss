package com.mitosis.fieldtracking.salesrep;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONObject;

import utils.Utils;

/**
 * Created by mitosis on 29/3/17.
 */

public class ForgotActivity extends AppCompatActivity {

    EditText username,newpass,confirmpass;
    RelativeLayout submit;
    Context context;

    JSONObject jsonObject;
    String registerUserURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpass);
        context = this;

        username = (EditText) findViewById(R.id.forgot_username);
        newpass = (EditText) findViewById(R.id.forgot_newpass);
        confirmpass = (EditText) findViewById(R.id.forgot_confirmpass);
        submit = (RelativeLayout) findViewById(R.id.rel_forgot);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sUsername= username.getText().toString();
                String sNewpass=  newpass.getText().toString();
                String sConfirmpass= confirmpass.getText().toString();

                if (!sUsername.isEmpty()||!sNewpass.isEmpty()||!sConfirmpass.isEmpty()){

                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("userName", sUsername);
                        jsonObject.put("newPassword", sNewpass);
                        jsonObject.put("confirmPassword",sConfirmpass);

                        new MyAsyncTask(ForgotActivity.this).execute();

                    } catch (Exception e) {

                    }
                }else{
                    Toast.makeText(context, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    class MyAsyncTask extends AsyncTask<String, String, String> {
        Activity mContex;

        ProgressDialog progressDialog = new ProgressDialog(context);


        public MyAsyncTask(Activity context) {
            this.mContex = context;
        }

        protected String doInBackground(String... params) {

            registerUserURL = Constants.forgot;

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

            if (result.equals("Password has reset successfully.\n")) {

                Toast.makeText(context, "Password has reset Successfully", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

                Intent main = new Intent(ForgotActivity.this,LoginPageActivity.class);
                startActivity(main);

            }
            else {
                Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }
    }
}