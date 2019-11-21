package com.example.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geoquiz.Activities.WebViewActivity;
import com.example.geoquiz.Models.Country;
import com.example.geoquiz.Models.Leaderboard;

import java.util.List;

//This is my recycler view adapter, i use it for my search and favourites fragment

// We need to give a type in angle brackets <> when we extend RecyclerView.Adapter
// It's saying that we want an adapter that adapts to CountryViewHolder (our custom ViewHolder)
public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {
    // class variable that holds the data that we want to adapt
    private List<Leaderboard.Scores> scoresToAdapt;


    public LeaderboardAdapter() {
    }

    public void setData(List<Leaderboard.Scores> scoresToAdapt) {
        // This is basically a Setter that we use to give data to the adapter
        this.scoresToAdapt = scoresToAdapt;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // First create a View from the layout file. It'll probably be a ViewGroup like
        // ConstraintLayout that contains more Views inside it.
        // This view now represents your entire one item.
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_item, parent, false);

        // Then create an instance of your custom ViewHolder with the View you got from inflating
        // the layout.
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LeaderboardViewHolder holder, final int position) {
        final Leaderboard.Scores scoreAtPosition = scoresToAdapt.get(position);

        //show the score
        String scoreItem = scoreAtPosition.getUsername() + ": " + scoreAtPosition.getScore();
        holder.score.setText(scoreItem);

    }

    @Override
    public int getItemCount() {
        return scoresToAdapt.size();
    }


    // ViewHolder represents one item, but doesn't have data when it's first constructed.
    // We assign the data in onBindViewHolder.
    public static class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView score;

        // This constructor is used in onCreateViewHolder
        public LeaderboardViewHolder(View v) {
            super(v);  // runs the constructor for the ViewHolder superclass
            view = v;
            score = v.findViewById(R.id.countryName);
        }
    }


}
