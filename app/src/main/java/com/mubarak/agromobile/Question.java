package com.mubarak.agromobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Question extends AppCompatActivity {

    private ImageView back;
    private EditText message;
    private Button send;
    private TextView call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.deep_green));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.deep_green));
        }

        back =  (ImageView) findViewById(R.id.back_from_feedback);
        message = (EditText) findViewById(R.id.feedback_message);
        send = (Button) findViewById(R.id.post_feedback);
        call = (TextView) findViewById(R.id.call_customer);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}