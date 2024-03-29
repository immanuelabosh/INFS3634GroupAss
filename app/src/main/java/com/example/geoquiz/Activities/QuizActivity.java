package com.example.geoquiz.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.geoquiz.Database.AppDatabase;
import com.example.geoquiz.Models.CitiesResponse;
import com.example.geoquiz.Models.CountriesResponse;


import com.example.geoquiz.Models.Country;
import com.example.geoquiz.Models.FlagResponse;
import com.example.geoquiz.R;
import com.example.geoquiz.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    ImageView flagImage;
    TextView question;
    RadioGroup options;
    List<RadioButton> answers = new ArrayList<>();
    Button button;
    Random random = new Random();
    Context context;
    String correctAnswer;
    int difficulty;
    String quizType;
    String questionQuery;
    String flagQuiz;
    AnimationDrawable load;
    int questsDone = 0;
    int questsDoneRight = 0;
    AppDatabase db;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flags_quiz);

        //get intent
        Intent intent = getIntent();
        //get quiz type (flags or cities)
        quizType = intent.getStringExtra("quizType");
        flagQuiz = getString(R.string.flag_quiz);
        //get difficulty
        difficulty = intent.getIntExtra("difficulty", 4);

        //initialise all the fields
        answers.add((RadioButton) findViewById(R.id.radioButton));
        answers.add((RadioButton) findViewById(R.id.radioButton2));
        findViewById(R.id.radioButton3).setVisibility(View.GONE);
        findViewById(R.id.radioButton4).setVisibility(View.GONE);
        if (difficulty >= 3){
            findViewById(R.id.radioButton3).setVisibility(View.VISIBLE);
            answers.add((RadioButton) findViewById(R.id.radioButton3));
        }
        if (difficulty == 4){
            findViewById(R.id.radioButton4).setVisibility(View.VISIBLE);
            answers.add((RadioButton) findViewById(R.id.radioButton4));
        }
        question = findViewById(R.id.quizQuestion);
        flagImage = findViewById(R.id.imageView);
        if (quizType.equals(flagQuiz)){
            question.setVisibility(View.GONE);
            questionQuery = "http://geodb-free-service.wirefreethought.com/v1/geo/countries/";
        }else {
            flagImage.setVisibility(View.GONE);
            questionQuery = "http://geodb-free-service.wirefreethought.com/v1/geo/cities?countryIds=";
        }
        options = findViewById(R.id.radiogroup);
        context = getApplicationContext();
        db = AppDatabase.getInstance(context);
        button = findViewById(R.id.button);
        button.setOnClickListener(nextButton);
        button.setEnabled(false);
        button.setText("Check Answer");
        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for(RadioButton radio :  answers){
                    if (radio.isChecked()){
                        radio.setBackground(getDrawable(R.drawable.shaded_background));
                    }else {
                        radio.setBackground(getDrawable(R.drawable.unshaded_background));
                    }
                }
                if (button.isEnabled() == false){
                    button.setEnabled(true);
                }
            }
        });

        //show the tutorial if it's their first time
        if (Utils.getScore(this).equals("0/0")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Add the buttons
            builder.setPositiveButton("Begin", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            //add title
            builder.setTitle("Tutorial");
            //set message and show alert
            builder.setMessage("Click the country you think matches the flag or city you are shown" +
                    ". Have fun!").show();
        }

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
        final int selectedCountry = offset % difficulty;
        //add to the total number of questions they answered
        questsDone++;

        //set the imageView to the downloading image to let the user know the image is downloading
        flagImage.setImageResource(R.drawable.loadanimation);
        load = (AnimationDrawable) flagImage.getDrawable();
        load.start();

        //Make request to get 4 countries
        List<Country> countryData = db.countryDao().getCountriesRandom(difficulty);
        correctAnswer = countryData.get(selectedCountry).getName();
        //set the text of the radio buttons
        for (int i = 0; i < answers.size(); i++) {
            answers.get(i).setText(countryData.get(i).getName());
        }
        //get the id of a random country
        String countryID = countryData.get(selectedCountry).getCode();
        //append it to the url
        String questionURL = questionQuery + countryID;
        //if its a city, get a random city by setting a random offset
        if (quizType.equals(getString(R.string.cities_quiz))) {
            questionURL = questionURL + "&offset=" + offset;
        }
        final RequestQueue requestQueues = com.android.volley.toolbox.Volley.newRequestQueue(context);
        Response.Listener<String> responseListenerFlag = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //turn them into an object that can be accessed using getters and setters
                Gson gson = new Gson();
                //if its a flag quiz
                if (quizType.equals(flagQuiz)) {
                    FlagResponse flagData = gson.fromJson(response, FlagResponse.class);
                    String imageURL = flagData.getData().getFlagImageUri();
                    //set the image of the flag
                    Utils.fetchSvg(context, imageURL, flagImage);
                //if its a cities quiz
                } else {
                    CitiesResponse cityData = gson.fromJson(response, CitiesResponse.class);
                    //if no cities are returned
                    //refresh questions
                    if (cityData.getMetadata().getTotalCount() == 0) {
                        refreshQuestions();
                        questsDone--;
                    } else {
                        question.setText("Which country is this city in: " + cityData.getData().get(0).getName());
                    }
                }
                requestQueues.stop();
            }
        };

        Response.ErrorListener errorListenerFlag = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley error", error.toString());
                requestQueues.stop();
            }
        };
        StringRequest stringRequests = new StringRequest(Request.Method.GET, questionURL, responseListenerFlag,
                errorListenerFlag);
        requestQueues.add(stringRequests);
    }


    private void checkAnswer(){
        //check if they're clicking to go to the next question or check their answer
        if (button.getText().equals("Check Answer")) {
            //find the radio button that was checked
            RadioButton selectedAnswer = findViewById(options.getCheckedRadioButtonId());
            //display correct answer
            for (RadioButton radio : answers) {
                if (radio.getText() == correctAnswer) {
                    radio.setTextColor(Color.parseColor("#00c400"));
                    radio.setBackground(getDrawable(R.drawable.shaded_background));
                }
            }
            //check if the user got it right
            if (selectedAnswer.getText() == correctAnswer) {
                //if they did congratulate them
                selectedAnswer.setText("Good job");
                //add to their score
                questsDoneRight++;
            //if they didnt chatise user
            } else {
                selectedAnswer.setText("Bad job");
                selectedAnswer.setTextColor(Color.parseColor("#ff6666"));
            }
            //change the text of the next button
            button.setText("Next");

        //if they want to go to the next question
        }else {
            RadioButton selectedAnswer = findViewById(options.getCheckedRadioButtonId());
            //change the question
            refreshQuestions();
            //change the text of the next button
            button.setText("Check Answer");
            //uncheck the radio buttons
            options.clearCheck();
            //disable the button until a radio button is selected
            button.setEnabled(false);
            //reset the radio buttons
            for(RadioButton radio :  answers){
                radio.setBackground(getDrawable(R.drawable.unshaded_background));
                radio.setTextColor(Color.parseColor("#000058"));
            }
        }
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setPositiveButton("Return home", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //update score
                Utils.addToScore(questsDoneRight, questsDone, context);
                // User clicked OK button
                pressBack();
            }
        });
        //set message and show alert
        builder.setMessage("Congratulations! You answered " + questsDoneRight + "/" + questsDone +
                " questions correctly!").show();
    }

    public void pressBack() {
        super.onBackPressed();
    }
}