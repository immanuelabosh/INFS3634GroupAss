package com.example.geoquiz.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.geoquiz.CitiesQuiz;
import com.example.geoquiz.R;

public class CityQuizFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flag_quiz, container, false);

        Intent intent = new Intent(getContext(), CitiesQuiz.class);
        startActivity(intent);


        return view;
    }

}
