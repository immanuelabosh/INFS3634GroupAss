package com.example.geoquiz.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.geoquiz.Database.AppDatabase;
import com.example.geoquiz.Models.CountriesResponse;
import com.example.geoquiz.Models.Country;
import com.example.geoquiz.Models.Link;
import com.example.geoquiz.Utils;
import com.google.gson.Gson;

import java.util.List;


//This task queries the api and fills the database with the results
public class InsertCountriesAsyncTask extends AsyncTask<Void, Void, Void> {

    // This asynctask will also need to be given a database instance, so it can perform database
    // queries. If your Room database class is named something else, change this.
    private AppDatabase db;
    private Context context;
    private String downloadKey = "countriesDownloaded";

    public void setContext(Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //if countries are downloaded, do nothing
        if (Utils.getPrefs(downloadKey, context).equalsIgnoreCase("true")){
            //if countries arent downloaded, download them
        }else {
            this.db = AppDatabase.getInstance(context);
            makeRequest("/v1/geo/countries?limit=10");
        }
        return null;
    }

    protected void makeRequest(String tempUrl){
        String URL = "http://geodb-free-service.wirefreethought.com";
        URL += tempUrl;


        //Make request to get 10 countries
        final RequestQueue requestQueue =  com.android.volley.toolbox.Volley.newRequestQueue(context);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //turn them into an object that can be accessed using getters and setters
                Gson gson = new Gson();
                CountriesResponse countriesResponse = gson.fromJson(response, CountriesResponse.class);
                Toast toast = Toast.makeText(context, "Countries Downloaded: " + db.countryDao().getSize(), Toast.LENGTH_SHORT);
                toast.show();
                if (db.countryDao().getSize() == countriesResponse.getMetadata().getTotalCount()){
                    Utils.editPrefs(downloadKey, "true", context);
                }else {
                    List<Country> countryData = countriesResponse.getData();
                    db.countryDao().insert(countryData);
                    String nextURL = "";
                    for (Link link : countriesResponse.getLinks()) {
                        if (link.getRel().equalsIgnoreCase("next")) {
                            nextURL = link.getHref();
                        }
                    }
                    makeRequest(nextURL);
                }
                requestQueue.stop();
            }

        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                requestQueue.stop();
                Toast toast = Toast.makeText(context, "Volley error: " + error.toString(), Toast.LENGTH_LONG);
                toast.show();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, responseListener,
                errorListener);
        requestQueue.add(stringRequest);
    }

}

