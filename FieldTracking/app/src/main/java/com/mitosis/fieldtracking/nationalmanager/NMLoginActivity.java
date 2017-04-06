package com.mitosis.fieldtracking.nationalmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mitosis.fieldtracking.R;

public class NMLoginActivity extends AppCompatActivity {

    TextView login;
    EditText editmail;
    EditText editpassword;
    Context context;

    private CheckBox saveDetailsCheck;
    private SharedPreferences savePreferences;
    private SharedPreferences.Editor savePrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nmlogin);
        context = this;

        editmail = (EditText) findViewById(R.id.editText_username);
        editpassword = (EditText) findViewById(R.id.editText_password);

        login = (TextView) findViewById(R.id.text_login);

        saveDetailsCheck = (CheckBox) findViewById(R.id.checkBox_admin);

        savePreferences = getSharedPreferences("savePrefs", 0);
        savePrefsEditor = savePreferences.edit();

        saveLogin = savePreferences.getBoolean("saveLogin", false);
        if (saveLogin == true){
            editpassword.setText(savePreferences.getString("password", ""));
            editmail.setText(savePreferences.getString("email", ""));
            saveDetailsCheck.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editmail.getText().toString().equals("fieldtrackingadmin@yopmail.com") &&
                        editpassword.getText().toString().equals("admin123")) {
                    if (saveDetailsCheck.isChecked()) {
                        savePrefsEditor.putBoolean("saveLogin", true);
                        savePrefsEditor.putString("email", editmail.getText().toString());
                        savePrefsEditor.putString("password", editpassword.getText().toString());
                        savePrefsEditor.commit();
                    } else {
                        savePrefsEditor.clear();
                        savePrefsEditor.commit();
                    }
                    Intent create = new Intent(NMLoginActivity.this, NMMainActivity.class);
                    startActivity(create);

                } else {
                    Toast.makeText(getApplicationContext(), "Email or Password incorrect", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}