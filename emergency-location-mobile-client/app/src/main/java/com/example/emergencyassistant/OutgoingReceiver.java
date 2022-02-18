package com.example.emergencyassistant;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import java.util.HashMap;
import java.util.Map;

public class OutgoingReceiver extends BroadcastReceiver {
    SimpleVolleyRequestHandler restHandler;
    private FusedLocationProviderClient client;
    private Context mainAppContext;
    public OutgoingReceiver(Context context) {
        this.mainAppContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        Toast.makeText(context,
                "Outgoing: " + number,
                Toast.LENGTH_LONG).show();

        if(number.equals("0876506847")) {
            if(client == null) {
                client = LocationServices.getFusedLocationProviderClient(mainAppContext);
            }
            if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                return;
            }
            client.getLastLocation()
                    .addOnSuccessListener((Activity) mainAppContext, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                Toast.makeText(context,
                                        "lat: " + location.getLatitude() + "\nlng: " + location.getLongitude(),
                                        Toast.LENGTH_LONG).show();

                                if (restHandler == null) {
                                    restHandler = new SimpleVolleyRequestHandler(context);
                                }
                                Map<String, String> locationParams = new HashMap<>();
                                locationParams.put("id", "3");
                                locationParams.put("latitude", String.valueOf(location.getLatitude()));
                                locationParams.put("longitude", String.valueOf(location.getLongitude()));
                                JSONObject locationObject = new JSONObject(locationParams);

                                Map<String, String> params = new HashMap<>();
                                params.put("id", "1");
                                params.put("callerName", "Daniel");
                                params.put("callerLastname", "Connaughton");
                                params.put("callerTelephoneNumber", "0871234567");
                                JSONObject requestObject = new JSONObject(params);
                                try {
                                    requestObject.put("location", locationObject);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                restHandler.sendRequest(requestObject);
                            }
                        }
                    });

        }
    }


}