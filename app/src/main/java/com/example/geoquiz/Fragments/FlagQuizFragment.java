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

import com.example.geoquiz.Activities.FlagsQuizActivity;
import com.example.geoquiz.R;

public class FlagQuizFragment extends Fragment {

    ImageView image;
    TextView title;
    Button startQuiz;
    ImageView flagImage;
    TextView descrip;
    AnimationDrawable flagglobe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_start, container, false);

        //init all the fields
        image = view.findViewById(R.id.quizStartImage);
        title = view.findViewById(R.id.quizTitle);
        descrip = view.findViewById(R.id.quizDescrip);
        startQuiz = view.findViewById(R.id.startQuizButton);
        flagImage = view.findViewById(R.id.quizStartImage);
        flagImage.setBackgroundResource(R.drawable.earthanimation);
        flagglobe = (AnimationDrawable) flagImage.getBackground();

        //set all the texts
        title.setText("Flag Quiz");
        descrip.setText("Match these flags with the right countries!");
        startQuiz.setText("Start Quiz");
        //TODO add image to quiz fragments
        //add nice city image here
       // image.setImageResource();
        flagglobe.start();


        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FlagsQuizActivity.class);
                startActivity(intent);
            }
        });




        return view;
    }

}
