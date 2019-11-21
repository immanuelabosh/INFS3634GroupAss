package com.example.geoquiz.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CountriesResponse {

    @SerializedName("data")
    @Expose
    private List<Country> data = null;
    @SerializedName("links")
    @Expose
    private List<Link> links = null;
    @SerializedName("metadata")
    @Expose
    private Metadata metadata;

    public List<Country> getData() {
        return data;
    }

    public void setData(List<Country> data) {
        this.data = data;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

}

