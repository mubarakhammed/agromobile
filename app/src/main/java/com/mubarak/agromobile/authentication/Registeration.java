package com.mubarak.agromobile.authentication;

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
import com.mubarak.agromobile.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Registeration extends AppCompatActivity {

    private ImageView back;
    private EditText firstname, lastname, username, email, password, institution, confirm_password;
    private Button register;
    public static final String TAG = "Registrateion";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.deep_green));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.deep_green));
        }
        progressDialog  = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registering... Please wait");

        back = (ImageView) findViewById(R.id.back_from_register);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firstname = (EditText) findViewById(R.id.firstname_input);
        lastname = (EditText) findViewById(R.id.lastname_input);
        username = (EditText) findViewById(R.id.register_username_input);
        email = (EditText) findViewById(R.id.email_input);
        password = (EditText) findViewById(R.id.register_password_input);
        confirm_password = (EditText) findViewById(R.id.register_confirm_password_input);
        institution = (EditText) findViewById(R.id.institution_input);

        register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstname.getText().toString().isEmpty() || lastname.getText().toString().isEmpty() || username.getText().toString().isEmpty() ||
                email.getText().toString().isEmpty()){

                }else if (!password.getText().toString().equals(confirm_password.getText().toString())){
                    password.setError("Passwords not matching");
                }else {
                    registerUser();
                }
            }
        });

    }

    private void registerUser() {
        showDialog();
        AndroidNetworking.post("https://ebco.com.ng//agromobile-working-api/create-authetication.php?username="+username.getText().toString()+"&first_name="+firstname.getText().toString()+
                "&last_name="+lastname.getText().toString()+"&email="+email.getText().toString()+"&password="+password.getText().toString()+"&institution="+institution.getText().toString())
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
                          //  String id = jObj.getString("unique_id");
                            switch (status){
                                case "200":
                                    hideDialog();
                                    Toast.makeText(getApplicationContext(), "Successful registeration!", Toast.LENGTH_LONG).show();
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

    private void showDialog(){
        if (!progressDialog.isShowing())
            progressDialog.show();

    }

    private void hideDialog(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();

    }

}
