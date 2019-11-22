package com.example.geoquiz.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class ProfileFragment extends Fragment implements EditTextFragment.EditTextFragmentListener{

    private TextView score;
    private TextView username;
    private RecyclerView leaderboard;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        leaderboard = view.findViewById(R.id.leaderboard);
        username = view.findViewById(R.id.Username);
        score = view.findViewById(R.id.Score);

        String json = Utils.getPrefs("leaderboard", getContext());
        String userID = Utils.getPrefs("username", getContext());
        String userScore = Utils.getScore(getContext());

        username.setText(userID);
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

    }

    //on resume, update the recyclerview
    @Override
    public void onResume() {
        super.onResume();
    }


    // Call this method to launch the edit dialog
    private void showEditDialog() {
        FragmentManager fm = getFragmentManager();
        EditTextFragment editTextFragment = EditTextFragment.newInstance("Search Breeds");
        // SETS the target fragment for use later when sending results
        editTextFragment.setTargetFragment(ProfileFragment.this, 300);
        editTextFragment.show(fm, "fragment_edit_text");
    }

}
