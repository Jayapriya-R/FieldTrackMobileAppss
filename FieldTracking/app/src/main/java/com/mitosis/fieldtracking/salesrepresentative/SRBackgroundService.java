package com.mitosis.fieldtracking.salesrepresentative;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.mitosis.fieldtracking.R;
import com.mitosis.fieldtracking.integrated.SRLoginActivity;

import org.json.JSONObject;

import utils.Utils;

public class SRBackgroundService extends Service {

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;
    int i=0;
    JSONObject jsonObject;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();

        LocalBroadcastManager.getInstance(context).registerReceiver(
                mMessageReceiver, new IntentFilter("intentKey"));

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {

                SRGPSService mGPSService = new SRGPSService(context);
                mGPSService.getLocation();
                
                double latitude = mGPSService.getLatitude();
                double longitude = mGPSService.getLongitude();
                
                Location location1 = new Location("locationA");
        		location1.setLatitude(latitude);
        		location1.setLongitude(longitude);
        		Location location2 = new Location("locationB");
        		location2.setLatitude(Double.valueOf(SRFavouritesAdapter.lat1));
        		location2.setLongitude(Double.valueOf(SRFavouritesAdapter.lng1));
        		double distance = location1.distanceTo(location2);

        		Toast.makeText(context, String.valueOf(Math.round(distance)), 500).show();
        		
        		if(Math.round(distance)<50)
        		{

                    Intent intent = new Intent(context, SRMainActivity.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder b = new NotificationCompat.Builder(context);

                    b.setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.drawable.ic_audiotrack)
                            .setTicker("Field Tracker")
                            .setContentTitle("You have reached to customer location")
                            .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                            .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                            .setContentIntent(contentIntent)
                            .setContentInfo("Info");


                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, b.build());

                    jsonObject=new JSONObject();
                    try {
                        jsonObject.put("appointmentDate", SRConstants.appointDate);
                        jsonObject.put("leadDetailsId", SRConstants.leadId);
                        jsonObject.put("status","reached");
                        jsonObject.put("repId", SRLoginActivity.userID);
                        jsonObject.put("notes","aaaaaaa");
                        String registerUserURL = SRConstants.update;
                        String WEB_RESULT = Utils.WebCall(registerUserURL, jsonObject.toString());
                        System.out.println("Output: " + WEB_RESULT);
                        Toast.makeText(context, "Reached Successfully", 500).show();

                        stopService(new Intent(SRMainActivity.act, SRBackgroundService.class));

                        Intent main = new Intent("intentKey");
                        main.putExtra("key", "mainactivity");
                        LocalBroadcastManager.getInstance(SRMainActivity.act).sendBroadcast(intent);

                    }
                    catch (Exception e) {
                        System.out.println("Output: " + e.toString());

                    }
                }
                
                handler.postDelayed(runnable, 5000);
            }
        };

        handler.postDelayed(runnable, 5000);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("key");

            Intent back=new Intent(SRMainActivity.act,SRMainActivity.class);
            startActivity(back);

        }
    };

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "Service started by user.", Toast.LENGTH_LONG).show();
    }

    class MyAsyncTask extends AsyncTask<String, String, String> {
        Activity mContex;

        public MyAsyncTask(Activity context) {
            this.mContex = context;
        }

        protected String doInBackground(String... params) {
            String registerUserURL;
            registerUserURL = SRConstants.update;
            String WEB_RESULT = Utils.WebCall(registerUserURL, jsonObject.toString());
            return WEB_RESULT;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println("Output: " + result);
            Toast.makeText(context, "Reached Successfully", 500).show();

        }
    }
}