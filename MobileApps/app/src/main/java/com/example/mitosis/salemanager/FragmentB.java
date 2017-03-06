package com.example.mitosis.salemanager;

/**
 * Created by mitosis on 18/2/17.
 */
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.mitosis.salemanager.Constants.doctorName;
import static com.example.mitosis.salemanager.Constants.doctorStatus;


public class FragmentB extends Fragment {


    ListView list;

TextView name;
    TextView status;
    Button click;

    public FragmentB() {
        // Required empty public constructor
    }


    public static FragmentB newInstance(String tabSelected) {
        FragmentB fragment = new FragmentB();
        Bundle args = new Bundle();
        args.putString(Constants.FRAG_B, tabSelected);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.repview, container, false);


        list = (ListView) view.findViewById(R.id.replist);

        //  new MyTask((Activity) getContext()).execute(Constants.loginUrl, "GET", "login");


        new RegisterTask().execute();


        return view;
    }

    private class RegisterTask extends AsyncTask<String, Void, Void> {

        public RegisterTask() {
        }

        @Override
        protected Void doInBackground(String... params) {


            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            String registerUserURL = Constants.loginUrl;

            StringRequest registerRequest = new StringRequest(Request.Method.GET, registerUserURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray=new JSONArray(response);

                        for(int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            doctorName.add(jsonObject.getString("drName"));
                            doctorStatus.add(jsonObject.getString("leadStatus"));
                            FavouritesAdapter adapter = new FavouritesAdapter(getContext(),doctorName,doctorStatus);
                            list.setAdapter(adapter);
                            System.out.println(doctorName);
                            System.out.println(doctorName);

                        }


                    } catch (JSONException e) {
                        //We should not be here
                        //spinner.setVisibility(View.GONE);
                        Log.e("RegisterActivity", e.getMessage());
                        // Paybee.get().showToast(getString(R.string.something_went_wrong), Toast.LENGTH_SHORT);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //spinner.setVisibility(View.GONE);
                    Log.e("RegisterActivity", error.getMessage() != null ? error.getMessage() : "");
                    //Paybee.get().showToast(getString(R.string.something_went_wrong), Toast.LENGTH_SHORT);
                }
            })

                    ;

            requestQueue.add(registerRequest);

            return null;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("DASHBOARD");
    }
}
