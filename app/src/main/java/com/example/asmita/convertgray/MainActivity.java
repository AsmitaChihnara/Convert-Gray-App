package com.example.asmita.convertgray;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int REQUEST_CAPTURE = 1;
    Button btn;
    ImageButton imgbtn;
    Bitmap im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(this);

        imgbtn = (ImageButton)findViewById(R.id.imageButton);
        imgbtn.setX(75);
        imgbtn.setY(200);
        imgbtn.setOnClickListener(this);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA},1);
            }
        }
        if(!hasCamera()){
            btn.setEnabled(false);
        }
    }

    public boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, REQUEST_CAPTURE);
        }
        else {
            if(im == null) {
                Toast t = Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_LONG);
                t.show();
            }else {
                Bitmap bmp = send();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Intent in1 = new Intent(this, ShowActivity.class);
                in1.putExtra("image", byteArray);
                startActivity(in1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK){
           Bundle extras = data.getExtras();
           Bitmap photo = (Bitmap)extras.get("data");
           imgbtn.setImageBitmap(photo);
           im = photo;
       }
    }

    public Bitmap send(){
        return im;
    }
}
