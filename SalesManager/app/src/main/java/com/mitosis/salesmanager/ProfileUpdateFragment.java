package com.mitosis.salesmanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import utils.Utils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by mitosis on 27/3/17.
 */

public class ProfileUpdateFragment extends Fragment {

    EditText firstname,lastname,teleNum,mobNum,eMail;
    TextView save,userName;
    JSONObject jsonObject;

    private Bitmap bitmap;
    private Uri filePath;
    ImageView leadimage;
    public String encodedImage;


    private int PICK_IMAGE_REQUEST = 1;

    String registerUserURL;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.profile_update, container, false);

        firstname=(EditText)view.findViewById(R.id.edit_firstname);
        lastname=(EditText)view.findViewById(R.id.edit_lastname);
        teleNum=(EditText)view.findViewById(R.id.edit_telenum);
        mobNum=(EditText)view.findViewById(R.id.edit_mobilenum);
        eMail=(EditText)view.findViewById(R.id.edit_email);
        userName=(TextView)view.findViewById(R.id.text_username);
        save=(TextView)view.findViewById(R.id.text_done);
        leadimage = (ImageView) view.findViewById(R.id.leadimage);


        String fName = getArguments().getString("firstName");
        String lName = getArguments().getString("lastName");
        String telenum = getArguments().getString("telephoneNumber");
        String mobnum = getArguments().getString("mobileNumber");
        String email = getArguments().getString("emailId");
        final String username = getArguments().getString("userName");
        String image = getArguments().getString("imageUrl");

        firstname.setText(fName);
        lastname.setText(lName);
        teleNum.setText(telenum);
        mobNum.setText(mobnum);
        eMail.setText(email);
        userName.setText(username);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sFirstname= firstname.getText().toString();
                String sLastname=  lastname.getText().toString();
                String stelenum= teleNum.getText().toString();
                String smobnum= mobNum.getText().toString();
                String sEmail= eMail.getText().toString();

                if (!sFirstname.isEmpty()||!sLastname.isEmpty()||!stelenum.isEmpty()||!smobnum.isEmpty()||!sEmail.isEmpty()||!username.isEmpty()){

                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("firstName", sFirstname);
                        jsonObject.put("lastName", sLastname);
                        jsonObject.put("telephoneNumber",stelenum);
                        jsonObject.put("mobileNumber", smobnum);
                        jsonObject.put("emailId", sEmail);
                        jsonObject.put("userName", username);
                        jsonObject.put("imageType", "jpg");
                        jsonObject.put("imageBase64", encodedImage);

                        new MyAsyncTask(getActivity()).execute();

                    } catch (Exception e) {

                    }
                }else{
                    Toast.makeText(getActivity(), "Please fill all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        leadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == leadimage) {
                    showFileChooser();
                }
            }
        });

        return view;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                leadimage.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class MyAsyncTask extends AsyncTask<String, String, String> {
        Activity mContex;
        ProgressDialog progressDialog=new ProgressDialog(getActivity());


        public MyAsyncTask(Activity context) {
            this.mContex = context;
        }

        protected String doInBackground(String... params) {

            registerUserURL = Constants.profileUpdate;

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

            if (result.equals("Profile updated sucessfully\n")) {
                Toast.makeText(getContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                System.out.println("result="+result);
                progressDialog.dismiss();

                Fragment fragment = new ProfileFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_layout_for_activity_navigation, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            } else {

                Toast.makeText(getContext(), "Updated Failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        getActivity().setTitle("PROFILE UPDATE");

        MenuItem item = menu.findItem(R.id.action_mainMenu3);
        item.setVisible(false);
        MenuItem item2 = menu.findItem(R.id.action_mainMenu2);
        item2.setVisible(false);
        MenuItem item3 = menu.findItem(R.id.action_search);
        item3.setVisible(false);
        MenuItem item4 = menu.findItem(R.id.mapview);
        item4.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }


}
