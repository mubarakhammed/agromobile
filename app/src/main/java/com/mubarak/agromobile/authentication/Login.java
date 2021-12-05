package com.mubarak.agromobile.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mubarak.agromobile.MainActivity;
import com.mubarak.agromobile.R;
import com.mubarak.agromobile.admin.AdminPanel;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    TextView registerlink;
    View view;
    EditText username, password;
    public static final String TAG = "Login";
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    public static String id;
    ProgressButton progressButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.deep_green));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.deep_green));
        }

        username = (EditText) findViewById(R.id.username_input);
        password = (EditText) findViewById(R.id.password_input);

//        registerlink = (TextView) findViewById(R.id.registerLink);
//        registerlink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent go = new Intent(Login.this, Registeration.class);
//                startActivity(go);
//            }
//        });
        mPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        mEditor = mPreferences.edit();



        view = findViewById(R.id.loginbtn);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {

                } else {
                    progressButton = new ProgressButton(getApplicationContext(), view);
                    String phone = username.getText().toString();
                    mEditor.putString(getString(R.string.stored_email), phone);
                    String spassword = password.getText().toString();
                    mEditor.putString(getString(R.string.stored_password), spassword);
                    mEditor.commit();

                    loginUser();

                }
            }
        });

        Preference();


    }

    private void loginUser() {
        progressButton.buttonActivated();
        AndroidNetworking.post("https://ebco.com.ng/agromobile-working-api/authetication-login.php?username_email="+username.getText().toString()+"&password="+password.getText().toString())
                .addBodyParameter("username_email", username.getText().toString())
                .addBodyParameter("password", password.getText().toString())
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
                            String user_id = jObj.getString("user_id");
                            id = user_id;
                            String  type = jObj.getString("is_admin");

                            switch (status) {

                                case "200":
                                    /*success*/
                                    progressButton.buttonFinished();
                                    switch (type){
                                        case "1":
                                            Intent intent = new Intent(getApplicationContext(), AdminPanel.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            break;
                                        case "2":
                                            Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                                            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent2);
                                            break;
                                        default:
                                            progressButton.buttonError();
                                            break;

                                    }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        progressButton.buttonError();
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

    private void bottomError() {
        View view = getLayoutInflater().inflate(R.layout.bottomloginerror, null);
        BottomSheetDialog dialog = new BottomSheetDialog(Login.this);
        dialog.setContentView(view);
        dialog.show();
    }



    private void Preference() {
        String usermail = mPreferences.getString(getString(R.string.stored_email), "");
        String userpassword = mPreferences.getString(getString(R.string.stored_password), "");
        username.setText(usermail);

    }

}