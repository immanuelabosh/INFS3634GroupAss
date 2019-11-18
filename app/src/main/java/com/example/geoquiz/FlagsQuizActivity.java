package com.example.geoquiz;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FlagsQuizActivity extends AppCompatActivity {
    ImageView flag;
    TextView question;
    RadioGroup options;
    RadioButton answer1;
    RadioButton answer2;
    RadioButton answer3;
    RadioButton answer4;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flags_quiz);

        //Make request here

        //do glide stuff here



    }
}