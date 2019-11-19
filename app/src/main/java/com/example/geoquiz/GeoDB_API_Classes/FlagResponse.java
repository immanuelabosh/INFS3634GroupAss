package com.example.geoquiz.GeoDB_API_Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlagResponse {

    @SerializedName("data")
    @Expose
    private CountryDetails data;

    public CountryDetails getData() {
        return data;
    }

    public void setData(CountryDetails data) {
        this.data = data;
    }

}