package com.example.geoquiz.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.geoquiz.Models.CitiesResponse;
import com.example.geoquiz.Models.CountriesResponse;
import com.example.geoquiz.Models.Country;
import com.example.geoquiz.R;
import com.google.gson.Gson;

import java.util.List;
import java.util.Random;

public class CitiesQuizActivity extends AppCompatActivity {
    ImageView flagImage;
    TextView question;
    RadioGroup options;
    RadioButton[] answers = new RadioButton[4];
    Button button;
    Random random = new Random();
    Context context;
    String correctAnswer;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flags_quiz);

        //initialise all the fields
        answers[0] = findViewById(R.id.radioButton);
        answers[1] = findViewById(R.id.radioButton2);
        answers[2] = findViewById(R.id.radioButton3);
        answers[3] = findViewById(R.id.radioButton4);
        flagImage = findViewById(R.id.imageView);
        question = findViewById(R.id.quizQuestion);
        options = findViewById(R.id.radiogroup);
        context = getApplicationContext();
        button = findViewById(R.id.button);
        button.setOnClickListener(nextButton);
        button.setEnabled(false);
        button.setText("Check Answer");
        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (button.isEnabled() == false){
                    button.setEnabled(true);
                }
            }
        });

        //set the first question
        refreshQuestions();


    }

    private View.OnClickListener nextButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkAnswer();
        }
    };

    private void refreshQuestions() {
        // Obtain a number between [0 - 197], this will choose our country
        final int offset = random.nextInt(198);
        //mod the random number to choose the flag that will be displayed
        final int country = offset%4;

        //let the user know the question is loading
        question.setText("Loading Question...");


        String url = "http://geodb-free-service.wirefreethought.com/" +
                "v1/geo/countries?limit=4&offset="+ offset;

        final CountriesResponse[] countries = new CountriesResponse[1];
        //Make request to get 4 countries
        final RequestQueue requestQueue =  com.android.volley.toolbox.Volley.newRequestQueue(context);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //turn them into an object that can be accessed using getters and setters
                Gson gson = new Gson();
                countries[0] = gson.fromJson(response, CountriesResponse.class);
                List<Country> countryData = countries[0].getData();
                //set the correct answer
                correctAnswer = countryData.get(country).getName();
                //set the text of the radio buttons
                for(int i = 0;i < answers.length;i++){
                    answers[i].setText(countryData.get(i).getName());
                }

                //get the flag of a random country
                String countryID = countryData.get(country).getCode();
                final String cityURL = "http://geodb-free-service.wirefreethought.com/" +
                        "v1/geo/cities?countryIds=" + countryID + "&offset="+offset;
                final RequestQueue requestQueues =  com.android.volley.toolbox.Volley.newRequestQueue(context);
                Response.Listener<String> responseListenerFlag = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //turn them into an object that can be accessed using getters and setters
                        Gson gson = new Gson();
                        CitiesResponse cityData = gson.fromJson(response, CitiesResponse.class);
                        question.setText("Which country is this city in: " + cityData.getData().get(0).getName());
                        requestQueues.stop();
                    }
                };

                Response.ErrorListener errorListenerFlag = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        requestQueues.stop();
                    }
                };
                StringRequest stringRequests = new StringRequest(Request.Method.GET, cityURL, responseListenerFlag,
                        errorListenerFlag);
                requestQueues.add(stringRequests);

                requestQueue.stop();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                requestQueue.stop();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,
                errorListener);
        requestQueue.add(stringRequest);




    }

    private void checkAnswer(){
        //check if they're clicking to go to the next question or check their answer
        if (button.getText().equals("Check Answer")){
            //find the radio button that was checked
            RadioButton selectedAnswer = findViewById(options.getCheckedRadioButtonId());
            //check if its the right answer
            //TODO Add a proper congratulations/chatisement for the user
            if (selectedAnswer.getText() == correctAnswer){
                //display correct answer popup
                selectedAnswer.setText("Good job");
            }else {
                //display correct answer
                selectedAnswer.setText("Bad job");

            }
            //change the text of the next button
            button.setText("Next");

        //if they want to go to the next question
        }else {
            //change the question
            refreshQuestions();
            //change the text of the next button
            button.setText("Check Answer");
            //uncheck the radio buttons
            options.clearCheck();
            //disable the button until a radio button is selected
            button.setEnabled(false);
        }
    }
}