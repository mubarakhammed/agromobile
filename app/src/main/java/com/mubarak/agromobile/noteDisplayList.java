package com.mubarak.agromobile;

public class noteDisplayList {

    private String noteid;
    private String title;
    private String description;
    private String pdfLink;
    private String youtubeLink;

    public String getNoteid() {
        return noteid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public String getYoutubeLink(){
        return youtubeLink;
    }

    public noteDisplayList(String noteid, String title, String description, String pdfLink, String youtubeLink) {
        this.noteid = noteid;
        this.title = title;
        this.description = description;
        this.pdfLink = pdfLink;
        this.youtubeLink = youtubeLink;
    }
}
