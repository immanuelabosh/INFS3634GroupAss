package com.example.geoquiz.Fragments;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.geoquiz.Activities.CitiesQuizActivity;
import com.example.geoquiz.R;

public class CityQuizFragment extends Fragment {

    ImageView image;
    TextView title;
    Button startQuiz;
    ImageView cityImage;
    AnimationDrawable globe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_start, container, false);
        //init all the fields
        image = view.findViewById(R.id.quizStartImage);
        title = view.findViewById(R.id.quizTitle);
        startQuiz = view.findViewById(R.id.startQuizButton);
        cityImage = view.findViewById(R.id.quizStartImage);
        cityImage.setBackgroundResource(R.drawable.earthanimation);
        globe = (AnimationDrawable) cityImage.getBackground();

        //set all the texts
        title.setText("City Quiz");
        startQuiz.setText("Start Quiz");

        globe.start();


        //TODO add image to quiz fragments
        //add flag image here
        // image.setImageResource();

        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CitiesQuizActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }

}
