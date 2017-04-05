package com.mitosis.salesmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by mitosis on 9/3/17.
 */

public class ForgotpageActivity extends Activity {

    EditText email;
    Button submit;
    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);

        email = (EditText) findViewById(R.id.editText);
        submit = (Button) findViewById(R.id.btn_submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Email = email.getText().toString();

                final String Email = email.getText().toString();
                if (!isValidEmail(Email)) {
                    email.setError("Invalid Email");

                }else {

                    Intent sign = new Intent(ForgotpageActivity.this, ResetpasswordActivity.class);
                    startActivity(sign);
                }

            }

        });

    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}




