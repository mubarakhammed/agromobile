package com.mubarak.agromobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private LinearLayout NotecardView, Mapcardview, Questioncardview, UpdateCard;
    private Button about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.deep_green));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.deep_green));
        }


        NotecardView = (LinearLayout) findViewById(R.id.note);
        NotecardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go =  new Intent(MainActivity.this, SpecificNote.class);
                startActivity(go);

            }
        });
        Mapcardview = (LinearLayout) findViewById(R.id.map);
        Mapcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go =  new Intent(MainActivity.this, MapsActivity.class);
                startActivity(go);

            }
        });
        Questioncardview = (LinearLayout) findViewById(R.id.question);
        Questioncardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go =  new Intent(MainActivity.this, Question.class);
                startActivity(go);

            }
        });
        UpdateCard = (LinearLayout) findViewById(R.id.update);
        UpdateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go =  new Intent(MainActivity.this, Profile.class);
                startActivity(go);

            }
        });

        about = (Button) findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent about = new Intent(MainActivity.this, About.class);
                startActivity(about);
            }
        });
    }
}