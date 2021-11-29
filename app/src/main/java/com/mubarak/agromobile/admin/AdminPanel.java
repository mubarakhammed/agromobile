package com.mubarak.agromobile.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.mubarak.agromobile.MapsActivity;
import com.mubarak.agromobile.Profile;
import com.mubarak.agromobile.R;
import com.mubarak.agromobile.SpecificNote;
import com.mubarak.agromobile.seperate.New;

public class AdminPanel extends AppCompatActivity {

    private LinearLayout admincashew, adminaddnote, adminviewUsers, adminLocate, adminViewQuestions, adminUpdateprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.deep_green));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.deep_green));
        }

        admincashew = (LinearLayout) findViewById(R.id.admin_cashew_note);
        adminaddnote = (LinearLayout) findViewById(R.id.adminaddnote);
        adminviewUsers = (LinearLayout) findViewById(R.id.adminviewusers);
        adminLocate = (LinearLayout) findViewById(R.id.adminlocatevillage);
        adminViewQuestions = (LinearLayout) findViewById(R.id.admingetquestions);
        adminUpdateprofile = (LinearLayout) findViewById(R.id.adminupdateprofile);


        admincashew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go   = new Intent(AdminPanel.this, New.class);
                startActivity(go);
            }
        });
        adminaddnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go   = new Intent(AdminPanel.this, AdminAddNote.class);
                startActivity(go);
            }
        });
        adminviewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go   = new Intent(AdminPanel.this, Users.class);
                startActivity(go);
            }
        });
        adminLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go   = new Intent(AdminPanel.this, MapsActivity.class);
                startActivity(go);
            }
        });
//        adminViewQuestions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent go   = new Intent(AdminPanel.this, New.class);
//                startActivity(go);
//            }
//        });
        adminUpdateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go   = new Intent(AdminPanel.this, Profile.class);
                startActivity(go);
            }
        });


    }

}