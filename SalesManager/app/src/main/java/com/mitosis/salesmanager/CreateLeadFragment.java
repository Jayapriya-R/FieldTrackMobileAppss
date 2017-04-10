package com.mitosis.salesmanager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import utils.Utils;
import static android.app.Activity.RESULT_OK;
import static com.mitosis.salesmanager.Constants.firstname;
import static com.mitosis.salesmanager.Constants.lastName;
import static com.mitosis.salesmanager.Constants.userId;
import static com.mitosis.salesmanager.LoginPageActivity.loginuserid;


public class CreateLeadFragment extends Fragment  implements View.OnTouchListener {

    GoogleApiClient mGoogleApiClient;

    private final int PLACE_PICKER_REQUEST = 1000;
    DailogAdapter adapter;
    private String KEY_IMAGE = "image";
    ListView view;
    EditText mAddress;
    EditText mPhone;
    ImageView leadimage;
    Button btnsubmit;
    EditText Clientname, Desitination, mAddress2;
    EditText city, state, zipCode, assignto;
    TextView spinner_txt;
    String name;
    private Bitmap bitmap;
    private Uri filePath;
    ImageView imageicon;
    public String encodedImage;
    ArrayList<String> LatLong = new ArrayList<String>();
    String assignstask;
    ImageView spinnerimage;
    private int PICK_IMAGE_REQUEST = 1;
    JSONObject jsonObject;
    JSONObject jsonObject1;
    String latitude, longitude;
    private AwesomeValidation awesomeValidation;

String clientname,desitination,address1,address2,cityy,statee,zipcodee,mphonee,spinnertxt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.createlead, container, false);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

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
        btnsubmit = (Button) view.findViewById(R.id.layout_create);
        spinner_txt = (TextView) view.findViewById(R.id.spinner_txt);
        spinnerimage=(ImageView)view.findViewById(R.id.spinnerimage);
        new RegisterTask().execute(Constants.repview);
        spinner_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerselection();
            }
        });
        spinnerimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerselection();
            }
        });
//        asfkjjasfojadfoafeofqeiofhqeihheqif
//        afjsdddfdfjdifdjfdijf
//        sfkafafnainoiggioqengeq
//        aiifeaifheifoeiggioqrgiorbgiorwbhoiwrbiwrbiwrvwirv
//        efqefipeqfpeqifhepfehfieqhfpiqefheqfe
        clientname=Clientname.getText().toString();
        desitination=Desitination.getText().toString();
        address1 =mAddress.getText().toString();
        address2 = mAddress2.getText().toString();
        cityy= city.getText().toString();
        statee=state.getText().toString();
        zipcodee=   zipCode.getText().toString();
        mphonee=   mPhone.getText().toString();
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jsonObject = new JSONObject();
                try {
                    jsonObject.put("contactName", clientname);
                    jsonObject.put("leadName",desitination);
                    jsonObject.put("addressLine1", address1);
                    jsonObject.put("addressLine2", address2);
                    jsonObject.put("city", cityy);
                    jsonObject.put("state", statee);
                    jsonObject.put("country", "India");
                    jsonObject.put("zipCode", zipcodee);
                    jsonObject.put("createdBy", loginuserid);
                    jsonObject.put("assignedTo", assignstask);
                    jsonObject.put("landMark", "windmare appt");
                    jsonObject.put("telephoneNumber", "7848938499");
                    jsonObject.put("mobileNumber",mphonee );
                    jsonObject.put("email", "ghousia.khan@gmail.com");
                    jsonObject.put("imageType", "jpg");
                    jsonObject.put("imageBase64", encodedImage);
                    jsonObject.put("latitide", latitude);
                    jsonObject.put("longitude", longitude);

                } catch (Exception e) {

                }
                awesomeValidation.addValidation(getActivity(), R.id.edit_docname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.name);

                awesomeValidation.addValidation(getActivity(), R.id.edit_mobNum, "^[2-9]{2}[0-9]{8}$", R.string.name);


                if (awesomeValidation.validate()) {
                    new MyAsyncTask(getActivity()).execute();
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
                    jsonObject1.put("leadDetailsId", "8");
                    jsonObject1.put("imageType", "jpg");
                    jsonObject1.put("imageBase64", encodedImage);
                    /// new MyAsyncTask1(getActivity()).execute();
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
        if (!TextUtils.isEmpty(place.getPhoneNumber())) {
            content2 += "" + place.getPhoneNumber();
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
            mAddress.setText(address[0]);
            mAddress2.setText(address[1]);
            city.setText(address[2]);
            state.setText(address[3]);
            //   zipCode.setText(address[4]);
        }
        mPhone.setText(content2);
        LatLong.add(String.valueOf(content3));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    class MyAsyncTask extends AsyncTask<String, String, String> {
        Activity mContex;

        public MyAsyncTask(Activity context) {
            this.mContex = context;
        }

        protected String doInBackground(String... params) {
            String registerUserURL;
            registerUserURL = Constants.create;
            String WEB_RESULT = Utils.WebCall(registerUserURL, jsonObject.toString());
            return WEB_RESULT;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println("result=" + result);
            Toast.makeText(getContext(), "Created Successfully", Toast.LENGTH_SHORT).show();
            Intent main = new Intent(getContext(), MainActivity.class);
            startActivity(main);
        }
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("CREATE LEAD");
    }

    private void spinnerselection() {
        //Create sequence of items
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dailog, null);
        dialogBuilder.setView(dialogView);
        view = (ListView) dialogView.findViewById(R.id.lv);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                name = firstname.get(position);
                assignstask=userId.get(position);

            }
        });
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                spinner_txt.setText(name);
                spinnertxt=spinner_txt.getText().toString();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
    private class RegisterTask extends AsyncTask<String, Void, Void> {

        public RegisterTask() {
        }
        @Override
        protected Void doInBackground(String... params) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String registerUserURL = params[0];
            firstname.clear();
            lastName.clear();
            StringRequest registerRequest = new StringRequest(Request.Method.GET, registerUserURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            firstname.add(jsonObject.getString("firstName"));
                            lastName.add(jsonObject.getString("lastName"));
                            userId.add(jsonObject.getString("userId"));
                        }
                        adapter = new DailogAdapter(getContext(), firstname,userId);
                    } catch (JSONException e) {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("RegisterActivity", error.getMessage() != null ? error.getMessage() : "");
                }
            });
            requestQueue.add(registerRequest);
            return null;
        }
    }
}
   /* if(clientname.length()==0){
                    Clientname.setError("First name not entered");
                    Clientname.requestFocus();
                }
                if(desitination.length()==0){
                    Desitination.setError("Enter the destination");
                    Desitination.requestFocus();
                }
                if(address1.length()==0){
                    mAddress.setError("Enter the address");
                    mAddress.requestFocus();
                }
                if(address2.length()==0){
                    mAddress2.setError("Enter the adress");
                    mAddress2.requestFocus();
                }


                if(statee.length()==0){
                    state.setError("Enter the adress");
                    state.requestFocus();
                }
                if(cityy.length()==0){
                    city.setError("Enter the adress");
                    city.requestFocus();
                }

                if(zipcodee.length()== 0){
                    zipCode.setError("Enter the Zipcode");
                    zipCode.requestFocus();
                }
                if(mphonee.length()==0){
                    mPhone.setError("Enter the phone");
                    mPhone.requestFocus();
                }
                if(spinnertxt.length()==0){
                    spinner_txt.setError("select the representative");
                    spinner_txt.requestFocus();
                }*/