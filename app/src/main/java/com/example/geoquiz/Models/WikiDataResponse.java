package com.example.geoquiz.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WikiDataResponse {

    @SerializedName("entities")
    @Expose
    private Entities entities;
    @SerializedName("success")
    @Expose
    private int success;

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getWikiURL(){
        return this.entities.wikiID.sitelinks.enwiki.url;
    }


    public class Entities {

        @SerializedName("wikiID")
        @Expose
        private WikiID wikiID;

        public WikiID getWikiID() {
            return wikiID;
        }

        public void setWikiID(WikiID wikiID) {
            this.wikiID = wikiID;
        }

    }
    public class WikiID {

        @SerializedName("sitelinks")
        @Expose
        private Sitelinks sitelinks;

        public Sitelinks getSitelinks() {
            return sitelinks;
        }

        public void setSitelinks(Sitelinks sitelinks) {
            this.sitelinks = sitelinks;
        }
    }


    public class Sitelinks {

        @SerializedName("enwiki")
        @Expose
        private Enwiki enwiki;

        public Enwiki getEnwiki() {
            return enwiki;
        }

        public void setEnwiki(Enwiki enwiki) {
            this.enwiki = enwiki;
        }
    }

    public class Enwiki {

        @SerializedName("url")
        @Expose
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

}