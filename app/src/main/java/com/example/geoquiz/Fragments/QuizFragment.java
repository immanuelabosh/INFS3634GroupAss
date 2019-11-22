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

import com.example.geoquiz.Activities.QuizActivity;
import com.example.geoquiz.R;

public class QuizFragment extends Fragment {

    ImageView image;
    TextView title;
    Button easyQuiz;
    Button medQuiz;
    Button hardQuiz;
    ImageView quizImage;
    AnimationDrawable globe;
    String quizType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_start, container, false);

        //get quiz type
        quizType = this.getArguments().getString("quizType");

        //init all the fields
        image = view.findViewById(R.id.quizStartImage);
        title = view.findViewById(R.id.quizTitle);
        easyQuiz = view.findViewById(R.id.easyQuizButton);
        medQuiz = view.findViewById(R.id.mediumQuizButton);
        hardQuiz = view.findViewById(R.id.hardQuizButton);
        quizImage = view.findViewById(R.id.quizStartImage);



        //set the animations depending on quiz
        if (quizType == getString(R.string.flag_quiz)){
            quizImage.setImageResource(R.drawable.flagearthanimation);
        }else {
            quizImage.setImageResource(R.drawable.earthanimation);
        }
        easyQuiz.setText("Easy");
        medQuiz.setText("Medium");
        hardQuiz.setText("Hard");

        title.setText(quizType);
        globe = (AnimationDrawable) quizImage.getDrawable();
        globe.start();


        easyQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz(2);
            }
        });
        medQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz(3);
            }
        });
        hardQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz(4);
            }
        });


        return view;
    }

    public void startQuiz(int difficulty){
        Intent intent = new Intent(getContext(), QuizActivity.class);
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("quizType", quizType);
        startActivity(intent);
    }

}
