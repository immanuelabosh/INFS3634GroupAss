package com.example.geoquiz.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geoquiz.LeaderboardAdapter;
import com.example.geoquiz.Models.Leaderboard;
import com.example.geoquiz.R;
import com.example.geoquiz.Utils;
import com.google.gson.Gson;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class ProfileFragment extends Fragment implements EditTextFragment.EditTextFragmentListener{

    private TextView score;
    private TextView username;
    private RecyclerView leaderboard;
    private String userID;
    private ImageView editButton;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_title_bar, menu);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        setHasOptionsMenu(TRUE);

        leaderboard = view.findViewById(R.id.leaderboard);
        username = view.findViewById(R.id.Username);
        score = view.findViewById(R.id.Score);

        String json = Utils.getPrefs("leaderboard", "",getContext());
        userID = Utils.getUsername(getContext());
        String userScore = Utils.getScore(getContext());

        if (userID.equals("User")){
            showEditDialog();
        }
        editButton = view.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        username.setText("Hi " + userID + ", Welcome back!");
        score.setText("You've answered " + userScore + " questions correctly!");

/*
        Gson gson = new Gson();
        Leaderboard board = gson.fromJson(json, Leaderboard.class);
        List<Leaderboard.Scores> scoreboard = board.getScores();

        LeaderboardAdapter adapter = new LeaderboardAdapter();
        adapter.setData(scoreboard);
        leaderboard.setAdapter(adapter);
*/

        return view;
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        //if they entered nothing
        if (inputText.equals("")){
            //dont change the default name
        //otherwise set their username
        }else {
            Utils.setUsername(inputText, getContext());
            userID.equals(inputText);
            username.setText("Hi " + inputText + ", Welcome back!");
        }
    }

    //on resume, update the recyclerview and username
    @Override
    public void onResume() {
        super.onResume();
        username.setText("Hi " + userID + ", Welcome back!");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //when edit is clicked
        showEditDialog();
        return true;
    }

    // Call this method to launch the edit dialog
    private void showEditDialog() {
        FragmentManager fm = getFragmentManager();
        EditTextFragment editTextFragment = EditTextFragment.newInstance("Please enter your name");
        // SETS the target fragment for use later when sending results
        editTextFragment.setTargetFragment(ProfileFragment.this, 300);
        editTextFragment.show(fm, "fragment_edit_text");
    }

}
