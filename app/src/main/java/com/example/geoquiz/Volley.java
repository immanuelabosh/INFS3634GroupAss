package com.example.geoquiz;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


public abstract class Volley {

    public static String makeRequest(String url, Context context){
        final RequestQueue requestQueue =  com.android.volley.toolbox.Volley.newRequestQueue(context);
        final String[] result = new String[1];
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result[0] = response;
                requestQueue.stop();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result[0] = "";
                requestQueue.stop();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,
                errorListener);
        requestQueue.add(stringRequest);

        return result[0];
    }
}
