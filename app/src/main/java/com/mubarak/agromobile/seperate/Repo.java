package com.mubarak.agromobile.seperate;

public class Repo  {
    String note_id;
    String title;
    String description;
    String youtube_link;
    String pdf_link;


    public Repo( String note_id, String title, String description, String youtube_link, String pdf_link ) {
        this.note_id = note_id;
        this.title = title;
        this.description = description;
        this.youtube_link = youtube_link;
        this.pdf_link = pdf_link;
    }



public String getNote_id(){
        return note_id;
}
public void setNote_id(String note_id){
        this.note_id = note_id;
}
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getYoutube_link() {
        return youtube_link;
    }

    public void setYoutube_link(String youtube_link) {
        this.youtube_link = youtube_link;
    }

    public String getPdf_link() {
        return pdf_link;
    }

    public void setPdf_link(String pdf_link) {
        this.pdf_link = pdf_link;
    }

}