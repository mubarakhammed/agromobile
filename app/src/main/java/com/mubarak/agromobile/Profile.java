package com.mubarak.agromobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.material.snackbar.Snackbar;
import com.mubarak.agromobile.admin.UserAdapter;
import com.mubarak.agromobile.admin.UserList;
import com.mubarak.agromobile.authentication.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity {
    private EditText username, firstname, lastname, email, password, institution;
    private Button update;
    private ImageView back;
    private String id = Login.id;
    private ProgressDialog progressDialog;
    public static  final String TAG = "Update";

    String usernamedata, firstnamedata, lastnamedata, emaildata, institutiondata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.deep_green));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.deep_green));
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Updating...");

        back = (ImageView) findViewById(R.id.back_from_update);
        update  = (Button) findViewById(R.id.update_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        username = (EditText) findViewById(R.id.username);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.email);
        institution = (EditText) findViewById(R.id.institution);
        password = (EditText) findViewById(R.id.password);
        fetchdata();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() || firstname.getText().toString().isEmpty() || lastname.getText().toString().isEmpty() || email.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Empty fields are not allowed", Toast.LENGTH_LONG).show();
                }else {
                    Update();
                }
            }
        });
    }


    private void  Update(){

        showDialog();
        AndroidNetworking.post("https://ebco.com.ng//agromobile-working-api/update-authetication.php?username="+username.getText().toString()+"&first_name="+firstname.getText().toString()+
                "&last_name="+lastname.getText().toString()+"&email="+email.getText().toString()+"&password="+password.getText().toString()+"&institution="+institution.getText().toString()+"&userid="+id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        // do anything with response
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String status = jObj.getString("status");
                            switch (status){

                                case "200":
                                    hideDialog();
                                    Toast.makeText(getApplicationContext(), "Successful Update!", Toast.LENGTH_LONG).show();
                                    username.getText().clear();
                                    firstname.getText().clear();
                                    lastname.getText().clear();
                                    email.getText().clear();
                                    password.getText().clear();
                                    break;
                                default:
                                    hideDialog();
                                    Log.d(TAG, "onResponse: " + "an error occurred");
                                    break;
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        hideDialog();
                        View contextView = findViewById(android.R.id.content);
                        Snackbar.make(contextView, "We encountered an error, check entries and try later", Snackbar.LENGTH_SHORT)
                                .show();

                        if (error.getErrorCode() != 0) {

                            Log.d(TAG, "onError errorCode : " + error.getErrorCode());
                            Log.d(TAG, "onError errorBody : " + error.getErrorBody());
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());

                        } else {

                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                        }
                    }


                });


    }

    private void fetchdata(){
        showDialog();
        AndroidNetworking.post("https://ebco.com.ng/agromobile-working-api/user_detail.php?id="+id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                      hideDialog();
                        Log.d(TAG, "onResponse: " + response);
                        // do anything with response
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Log.d(TAG, "onResponse: " + jObj);
                            usernamedata = jObj.getString("username");
                            firstnamedata = jObj.getString("first_name");
                            lastnamedata = jObj.getString("last_name");
                            emaildata = jObj.getString("email");
                            institutiondata = jObj.getString("institution");

                            username.setText(usernamedata);
                            firstname.setText(firstnamedata);
                            lastname.setText(lastnamedata);
                            email.setText(emaildata);
                            institution.setText(institutiondata);





                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        hideDialog();
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

    private void showDialog(){
        if (!progressDialog.isShowing())
            progressDialog.show();

    }

    private void hideDialog(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();

    }

}
