package com.example.geoquiz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.geoquiz.GeoDB_API_Classes.WikiDataResponse;
import com.example.geoquiz.R;
import com.google.gson.Gson;


//this handles the webview for wikipedia
public class WebViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        //change the title
        setTitle("Wikipedia");

        //init the webview
        final WebView mWebView = findViewById(R.id.webview);

        //getting the intent
        Intent intent = getIntent();
        final String wikiID = intent.getStringExtra("wikiID");

        //this is stuff i found on stackoverflow, it does things
        //https://stackoverflow.com/questions/50990374/webview-doesnt-display-anything
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        mWebView.setVerticalScrollBarEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        webSettings.setBlockNetworkLoads(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mWebView.setWebChromeClient(new WebChromeClient());


        //making the url to get the wikipedia link from the intent
        String url = "https://www.wikidata.org/w/api.php?action=wbgetentities&format=json&props=sitelinks/urls&ids=";
        url = url + wikiID + "&sitefilter=enwiki";

        final RequestQueue requestQueue =  com.android.volley.toolbox.Volley.newRequestQueue(getApplicationContext());
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replaceFirst(wikiID, "wikiID" );
                Gson gson = new Gson();
                WikiDataResponse wikiResponse = gson.fromJson(response, WikiDataResponse.class);

                //load the wikipedia link
                mWebView.loadUrl(wikiResponse.getWikiURL());

                requestQueue.stop();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error: " +
                        error.toString(), Toast.LENGTH_LONG);
                toast.show();
                requestQueue.stop();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,
                errorListener);
        requestQueue.add(stringRequest);





    }
}
