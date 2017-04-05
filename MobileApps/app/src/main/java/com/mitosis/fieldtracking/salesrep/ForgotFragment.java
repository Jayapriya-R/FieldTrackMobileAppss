package com.mitosis.fieldtracking.salesrep;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.annotation.ThreadSafe;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.Utils;


/**
 * Created by mitosis on 9/3/17.
 */

public class ForgotFragment extends AppCompatActivity {

    EditText username;
    RelativeLayout submit;
    Context context;
    TextView resettextsucess,resettextfailed;
    JSONObject jsonObject;
    String registerUserURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);
        context=this;
        username = (EditText) findViewById(R.id.editText);
        resettextsucess=(TextView)findViewById(R.id.resettextsucess);
        resettextfailed=(TextView)findViewById(R.id.resettextfailed);

        submit = (RelativeLayout) findViewById(R.id.rel_change);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    registerUserURL = "http://202.61.120.46:9081/FieldTracking/users/forgotPassword?userName=" + username.getText().toString();
                    jsonObject = new JSONObject();
                    new ForgotpasswordAsynctask().execute();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public class ForgotpasswordAsynctask extends AsyncTask<String, String, String> {
        Activity mContex;
        ProgressDialog progressDialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait..");
            progressDialog.setTitle("Loading..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            resettextsucess.setVisibility(View.GONE);
            resettextfailed.setVisibility(View.GONE);

        }

        @Override
        protected String doInBackground(String... params) {
            String WEB_RESULT = Utils.WebCall(registerUserURL, jsonObject.toString());
            publishProgress(WEB_RESULT);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return WEB_RESULT;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if (values[0].equals("password mail has sent\n")) {
                progressDialog.dismiss();
                resettextsucess.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("password mail has sent\n")) {
                  finish();
            }else{
                progressDialog.dismiss();
                resettextfailed.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        ForgotFragment.this.setTitle("FORGOT PASSWORD");
        return true;
    }
}