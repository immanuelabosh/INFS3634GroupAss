package com.example.geoquiz.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Leaderboard {

    @SerializedName("scores")
    @Expose
    private List<Scores> scores;

    public List<Scores> getScores() {
        return scores;
    }

    public void setScores(List<Scores> scores) {
        this.scores = scores;
    }

    public class Scores {
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("score")
        @Expose
        private String score;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

    }

}
