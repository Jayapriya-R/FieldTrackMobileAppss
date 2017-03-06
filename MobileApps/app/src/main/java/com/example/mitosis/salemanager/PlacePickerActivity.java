package com.example.mitosis.salemanager;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import utils.Utils;

import static android.app.Activity.RESULT_OK;


public class PlacePickerActivity extends Fragment {

    GoogleApiClient mGoogleApiClient;

    private final int PLACE_PICKER_REQUEST = 1000;

    EditText mAddress;
    EditText mPhone;
    ImageView imap;
    Button btnsubmit;
    EditText Clientname,Desitination,mAddress2;
    EditText city,state,zipCode,assignto;

    private Bitmap bitmap;
    private Uri filePath;
    private Button buttonChoose;
    private ImageView imageView;
    private int PICK_IMAGE_REQUEST = 1;

    JSONObject jsonObject;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_maps, container, false);

        imap=(ImageView)view.findViewById(R.id.imageicon);
        btnsubmit = (Button) view.findViewById(R.id.submit);

        mAddress=(EditText)view.findViewById(R.id.edit_add1);
        mAddress2=(EditText)view.findViewById(R.id.edit_add2);
        mPhone=(EditText)view.findViewById(R.id.edit_mobNum);
        Clientname = (EditText) view.findViewById(R.id.edit_docname);
        city = (EditText) view.findViewById(R.id.edit_city);
        state = (EditText) view.findViewById(R.id.edit_state);
        zipCode = (EditText) view.findViewById(R.id.edit_pin);
        Desitination = (EditText) view.findViewById(R.id.edit_docdes);
        assignto = (EditText) view.findViewById(R.id.edit_assignTo);


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jsonObject=new JSONObject();
                try {
                    jsonObject.put("drName",Clientname.getText().toString());
                    jsonObject.put("leadName",Desitination.getText().toString());
                    jsonObject.put("resiName",mAddress2.getText().toString());
                    jsonObject.put("resiNum",mAddress.getText().toString());
                    jsonObject.put("city",city.getText().toString());
                    jsonObject.put("region",state.getText().toString());
                    jsonObject.put("country","India");
                    jsonObject.put("ZipCode",zipCode.getText().toString());
                    jsonObject.put("createdBy","1");
                    jsonObject.put("reportingTo",assignto.getText().toString());
                    jsonObject.put("landMark","windmare appt");
                    jsonObject.put("telepNum","7848938499");
                    jsonObject.put("mobNum",mPhone.getText().toString());
                    jsonObject.put("email","ghousia.khan@gmail.com");

                    new MyAsyncTask(getActivity()).execute();


                } catch (Exception e) {

                }

                /* final String clientname = Clientname.getText().toString();
                if (!isValidClientname(clientname)) {
                    Clientname.setError("Invalid ClientName");
                }
                final String hospitalname = Hospitalname.getText().toString();
                if (!isValidHospitalname(hospitalname)) {
                    Hospitalname.setError("Invalid Hospitalname");
                }
               final String add1 = mAddress.getText().toString();
                if (!isValidAdd1(add1)) {
                    mAddress.setError("Invalid Address1");
                }

                final String add2 = mAddress2.getText().toString();
                if (!isValidAdd2(add2)) {
                    mAddress2.setError("Invalid Address2");
                }

                final String phone = mPhone.getText().toString();
                if (!isValidPhone(phone)) {
                    mPhone.setError("Invalid Phone");
                    Toast.makeText(getContext(), "Fill All The Filed", Toast.LENGTH_LONG).show();
                }
            }

            private boolean isValidAdd2(String add2) {
                if (add2 != null && add2.length() > 0) {
                    return true;
                }
                return false;
            }

            private boolean isValidAdd1(String add1) {
                if (add1 != null && add1.length() > 0) {
                    return true;
                }
                return false;
            }

            private boolean isValidHospitalname(String hospitalname) {
                if (hospitalname != null && hospitalname.length() > 0) {
                    return true;
                }
                return false;
            }

            private boolean isValidClientname(String clientname) {
                if (clientname != null && clientname.length() > 0) {
                    return true;
                }
                return false;
            }

            private boolean isValidPhone(String phone) {
                if (phone != null && phone.length() > 9) {
                    return true;
                }
                return false;*/
            }
        });

        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        imap.setOnClickListener(new View.OnClickListener() {
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

     /*  buttonChoose = (Button)view. findViewById(R.id.btn_upload);
        imageView = (ImageView)view. findViewById(R.id.image_upload);

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == buttonChoose) {
                    showFileChooser();
                }
            }
        });*/
        return view;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onStart() {
        super.onStart();
        if( mGoogleApiClient != null )
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {
        if( requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK ) {
            displayPlace( PlacePicker.getPlace( data, getActivity() ) );
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void displayPlace( Place place ) {
        if( place == null )
            return;

        String content = "";
        String content1="";
        String content2="";
        if( !TextUtils.isEmpty( place.getName() ) ) {
            content += "" + place.getName() + "\n";
        }

        if( !TextUtils.isEmpty( place.getAddress() ) ) {
            content1 += "" + place.getAddress() + "\n";
        }
        if( !TextUtils.isEmpty( place.getPhoneNumber() ) ) {
            content2 += "" + place.getPhoneNumber();
        }

        Clientname.setText( content );
        mAddress.setText( content1 );
        mPhone.setText( content2 );
    }

    class MyAsyncTask extends AsyncTask<String, String, String> {
        Activity mContex;

        public MyAsyncTask(Activity contex) {
            this.mContex = contex;
        }

        protected String doInBackground(String... params) {
            String registerUserURL = Constants.createloginUrl;

            String WEB_RESULT = Utils.WebCall(registerUserURL, jsonObject.toString());
            return WEB_RESULT;
        }

        @Override
        protected void onPostExecute(String result) {

            System.out.println("Output: "+result);

        }
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("CREATE LEAD");
    }
}
