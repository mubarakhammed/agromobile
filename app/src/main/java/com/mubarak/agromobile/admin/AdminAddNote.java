package com.mubarak.agromobile.admin;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.mubarak.agromobile.FilePath;
import com.mubarak.agromobile.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class AdminAddNote extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_FILE_REQUEST = 1;
    private static final String TAG = AdminAddNote.class.getSimpleName();
    private String selectedFilePath;

    private EditText description, youtubelink, title;
    Button ivAttachment;
    Button bUpload, view;
    TextView tvFileName;
    ProgressDialog dialog;
    File selectedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ivAttachment = findViewById(R.id.choose_pdf);
        bUpload = findViewById(R.id.add_btn);
        tvFileName = findViewById(R.id.textTitle);

        youtubelink = (EditText) findViewById(R.id.note_youtubelink);
        description = (EditText) findViewById(R.id.note_description);
        title = (EditText) findViewById(R.id.note_title);

        ivAttachment.setOnClickListener(this);
        bUpload.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if (v == ivAttachment) {
            isReadStoragePermissionGranted();
        }


        if (v == bUpload) {

            //on upload button Click
            if (selectedFilePath != null) {
                dialog = ProgressDialog.show(AdminAddNote.this, "", "Uploading File...", true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            //creating new thread to handle Http Operations
                           // uploadFile(selectedFilePath);
                            send();
                        } catch (OutOfMemoryError e) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AdminAddNote.this, "Insufficient Memory!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.dismiss();
                        }

                    }
                }).start();
            } else {
                Toast.makeText(AdminAddNote.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void showFileChooser() {


        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data == null) {
                    //no data present
                    return;
                }

//                PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
//                wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, TAG);
//                wakeLock.acquire();

                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePath.getPath(this, selectedFileUri);
                Log.i(TAG, "Selected File Path:" + selectedFilePath);

                if (selectedFilePath != null && !selectedFilePath.equals("")) {
                    tvFileName.setText(selectedFilePath);
                    selectedFile = new File(selectedFilePath);
                } else {
                    Toast.makeText(this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void send() {

        AndroidNetworking.upload("https://ebco.com.ng/agromobile-working-api/note_create.php")
                .addMultipartFile("pdf_upload", selectedFile)
                .addMultipartParameter("create_note", "create_note")
                .addMultipartParameter("title", title.getText().toString())
                .addMultipartParameter("description",description.getText().toString())
                .addMultipartParameter("youtube_link", youtubelink.getText().toString())


                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        showDialog();
                        // do anything with progress
                        //  tvProgress.setText((bytesUploaded / totalBytes) * 100 + " %");
                    }
                })
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        Log.d(TAG, "onResponse: " + response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String status = jObj.getString("status");
                            String message = jObj.getString("message");
                            switch (status) {
                                case "200":
                                    Log.d(TAG, "onResponse: " + "Successfull registeration");
                                    Toast.makeText(getApplicationContext(), "Note Added successfully!", Toast.LENGTH_LONG).show();
                                    break;

                                default:
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        if (error.getErrorCode() != 0) {
                            hideDialog();
                            Log.d(TAG, "onError errorCode : " + error.getErrorCode());
                            Log.d(TAG, "onError errorBody : " + error.getErrorBody());
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());

                        } else {
                            hideDialog();
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
                        }
                    }

                });
    }



    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted!");

                showFileChooser();
                return true;
            } else {

                Log.v(TAG,"Permission is revoked!");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted!");
            showFileChooser();
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Log.d(TAG, "External storage2");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                    showFileChooser();
                }else{
                    dialog.dismiss();
                }
                break;

        }
    }

    private void showDialog () {
        if (!dialog.isShowing())
            dialog.show();
    }
    private void hideDialog () {
        if (dialog.isShowing())
            dialog.dismiss();
    }


}