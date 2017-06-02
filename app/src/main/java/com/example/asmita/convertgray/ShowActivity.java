package com.example.asmita.convertgray;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;

public class ShowActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    Switch sth;
    ImageView imgV;
    Bitmap bmp;
    private Mat mRgba;
    private Mat mGray;
    static {
        System.loadLibrary("MyLibs");
        System.loadLibrary("opencv_java3");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        sth = (Switch)findViewById(R.id.switch1);
        sth.setOnCheckedChangeListener(this);

        imgV = (ImageView)findViewById(R.id.imageView);
        imgV.setX(50);
        imgV.setY(100);
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imgV.setImageBitmap(bmp);
    }

    public Bitmap send(){
        return bmp;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(isChecked) {
            Toast t = Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_LONG);
            t.show();

            Bitmap bm = send();
            mRgba = new Mat (bm.getWidth(), bm.getHeight(), CvType.CV_8UC1);
            Utils.bitmapToMat(bm, mRgba);
            Convertgray.convertgray(mRgba.getNativeObjAddr(),mGray.getNativeObjAddr());
            //Imgproc.cvtColor(tmp, tmp, Imgproc.COLOR_RGB2GRAY);
            if(mGray!=null) {
                Utils.matToBitmap(mGray, bmp);
                imgV.setImageBitmap(bmp);
            }


            /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            if (bm != null) {
                int x = bm.getWidth();
                int y = bm.getHeight();
                int[] intArray = new int[x * y];
                bm.getPixels(intArray, 0, x, 0, 0, x, y);
                Toast t1 = Toast.makeText(getApplicationContext(), "x:"+x+", y:"+y+", int[]:"+intArray, Toast.LENGTH_LONG);
                t1.show();

                byte[] ba = Convertgray.convertgray(bm.getWidth(), bm.getHeight(), intArray);
                //Toast t3 = Toast.makeText(getApplicationContext(), "byte[]:"+ba, Toast.LENGTH_LONG);
               // t3.show();
                Bitmap bmp1 = BitmapFactory.decodeByteArray(ba, 0, ba.length);
                imgV.setImageBitmap(bmp1);
            } else {
                Toast t2 = Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_LONG);
                t2.show();
            }*/
        }
    }

}
