package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class FiltroThree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_three);

        ImageView imageView = findViewById(R.id.image_view);

        File file = new File(getExternalFilesDir(null), "FiltroThree/Frame0.jpg");
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        imageView.setImageBitmap(bitmap);
    }
}