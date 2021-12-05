package com.mubarak.agromobile;

public class NoteList {

    private String noteid;
    private String title;
    private String pdflink;

    public NoteList(String noteid,String title, String pdflink){
        this.noteid = noteid;
        this.title = title;
        this.pdflink = pdflink;

    }

    public String getNoteid(){
        return  noteid;
    }

    public String getTitle(){
        return title;
    }


    public String getPdflink(){
        return pdflink;
    }
}
