package com.example.geoquiz.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.geoquiz.FlagsQuizActivity;
import com.example.geoquiz.R;

public class FlagQuizFragment extends Fragment {

    ImageView image;
    TextView title;
    Button startQuiz;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_start, container, false);

        //init all the fields
        image = view.findViewById(R.id.quizStartImage);
        title = view.findViewById(R.id.quizTitle);
        startQuiz = view.findViewById(R.id.startQuizButton);

        //set all the texts
        title.setText("Flag Quiz");
        startQuiz.setText("Start Quiz");
        //TODO add image to quiz fragments
        //add nice city image here
       // image.setImageResource();

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
