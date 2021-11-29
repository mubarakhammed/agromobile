package com.mubarak.agromobile.seperate;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mubarak.agromobile.R;
import com.mubarak.agromobile.authentication.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class New extends AppCompatActivity {

    private String id = Login.id;
    public String FETCHURL;
    List<Repo> recipes;
    private RecyclerView recyclerview;
    private ArrayList<Repo> arrayList;
    private CustomRecyclerview adapter;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        pb = findViewById(R.id.pb);
        pb.setVisibility(View.GONE);

         FETCHURL = "https://ebco.com.ng/agromobile-working-api/note_display.php";

        recyclerview = findViewById(R.id.recyclerview);
        arrayList = new ArrayList<>();
        adapter = new CustomRecyclerview(this, arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setAdapter(adapter);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting() && arrayList != null) {
            fetchfromServer();
        } else {
            fetchfromRoom();
        }


    }




    private void fetchfromRoom() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                List<Recipe> recipeList = DatabaseClient.getInstance(New.this).getAppDatabase().recipeDao().getAll();
                arrayList.clear();
                for (Recipe recipe: recipeList) {
                    Repo repo = new Repo(
                            recipe.getNote_id(),
                            recipe.getTitle(),
                            recipe.getDescription(),
                            recipe.getYoutube_link(),
                            recipe.getPdf_link());
                    arrayList.add(repo);
                }
                // refreshing recycler view
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        thread.start();


    }

    private void fetchfromServer() {
        pb.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(FETCHURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            pb.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Couldn't fetch the menu! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("note");
                            Log.d("Hello", "onResponse: "+ array);
                            recipes = new Gson().fromJson(array.toString(), new TypeToken<List<Repo>>() {
                            }.getType());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // adding data to cart list
                        arrayList.clear();
                        arrayList.addAll(recipes);


                        // refreshing recycler view
                        adapter.notifyDataSetChanged();

                        pb.setVisibility(View.GONE);

                        saveTask();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                pb.setVisibility(View.GONE);
                Log.e("TAG", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        request.setShouldCache(false);

        requestQueue.add(request);
    }


    private void saveTask() {


        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task

                for (int i = 0; i < recipes.size(); i++) {
                    Recipe recipe= new  Recipe();
                    recipe.setTitle(recipes.get(i).getTitle());
                    recipe.setDescription(recipes.get(i).getDescription());
                    recipe.setYoutube_link(recipes.get(i).getYoutube_link());
                    recipe.setPdf_link(recipes.get(i).getPdf_link());
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().recipeDao().insert(recipe);
                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

}