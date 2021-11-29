package com.mubarak.agromobile.admin;

public class Listing {
    private String id;
    private String title;
    private String description;
    private String youtubelink;
    private String pdflink;

    public Listing(String id, String title,String description, String youtubelink, String pdflink){
        this.id = id;
        this.title = title;
        this.description = description;
        this.youtubelink = youtubelink;
        this.pdflink = pdflink;

    }


    public String getId(){
        return id;
    }

    public String getNote(){
        return  title;
    }

    public String getDescription(){
        return description;
    }

    public String getYoutubelink(){
        return youtubelink;
    }

    public String getPdflink(){
        return pdflink;
    }
}
