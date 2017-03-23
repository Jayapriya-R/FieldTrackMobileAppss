package com.mitosis.adminapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mitosis on 16/3/17.
 */

public class EmployeeDetailsFragment extends Fragment {

    TextView firstname,lastname,username,role,eMail,mobnum,designation,employee,done;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.details, container, false);

        firstname=(TextView)view.findViewById(R.id.text_firstname);
        lastname=(TextView)view.findViewById(R.id.text_lastname);
        username=(TextView)view.findViewById(R.id.text_username);
        role=(TextView)view.findViewById(R.id.text_role);
        designation=(TextView)view.findViewById(R.id.text_designation);
        eMail=(TextView)view.findViewById(R.id.text_email);
        mobnum=(TextView)view.findViewById(R.id.text_mobilenum);
        employee=(TextView)view.findViewById(R.id.emp_name);
        done=(TextView)view.findViewById(R.id.text_done);


        String firstName=getArguments().getString("firstName");
        String lastName=getArguments().getString("lastName");
        String userName=getArguments().getString("userName");
        String rolE=getArguments().getString("role");
        String email=getArguments().getString("emailId");
        String mobilenum=getArguments().getString("mobileNumber");

        firstname.setText(firstName);
        lastname.setText(lastName);
        username.setText(userName);
        role.setText(rolE);
        eMail.setText(email);
        mobnum.setText(mobilenum);
        designation.setText(rolE);
        employee.setText(firstName+" "+lastName);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Intent main=new Intent(getContext(),MainActivity.class);
                startActivity(main);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.lead_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().setTitle("DETAILS");

    }
}