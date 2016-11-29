package com.org.iii.shine23;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private ImageView img;
    private File sdroot, photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        img = (ImageView)findViewById(R.id.img);
        sdroot = Environment.getExternalStorageDirectory();

        //取得權限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]
                        {Manifest.permission.CAMERA,
                         Manifest.permission.READ_EXTERNAL_STORAGE,
                         Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        123);
        }


    }
    //使用別人的相機
    public void test1(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    //存入記憶卡
    public void test2(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = new File(sdroot,  "brad.jpg");
        Uri photoUri = Uri.fromFile(photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, 2);
    }

    //
    public void test3(View v){
        Intent it = new Intent(this,CameraActivity.class);
        startActivityForResult(it, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            take1(data);
        }else if (requestCode == 2 && resultCode == RESULT_OK){
            take2(data);
        }
    }

    //縮圖 資料放在INTENT裡面
    private void take1(Intent it){
        Bitmap bmp = (Bitmap)it.getExtras().get("data");
        img.setImageBitmap(bmp);
    }
    //原圖
    private void take2(Intent it){
        Bitmap bmp = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
        img.setImageBitmap(bmp);
    }



}