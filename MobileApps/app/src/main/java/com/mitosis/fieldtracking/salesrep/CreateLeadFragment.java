package com.mitosis.fieldtracking.salesrep;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
/*import com.mitosis.FieldTrackingapp.SalePerson.R;
import com.mitosis.FieldTrackingapp.SalePerson.constant.Constants;
import com.mitosis.FieldTrackingapp.SalePerson.utils.Utils;*/

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import utils.Utils;

import static android.app.Activity.RESULT_OK;

public class CreateLeadFragment extends Fragment {

    GoogleApiClient mGoogleApiClient;

    private final int PLACE_PICKER_REQUEST = 1000;

    private String KEY_IMAGE = "image";

    EditText mAddress;
    EditText mPhone;
    ImageView leadimage;
    EditText Clientname, Desitination, mAddress2;
    EditText city, state, zipCode, assignto;
    Spinner spnr;
    RelativeLayout create_lead;

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
        View view = inflater.inflate(R.layout.createlead, container, false);
        setHasOptionsMenu(true);

        Clientname = (EditText) view.findViewById(R.id.edit_docname);
        Desitination = (EditText) view.findViewById(R.id.edit_docdes);
        mAddress = (EditText) view.findViewById(R.id.edit_add1);
        mAddress2 = (EditText) view.findViewById(R.id.edit_add2);
        city = (EditText) view.findViewById(R.id.edit_city);
        state = (EditText) view.findViewById(R.id.edit_state);
        zipCode = (EditText) view.findViewById(R.id.edit_pin);
        mPhone = (EditText) view.findViewById(R.id.edit_mobNum);
        // assignto = (EditText) view.findViewById(R.id.edit_assignTo);
        imageicon = (ImageView) view.findViewById(R.id.imageicon);
        leadimage = (ImageView) view.findViewById(R.id.leadimage);
        create_lead = (RelativeLayout) view.findViewById(R.id.layout_create);
        spnr = (Spinner) view.findViewById(R.id.btn_spinner);

        String[] celebrities = {"MY SELF"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, celebrities);

        spnr.setAdapter(adapter);
        spnr.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = spnr.getSelectedItemPosition();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

        create_lead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jsonObject = new JSONObject();
                try {
                    jsonObject.put("contactName", Clientname.getText().toString());
                    jsonObject.put("leadName", Desitination.getText().toString());
                    jsonObject.put("addressLine1", mAddress.getText().toString());
                    jsonObject.put("addressLine2", mAddress2.getText().toString());
                    jsonObject.put("city", city.getText().toString());
                    jsonObject.put("state", state.getText().toString());
                    jsonObject.put("country", "India");
                    jsonObject.put("zipCode", zipCode.getText().toString());
                    jsonObject.put("createdBy", "26");
                    jsonObject.put("assignedTo", "26");
                    jsonObject.put("landMark", "windmare appt");
                    jsonObject.put("telephoneNumber", "7848938499");
                    jsonObject.put("mobileNumber", mPhone.getText().toString());
                    jsonObject.put("email", "ghousia.khan@gmail.com");
                    jsonObject.put("imageType", "jpg");
                    jsonObject.put("imageBase64", encodedImage);
                    jsonObject.put("latitide", latitude);
                    jsonObject.put("longitude", longitude);

                    new MyAsyncTask(getActivity()).execute();

                } catch (Exception e) {

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
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                try {

                    jsonObject1 = new JSONObject();

                    jsonObject1.put("leadDetailsId", Constants.leadDetailsId);
                    jsonObject1.put("imageType", "jpg");
                    jsonObject1.put("imageBase64", encodedImage);

                    // new MyAsyncTask1(getActivity()).execute();

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
        String content2 = "";
        String content3 = "";
        if (!TextUtils.isEmpty(place.getName())) {
            content += "" + place.getName() + "\n";
        }

        if (!TextUtils.isEmpty(place.getAddress())) {
            content1 += "" + place.getAddress() + "\n";
        }
       /* if (!TextUtils.isEmpty(place.getPhoneNumber())) {
            content2 += "" + place.getPhoneNumber();
        }*/
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

        //mPhone.setText(content2);
        LatLong.add(String.valueOf(content3));
    }

    class MyAsyncTask1 extends AsyncTask<String, String, String> {
        Activity mContex;

        public MyAsyncTask1(Activity context) {
            this.mContex = context;
        }

        protected String doInBackground(String... params) {
            String registerUserURL;
            registerUserURL = Constants.imageUpload_URL;

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

        public MyAsyncTask(Activity context) {
            this.mContex = context;
        }

        protected String doInBackground(String... params) {

            registerUserURL = Constants.create;

            String WEB_RESULT = Utils.WebCall(registerUserURL, jsonObject.toString());
            return WEB_RESULT;
        }

        @Override
        protected void onPostExecute(String result) {

            System.out.println("result=" + result);

            // if (result.equals("Lead created successfully"+","+"leadDetailsId"+""+","+"imageUrl"+"\n")) {

            Toast.makeText(getContext(), "Created Successfully", Toast.LENGTH_SHORT).show();

            Intent main = new Intent(getContext(), MainActivity.class);
            startActivity(main);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
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