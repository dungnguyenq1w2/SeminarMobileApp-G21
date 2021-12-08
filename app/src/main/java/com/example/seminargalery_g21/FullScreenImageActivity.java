package com.example.seminargalery_g21;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.seminargalery_g21.database.AlbumDataSource;
import com.example.seminargalery_g21.helper.Photo;

import java.util.List;

public class FullScreenImageActivity extends AppCompatActivity {

    ImageView imgView;
    ImageButton btnInfo;
    private ImageButton btnReact;
    private ImageButton btnDelete;
    private Context context;
    private AlbumDataSource albumDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_full_screen_image);
        context = FullScreenImageActivity.this;
        albumDataSource = new AlbumDataSource(context);

        imgView = (ImageView) findViewById(R.id.image_solo);

        Intent intent = getIntent();

        String path = intent.getExtras().getString("path");

//        ImageAdapter imgAdapter = new ImageAdapter(this);

        Glide.with(this).load(path).into(imgView);
        btnReact = (ImageButton) findViewById(R.id.btn_react);
        btnInfo = (ImageButton) findViewById(R.id.btn_info);
        btnDelete = (ImageButton) findViewById(R.id.btn_delete);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(FullScreenImageActivity.this, ImageInfoActivity.class);
                intent.putExtra("path", path);
                startActivity(intent);
            }
        }) ;

        if(check(path))
            btnReact.setImageResource(R.drawable.react_red);
        else
            btnReact.setImageResource(R.drawable.react_white);
        btnReact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(check(path)){
                    btnReact.setImageResource(R.drawable.react_white);
                    albumDataSource.updateReact(path, 0);
                    Intent intent = new Intent(FullScreenImageActivity.this, AlbumActivity.class);
                    startActivity(intent);
                    //finish();
                }
                else {
                    btnReact.setImageResource(R.drawable.react_red);
                    albumDataSource.updateReact(path, 1);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                    albumDataSource.updateBin(path, 1);
                    Intent intent = new Intent(FullScreenImageActivity.this, AlbumActivity.class);
                    startActivity(intent);
                    //finish();

            }
        });
    }
    //add
    public Boolean check(String path){
        List<Photo> photos = albumDataSource.getPhotos();
        for(int i = 0; i < photos.size(); i++){
            if(photos.get(i).getPath().equals(path) && photos.get(i).getReact() == 1)
                return true;
        }
        return false;
    }
}