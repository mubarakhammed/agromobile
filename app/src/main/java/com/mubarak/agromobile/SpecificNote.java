package com.mubarak.agromobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpecificNote extends AppCompatActivity {

    private LottieAnimationView animationView, empty;
    private static final String TAG = "Notes";
    private ImageView back;

    RecyclerView recyclerView;
    RecyclerView.Adapter[] adapter = new RecyclerView.Adapter[1];
    List<NoteList> listitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_note);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.deep_green));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.deep_green));
        }

       // empty = (LottieAnimationView)findViewById(R.id.empty_plan);

        back = (ImageView) findViewById(R.id.back_from_notes);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        animationView = (LottieAnimationView)findViewById(R.id.animationView2);
        recyclerView = (RecyclerView)findViewById(R.id.note_recycler);
        recyclerView.setHasFixedSize(false);
        listitems = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter[0] = new NoteAdapter(listitems, R.layout.notelist, this);
        recyclerView.setAdapter(adapter[0]);

        getNotes();


    }

    private void getNotes() {
        animationView.setVisibility(View.VISIBLE);
        AndroidNetworking.post("https://ebco.com.ng/agromobile-working-api/note_display.php")

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                         animationView.setVisibility(View.GONE);
                        Log.d(TAG, "onResponse: " + response);
                        // do anything with response
                        try {
                            JSONObject jObj = new JSONObject(response);
                            int status = jObj.getInt("status");
                            String msg = jObj.getString("message");
                            JSONArray array = jObj.getJSONArray("note");
                            Log.d(TAG, "onResponse: " + array);

                            if (array.length()<1){
                                Log.d(TAG, "onResponse: " + "I am empty");

                               // empty.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                NoteList item = new NoteList(
                                        o.getString("title"),
                                        o.getString("description"),
                                        o.getString("youtube_link"),
                                        o.getString("pdf_link")

                                );
                                listitems.add(item);
                            }

                            adapter[0] = new NoteAdapter(listitems, R.layout.notelist, getApplicationContext());
                            recyclerView.setAdapter(adapter[0]);


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        animationView.setVisibility(View.GONE);
                       // empty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                        Log.d(TAG, "onError:  " + error);
                        if (error.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.d(TAG, "onError errorCode : " + error.getErrorCode());
                            Log.d(TAG, "onError errorBody : " + error.getErrorBody());
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                            // get parsed error object (If ApiError is your class)

                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                        }
                    }
                });
    }
}