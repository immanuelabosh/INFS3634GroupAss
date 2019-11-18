package com.example.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Random;

public class FlagsQuizActivity extends AppCompatActivity {
    ImageView flagImage;
    TextView question;
    RadioGroup options;
    RadioButton answer1;
    RadioButton answer2;
    RadioButton answer3;
    RadioButton answer4;
    Button button;
    Random random = new Random();
    Context context = getApplicationContext();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flags_quiz);

        //initialise all the fields
        button.setOnClickListener(nextButton);

        //set the first question
        refreshQuestions();


    }

    private View.OnClickListener nextButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkAnswer();
            refreshQuestions();
        }
    };

    private void refreshQuestions() {
        // Obtain a number between [0 - 197], this will choose our country
        int offset = random.nextInt(198);
        //i mod the random number to choose the flag that I will display
        int country = offset%4;

        //Make request to get 4 countries
        String response = Volley.makeRequest("http://geodb-free-service.wirefreethought.com/" +
                "v1/geo/countries?limit=4&offset="+ offset, context);

        //get the flag of the first one
        //String countryID = object[country];
        String countryID = null;
        String flagURL = Volley.makeRequest("http://geodb-free-service.wirefreethought.com/" +
                "v1/geo/countries/"+ countryID, context);

        //do glide stuff here
        button.setOnClickListener(nextButton);

        Glide.with(context).load(flagURL).into(flagImage);
    }

    private void checkAnswer(){
        if (options.getCheckedRadioButtonId() == answer1.getId()){

        }

    }
}