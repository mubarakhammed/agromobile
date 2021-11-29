package com.mubarak.agromobile;

public class NoteList {

    private String note;
    private String description;
    private String youtubelink;
    private String pdflink;

    public NoteList(String note,String description, String youtubelink, String pdflink){
        this.note = note;
        this.description = description;
        this.youtubelink = youtubelink;
        this.pdflink = pdflink;

    }

    public String getNote(){
        return  note;
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
