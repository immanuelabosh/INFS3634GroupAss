package com.example.geoquiz.GeoDB_API_Classes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Country {

    @PrimaryKey
    @NonNull
    @SerializedName("code")
    @Expose
    private String code;
    @Ignore
    @SerializedName("currencyCodes")
    @Expose
    private List<String> currencyCodes = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("wikiDataId")
    @Expose
    private String wikiDataId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getCurrencyCodes() {
        return currencyCodes;
    }

    public void setCurrencyCodes(List<String> currencyCodes) {
        this.currencyCodes = currencyCodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWikiDataId() {
        return wikiDataId;
    }

    public void setWikiDataId(String wikiDataId) {
        this.wikiDataId = wikiDataId;
    }

}
