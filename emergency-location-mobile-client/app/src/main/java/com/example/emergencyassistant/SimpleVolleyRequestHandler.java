package com.example.emergencyassistant;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SimpleVolleyRequestHandler {

    private RequestQueue queue;
    private Context context;

    public SimpleVolleyRequestHandler (Context context){
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

    public void sendRequest(JSONObject jsonObject){
        // localhost
//        String url = "http://10.128.20.97:8080/api/emergencies";
        String url = "http://emergency-location-service.herokuapp.com/api/emergencies";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context,
                        "Success",
                        Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context,
                        "Failure",
                        Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }


}
