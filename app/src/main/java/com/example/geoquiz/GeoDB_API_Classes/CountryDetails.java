package com.example.geoquiz.GeoDB_API_Classes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryDetails {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("currencyCodes")
    @Expose
    private List<String> currencyCodes = null;
    @SerializedName("flagImageUri")
    @Expose
    private String flagImageUri;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("numRegions")
    @Expose
    private int numRegions;
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

    public String getFlagImageUri() {
        return flagImageUri;
    }

    public void setFlagImageUri(String flagImageUri) {
        this.flagImageUri = flagImageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumRegions() {
        return numRegions;
    }

    public void setNumRegions(int numRegions) {
        this.numRegions = numRegions;
    }

    public String getWikiDataId() {
        return wikiDataId;
    }

    public void setWikiDataId(String wikiDataId) {
        this.wikiDataId = wikiDataId;
    }

}
