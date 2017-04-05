package com.mitosis.fieldtracking.salesrepresentative;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.mitosis.fieldtracking.R;
import com.mitosis.fieldtracking.integrated.SRLoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import utils.Utils;

import static android.app.Activity.RESULT_OK;
public class SRCreateLeadFragment extends Fragment {

    GoogleApiClient mGoogleApiClient;

    private final int PLACE_PICKER_REQUEST = 1000;

    private String KEY_IMAGE = "image";

    EditText mAddress;
    EditText mPhone;
    ImageView leadimage;
    EditText Clientname, Desitination, mAddress2;
    EditText city, state, zipCode;
    TextView spnr;
    Button create_lead;
    Bitmap resized;

    private Bitmap bitmap;
    private Uri filePath;
    ImageView imageicon;
    public String encodedImage;
    ArrayList<String> LatLong = new ArrayList<String>();

    private int PICK_IMAGE_REQUEST = 1;

    JSONObject jsonObject;
    JSONObject jsonObject1;
    String registerUserURL;

    String latitude, longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.srcreatelead, container, false);
        setHasOptionsMenu(true);

        Clientname = (EditText) view.findViewById(R.id.edit_docname);
        Desitination = (EditText) view.findViewById(R.id.edit_docdes);
        mAddress = (EditText) view.findViewById(R.id.edit_add1);
        mAddress2 = (EditText) view.findViewById(R.id.edit_add2);
        city = (EditText) view.findViewById(R.id.edit_city);
        state = (EditText) view.findViewById(R.id.edit_state);
        zipCode = (EditText) view.findViewById(R.id.edit_pin);
        mPhone = (EditText) view.findViewById(R.id.edit_mobNum);
        imageicon = (ImageView) view.findViewById(R.id.imageicon);
        leadimage = (ImageView) view.findViewById(R.id.leadimage);
        create_lead = (Button) view.findViewById(R.id.layout_create);
        spnr = (TextView) view.findViewById(R.id.btn_spinner);

        create_lead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sClientname= Clientname.getText().toString();
                String sDestination=  Desitination.getText().toString();
                String sAddress= mAddress.getText().toString();
                String sAddress2= mAddress2.getText().toString();
                String scity= city.getText().toString();
                String sstate= state.getText().toString();
                String szipcode= zipCode.getText().toString();
                String sPhone= mPhone.getText().toString();
                if (!sClientname.isEmpty()&&!sDestination.isEmpty()&&!sAddress.isEmpty()
                        &&!sAddress2.isEmpty()&&!scity.isEmpty()&&!sstate.isEmpty()&&!szipcode.isEmpty()
                        &&!sPhone.isEmpty()){

                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("contactName", sClientname);
                        jsonObject.put("leadName", sDestination);
                        jsonObject.put("addressLine1",sAddress);
                        jsonObject.put("addressLine2", sAddress2);
                        jsonObject.put("city", scity);
                        jsonObject.put("state", sstate);
                        jsonObject.put("country", "India");
                        jsonObject.put("zipCode",szipcode);
                        jsonObject.put("createdBy", SRLoginActivity.userID);
                        jsonObject.put("assignedTo", SRLoginActivity.userID);
                        jsonObject.put("landMark", "windmare appt");
                        jsonObject.put("telephoneNumber", "7848938499");
                        jsonObject.put("mobileNumber",sPhone);
                        jsonObject.put("email", "ghousia.khan@gmail.com");
                        jsonObject.put("imageType", "jpg");
                        jsonObject.put("imageBase64", encodedImage);
                        jsonObject.put("latitide", latitude);
                        jsonObject.put("longitude", longitude);
                        new MyAsyncTask().execute();

                    } catch (Exception e) {

                    }
                }else{
                    Toast.makeText(getActivity(), "Please fill all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        imageicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mGoogleApiClient == null || !mGoogleApiClient.isConnected())
                    return;

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
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

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            displayPlace(PlacePicker.getPlace(data, getActivity()));
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                leadimage.setImageBitmap(bitmap);
                resized = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                try {

                    jsonObject1 = new JSONObject();

                    jsonObject1.put("leadDetailsId", SRConstants.leadDetailsId);
                    jsonObject1.put("imageType", "jpg");
                    jsonObject1.put("imageBase64", encodedImage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void displayPlace(Place place) {
        if (place == null)
            return;

        String content = "";
        String content1 = "";
        String content3 = "";
        if (!TextUtils.isEmpty(place.getName())) {
            content += "" + place.getName() + "\n";
        }

        if (!TextUtils.isEmpty(place.getAddress())) {
            content1 += "" + place.getAddress() + "\n";
        }

        if (!TextUtils.isEmpty(place.getLatLng().latitude + " " + place.getLatLng().longitude)) {
            content3 += "" + place.getLatLng().latitude + " " + place.getLatLng().longitude;
            latitude = "" + place.getLatLng().latitude;
            longitude = "" + place.getLatLng().longitude;

        }

        Desitination.setText(content);

        String[] address = content1.split(",");
        for (String item : address) {
            System.out.println("item = " + item);

            mAddress.setText(address[0] + "," + address[1]);
            mAddress2.setText(address[2]);
            city.setText(address[3]);
            state.setText(address[4]);
            zipCode.setText(address[5]);
        }

        LatLong.add(String.valueOf(content3));
    }

    class MyAsyncTask1 extends AsyncTask<String, String, String> {
        Activity mContex;

        public MyAsyncTask1(Activity context) {
            this.mContex = context;
        }

        protected String doInBackground(String... params) {
            String registerUserURL;
            registerUserURL = SRConstants.imageUpload_URL;

            String WEB_RESULT = Utils.WebCall(registerUserURL, jsonObject1.toString());
            return WEB_RESULT;
        }

        @Override
        protected void onPostExecute(String result) {

            System.out.println("Output: " + result);

        }
    }

    class MyAsyncTask extends AsyncTask<String, String, String> {
        Activity mContex;
        String createResponse;
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(getActivity());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertdialog.setIcon(android.R.drawable.ic_menu_gallery );
            alertdialog.setTitle("IMAGE UPLOAD");
            alertdialog.setMessage("Image is not selected.Do you want to upload image?");
            alertdialog.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    leadimage.setImageResource(R.drawable.profile);

                }
            });
            alertdialog.setPositiveButton("Upload Image", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showFileChooser();
                }
            });
            alertdialog.create();
            progressDialog.setMessage("Please wait..");
            progressDialog.setTitle("Loading..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        protected String doInBackground(String... params) {
            registerUserURL = SRConstants.create;

            String WEB_RESULT = Utils.WebCall(registerUserURL, jsonObject.toString());
            try {
                JSONObject resultobj=new JSONObject(WEB_RESULT.replace("[","").replace("]",""));
                createResponse=resultobj.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return createResponse;
        }

        @Override
        protected void onPostExecute(String result) {

            System.out.println("result=" + result);

            if (result.equals("Lead created successfully")) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Lead Created Successfully", Toast.LENGTH_SHORT).show();
                getActivity().recreate();
            }
            else {
                progressDialog.dismiss();
                alertdialog.show();

            }

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.srmain, menu);
        getActivity().setTitle("CREATE LEAD");

        MenuItem item = menu.findItem(R.id.action_mainMenu3);
        item.setVisible(false);
        MenuItem item2 = menu.findItem(R.id.action_mainMenu2);
        item2.setVisible(false);
        MenuItem item3 = menu.findItem(R.id.action_search);
        item3.setVisible(false);
        MenuItem item4 = menu.findItem(R.id.action_details);
        item4.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
}