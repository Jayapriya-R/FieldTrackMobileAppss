package com.mitosis.fieldtracking.regionalmanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.mitosis.fieldtracking.R;

/**
 * Created by mitosis on 9/3/17.
 */

public class RMResetpasswordActivity extends Activity {

    EditText Password, Confirmpassword;
    Button Continue, Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rmresetpassword);


        Password = (EditText) findViewById(R.id.editText);
       /* Confirmpassword = (EditText) findViewById(R.id.editText1);
        Continue =(Button) findViewById(R.id.btn_continue);
        Cancel =(Button) findViewById(R.id.btn_cancel);

        Continue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub

                String Pass=Password.getText().toString();
                String Repass=Confirmpassword.getText().toString();

                if(Pass.equals("")||Repass.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!Pass.equals(Repass))
                {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {

                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                    Log.d("PASSWORD",Pass);
                    Log.d("RE PASSWORD",Repass);
                    Intent i=new Intent(RMResetpasswordActivity.this,RMLoginPageActivity.class);
                    startActivity(i);
                }
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Intent ii=new Intent(RMResetpasswordActivity.this,RMLoginPageActivity.class);
                startActivity(ii);
            }
        });

*/
    }
}