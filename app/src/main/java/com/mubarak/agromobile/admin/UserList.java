package com.mubarak.agromobile.admin;

public class UserList {

    private String id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String institution;

    public UserList(String id, String firstname, String lastname, String username, String email, String institution){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.institution = institution;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getInstitution() {
        return institution;
    }

    public String getUsername() {
        return username;
    }
}
