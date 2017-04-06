package com.mitosis.fieldtracking.regionalmanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.mitosis.fieldtracking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import utils.Utils;

import static android.app.Activity.RESULT_OK;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.firstname;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.lastName;
import static com.mitosis.fieldtracking.regionalmanager.RMConstants.userId;
import static com.mitosis.fieldtracking.integrated.LoginActivity.loginuserid;


public class RMCreateLeadFragment extends Fragment  implements View.OnTouchListener {

    GoogleApiClient mGoogleApiClient;

    private final int PLACE_PICKER_REQUEST = 1000;
    RMDailogAdapter adapter;
    private String KEY_IMAGE = "image";
    ListView view;
    EditText mAddress;
    EditText mPhone;
    ImageView leadimage;
    Button btnsubmit;
    EditText Clientname, Desitination, mAddress2,spinner_txt;
    EditText city, state, zipCode, assignto;
  //  TextView spinner_txt;
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
    String registerUserURL;
String clientname,desitination,address1,address2,cityy,statee,zipcodee,mphonee,spinnertxt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.rmcreatelead, container, false);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
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
        btnsubmit = (Button) view.findViewById(R.id.layout_create);
        spinner_txt = (EditText) view.findViewById(R.id.spinner_txt);
     //   spinnerimage=(ImageView)view.findViewById(R.id.spinnerimage);
        new RegisterTask().execute(RMConstants.repview);
        spinner_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerselection();
            }
        });
      /*  spinnerimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerselection();
            }
        });*/

        /*final String sClientname= Clientname.getText().toString();
        final String sDestination=  Desitination.getText().toString();
        final String sAddress= mAddress.getText().toString();
        final String sAddress2= mAddress2.getText().toString();
        final String scity= city.getText().toString();
        final String sstate= state.getText().toString();
        final String szipcode= zipCode.getText().toString();
        final String sPhone= mPhone.getText().toString();  */
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jsonObject = new JSONObject();
                try {
                    jsonObject.put("contactName",  Clientname.getText().toString());
                    jsonObject.put("leadName", Desitination.getText().toString());
                    jsonObject.put("addressLine1", mAddress.getText().toString());
                    jsonObject.put("addressLine2", mAddress2.getText().toString());
                    jsonObject.put("city",  city.getText().toString());
                    jsonObject.put("state", state.getText().toString());
                    jsonObject.put("country", "India");
                    jsonObject.put("zipCode", zipCode.getText().toString());
                    jsonObject.put("createdBy", loginuserid);
                    jsonObject.put("assignedTo", assignstask);
                    jsonObject.put("landMark", "windmare appt");
                    jsonObject.put("telephoneNumber", "7848938499");
                    jsonObject.put("mobileNumber",  mPhone.getText().toString());
                    jsonObject.put("email", "ghousia.khan@gmail.com");
                    jsonObject.put("imageType", "jpg");
                    jsonObject.put("imageBase64", encodedImage);
                    jsonObject.put("latitide", latitude);
                    jsonObject.put("longitude", longitude);
                } catch (Exception e) {
                }
             /*   awesomeValidation.addValidation(getActivity(), R.id.edit_docname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.name);
                awesomeValidation.addValidation(getActivity(), R.id.edit_docdes, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.destination);
                //awesomeValidation.addValidation(getActivity(), R.id.edit_add1, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.address);
                //awesomeValidation.addValidation(getActivity(), R.id.edit_add2, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.addresss);
                awesomeValidation.addValidation(getActivity(), R.id.edit_city, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.city);
                awesomeValidation.addValidation(getActivity(), R.id.edit_state, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.state);
             //   awesomeValidation.addValidation(getActivity(), R.id.spinner_txt, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.name);
              //  awesomeValidation.addValidation(getActivity(), R.id.edit_pin, "^\\d{6}$", R.string.pincode);
             //   awesomeValidation.addValidation(getActivity(), R.id.edit_mobNum, "^\\d{10}$", R.string.moblieno);*/

                if( Clientname.getText().toString().length() == 0 ) {
                    Clientname.requestFocus();
                    Toast.makeText(getContext(),"enter the client name",Toast.LENGTH_SHORT).show();

                }

                if( Desitination.getText().toString().length() == 0 ) {
                   // Desitination.setError("enter the destination");

                    Desitination.requestFocus();
                    Toast.makeText(getContext(),"enter the destination",Toast.LENGTH_SHORT).show();



                }

                if( mAddress.getText().toString().length() == 0 ) {
                    mAddress.requestFocus();
                    Toast.makeText(getContext(),"enter the address",Toast.LENGTH_SHORT).show();
                    //mAddress.setError("enter the address");
                }

                if( mAddress2.getText().toString().length() == 0 ) {
                   // mAddress2.setError("Enter the address");

                    mAddress2.requestFocus();

                    Toast.makeText(getContext(),"enter the destination",Toast.LENGTH_SHORT).show();

                }

                if( city.getText().toString().length() == 0 ) {
                    //city.setError("Enter the city");
                    city.requestFocus();
                    Toast.makeText(getContext(),"enter the destination",Toast.LENGTH_SHORT).show();

                }
                if( state.getText().toString().length() == 0 ) {
                    state.setError("Enter the Zipcode");
                    state.requestFocus();
                }
                if( zipCode.getText().toString().length() == 0 ) {
                    zipCode.setError("Enter the Zipcode");
                    zipCode.requestFocus();
                }

                if( mPhone.getText().toString().length() == 0 ) {
                    mPhone.setError("enter the phone number");
                    mPhone.requestFocus();
                }
                if(spinner_txt.getText().toString().length()==0)
                {

                    spinner_txt.setError("choose representive");
                    mPhone.requestFocus();

                }

               else{
                    new MyAsyncTask().execute();
                }
           /*     if(awesomeValidation.validate()) {
                    new MyAsyncTask().execute();
                    //process the data further
                }

*/
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
        if (data!=null)
        {
            // go to next activity
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
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }/*else{

            Picasso.with(getContext())
                    .load(filePath)
                    .placeholder(R.drawable.placeholder)   // optional
                    .error(R.drawable.error)     // optional
                    .resize(400,400)                    // optional
                   .into((Target) filePath);

        }*/}

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
                  /*  Bitmap drawableToBitmap (Drawable drawable) {
                        if (drawable instanceof BitmapDrawable) {
                            return ((BitmapDrawable)drawable).getBitmap();
                        }
                        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap);
                        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                        drawable.draw(canvas);

                        return bitmap;
                    }

                    public static InputStream bitmapToInputStream(Bitmap bitmap) {
                        int size = bitmap.getHeight() * bitmap.getRowBytes();
                        ByteBuffer buffer = ByteBuffer.allocate(size);
                        bitmap.copyPixelsToBuffer(buffer);
                        return new ByteArrayInputStream(buffer.array());
                    }
*/





                    Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                            R.drawable.doctor1);
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
            registerUserURL = RMConstants.create;

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
                getActivity().finish();

                Intent main = new Intent(getContext(), RMMainActivity.class);
                startActivity(main);
            }
            else {
                progressDialog.dismiss();
                alertdialog.show();
            }
        }
    }

    private void spinnerselection() {
        //Create sequence of items
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rmdailog, null);
        dialogBuilder.setView(dialogView);
        view = (ListView) dialogView.findViewById(R.id.lv);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
        dialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            Toast.makeText(getContext(),"Please choose the sale Representative",Toast.LENGTH_LONG).show();

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
                            //imageUrl.add(jsonObject.getString("imageUrl"));
                        }


                        adapter = new RMDailogAdapter(getContext(), firstname,userId);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.rmmain, menu);
        getActivity().setTitle("ADD THE NEW MEMBER");
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
 /* if(clientname.length()==0){
                    Clientname.setError("First name not entered");
                    Clientname.requestFocus();
                }
                if(desitination.length()==0){
                    Desitination.setError("Enter the destination");
                    Desitination.requestFocus();
                }
                if(address1.length()==0){
                    .mAddress.setError("Enter the address");
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