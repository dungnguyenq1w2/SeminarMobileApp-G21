package com.example.seminargalery_g21;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class FullScreenImageActivity extends AppCompatActivity {

    ImageView imgView;
    ImageButton btnInfo;
    private ImageButton btnReact;
    private boolean isColorReact = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_full_screen_image);

        imgView = (ImageView) findViewById(R.id.image_solo);

        Intent intent = getIntent();

        String path = intent.getExtras().getString("path");

//        ImageAdapter imgAdapter = new ImageAdapter(this);

        Glide.with(this).load(path).into(imgView);

        btnInfo = (ImageButton) findViewById(R.id.btn_info);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(FullScreenImageActivity.this, ImageInfoActivity.class);
                intent.putExtra("path", path);
                startActivity(intent);
            }
        }) ;

        btnReact = (ImageButton) findViewById(R.id.btn_react);
        btnReact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!isColorReact) {
                    btnReact.setImageResource(R.drawable.react_red);
                    isColorReact = true;
                }
                else {
                    btnReact.setImageResource(R.drawable.react_white);
                    isColorReact = false;
                }
            }
        });

    }
}