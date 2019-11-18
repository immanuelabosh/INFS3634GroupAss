package com.example.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.geoquiz.QuizClasses.FlagQuestions;
import com.google.gson.Gson;

import java.util.Random;

public class FlagsQuizActivity extends AppCompatActivity {
    ImageView flagImage;
    TextView question;
    RadioGroup options;
    RadioButton[] answers = new RadioButton[4];
    Button button;
    Random random = new Random();
    Context context = getApplicationContext();
    String correctAnswer;
    int checkedRB;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flags_quiz);

        //initialise all the fields
        answers[0] = findViewById(R.id.radioButton);
        answers[1] = findViewById(R.id.radioButton2);
        answers[2] = findViewById(R.id.radioButton3);
        answers[3] = findViewById(R.id.radioButton4);
        flagImage = findViewById(R.id.imageView);
        question = findViewById(R.id.textView);
        options = findViewById(R.id.radiogroup);
        button.setOnClickListener(nextButton);
        button.setEnabled(false);
        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                button.setEnabled(true);
                checkedRB = checkedId;
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
        int offset = random.nextInt(198);
        //mod the random number to choose the flag that will be displayed
        int country = offset%4;

        //Make request to get 4 countries
        String response = Volley.makeRequest("http://geodb-free-service.wirefreethought.com/" +
                "v1/geo/countries?limit=4&offset="+ offset, context);

        //turn them into an object that can be accessed using getters and setters
        Gson gson = new Gson();
        //Countries[] objectsArray = gson.fromJson(response, Countries[].class);


        //set the correct answer
        //correctAnswer = Countries[country].getName;
        //get the flag of a random one
        //String countryID = Countries[country].getID;
        String countryID = null;
        String flagURL = Volley.makeRequest("http://geodb-free-service.wirefreethought.com/" +
                "v1/geo/countries/"+ countryID, context);

        //set the text of the radio buttons
        for(int i = 0;i < answers.length;i++){
            //answers[i].setText(Countries[i].getName);
        }

        //set the image of the flag
        Glide.with(context).load(flagURL).into(flagImage);
    }

    private void checkAnswer(){
        //check if they're clicking to go to the next question or check their answer
        if (button.getText().equals("Check")){
            //find the radio button that was checked
            RadioButton selectedAnswer = findViewById(checkedRB);
            //check if its the right answer
            if (selectedAnswer.getText() == correctAnswer){
                //display correct answer popup
            }else {
                //whoops u made a fucky wucky
            }
            //change the text of the next button
            button.setText("Next");

        //if they want to go to the next question
        }else {
            //change the question
            refreshQuestions();
            //change the text of the next button
            button.setText("Check Answer");
            //disable the button until a radio button is selected
            button.setEnabled(false);
        }
    }
}