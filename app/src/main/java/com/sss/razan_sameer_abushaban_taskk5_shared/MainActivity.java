package com.sss.razan_sameer_abushaban_taskk5_shared;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button but_save,but_gall;
    ImageView profile;
    EditText ed_name, ed_email, ed_username, ed_pass,ed_phone, ed_address;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    Uri uri;
    public static final String FILE_NAME = "photos";
    public static final int WRITE_EX_REQ_CODE = 1;
    static final int insert_photo =101;
    public final String username_key = "username";
    public final String name_key = "name";
    public final String pass_key = "password";
    public final String email_key = "email";
    public final String phone_key = "phone";
    public final String address_key = "address";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profile = findViewById(R.id.img_profile);
        ed_name= findViewById(R.id.ed_name);
        ed_email = findViewById(R.id.ed_email);
        ed_username = findViewById(R.id.ed_username);
        ed_pass = findViewById(R.id.ed_pass);
        ed_phone = findViewById(R.id.ed_phone);
        ed_address = findViewById(R.id.ed_address);
        but_save = findViewById(R.id.but_save);
        but_gall = findViewById(R.id.but_gall);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        edit = sp.edit();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            String [] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this,permissions,WRITE_EX_REQ_CODE);

        }
        else {

        }

        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ed_name.getText().toString();
                String username = ed_username.getText().toString();
                String email = ed_email.getText().toString();
                String pass = ed_pass.getText().toString();
                String address = ed_address.getText().toString();
                String phone = ed_phone.getText().toString();

                edit.putString(username_key,username);
                edit.putString(name_key,name);
                edit.putString(email_key,email);
                edit.putString(address_key,address);
                edit.putString(phone_key,phone);
                edit.putString(pass_key,pass);
                edit.apply();

             if (isExternalStorageWritable()){
                 File ex_st = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                 File f = new File(ex_st,FILE_NAME);
                 try {
                     f.createNewFile();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }

                Toast.makeText(getBaseContext(), "THE INFORMATION IS SAVED ", Toast.LENGTH_LONG).show();

            }
        });
        but_gall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent but_gallary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(but_gallary,insert_photo);

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String name = ed_name.getText().toString();
        String username = ed_username.getText().toString();
        String email = ed_email.getText().toString();
        String pass = ed_pass.getText().toString();
        String address = ed_address.getText().toString();
        String phone = ed_phone.getText().toString();
        edit.putString(username_key,username);
        edit.putString(name_key,name);
        edit.putString(email_key,email);
        edit.putString(address_key,address);
        edit.putString(phone_key,phone);
        edit.putString(pass_key,pass);
        edit.apply();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == insert_photo && resultCode == RESULT_OK ) {

            profile.setImageBitmap((Bitmap) data.getExtras().get("data"));
            uri = data.getData();
            profile.setImageURI(uri);

        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getBaseContext(), "THE USER CANCELLED", Toast.LENGTH_LONG).show();
        }
}
        public boolean isExternalStorageWritable(){

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
}
    public boolean isExternalStorageReadable(){

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED) || state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
            return true;
        }
        return false;

}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case WRITE_EX_REQ_CODE:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                  Toast.makeText(this, "DONE",Toast.LENGTH_LONG).show();
                }
                return;
        }
    }
}