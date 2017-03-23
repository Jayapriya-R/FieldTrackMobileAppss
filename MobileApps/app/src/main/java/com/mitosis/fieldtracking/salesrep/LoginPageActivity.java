package com.mitosis.fieldtracking.salesrep;

import android.app.Activity;
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

import org.json.JSONObject;

import utils.Utils;

/**
 * Created by mitosis on 6/3/17.
 */

public class LoginPageActivity extends AppCompatActivity {

    public  JSONObject jsonObject;
    EditText Email,Password;
    Button Login;
    TextView forgot;
    Context context;
    String registerUserURL;
    String status;
    private CheckBox saveDetailsCheck;
    private SharedPreferences savePreferences;
    private SharedPreferences.Editor savePrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
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

                try {

                    registerUserURL = "http://202.61.120.46:9081/FieldTracking/users/login?userName=" + Email.getText().toString() + "&password=" + Password.getText().toString();
                    jsonObject = new JSONObject();
                 new MyAsyncTask().execute();


                } catch (Exception e1) {
                    e1.printStackTrace();
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
                /*Fragment fragment = new ForgotFragment();
                FragmentManager fragmentManager =LoginPageActivity.this.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_layout_for_activity_navigation, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
                Intent intent = new Intent(LoginPageActivity.this, ForgotFragment.class);
                startActivity(intent);

            }
        });
    }
    public class MyAsyncTask extends AsyncTask<String, String, String> {
        Activity mContex;

        public MyAsyncTask() {
            this.mContex = (Activity) context;
        }

        protected String doInBackground(String... params) {

            String WEB_RESULT = Utils.WebCall(registerUserURL, jsonObject.toString());
            return WEB_RESULT;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("Login sucessfull\n")) {
                Intent sign = new Intent(LoginPageActivity.this, MainActivity.class);
                startActivity(sign);
            }else{
                Toast.makeText(LoginPageActivity.this,
                        "Login failed",Toast.LENGTH_LONG).show();
            }

        }
    }
}