package com.example.geoquiz.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.geoquiz.Models.CountriesResponse;
import com.example.geoquiz.Models.Leaderboard;
import com.example.geoquiz.Utils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostLeaderboardAsyncTask extends AsyncTask<Void, Void, Void> {

    private Context context;

    public void setContext(Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String URL = "https://volatile.wtf";

        final String leaderboardKey = "GeoQuizAssINFS3634";

        final String json = Utils.getPrefs("leaderboard", "", context);

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put(leaderboardKey, json);

                return params;
            }
        };
        queue.add(postRequest);
        return null;
    }
}
