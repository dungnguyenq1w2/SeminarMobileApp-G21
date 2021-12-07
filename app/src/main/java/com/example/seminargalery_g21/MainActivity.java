package com.example.seminargalery_g21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seminargalery_g21.helper.Album;
import com.example.seminargalery_g21.helper.AlbumAdapter;
import com.example.seminargalery_g21.helper.AlbumGallery;
import com.example.seminargalery_g21.helper.ImageAdapter;
import com.example.seminargalery_g21.helper.ImagesGallery;
import com.example.seminargalery_g21.helper.StateManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageAdapter imageAdapter;
    List<String> images;
    TextView album;
    String albumName = "";

//    RecyclerView recyclerView;
//    ListView lvAlbums;
//    ArrayList<Album> albumList;
//    AlbumAdapter adapter;
//    List<String> images;

    private static final int MY_READ_PERMISSION_CODE = 101;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        // Lấy context để tải theme
        context = MainActivity.this;
        loadTheme();

        //gallery_number = findViewById(R.id.gallery_number);
        recyclerView = findViewById(R.id.recyclerview_gallery_images);

        // Check form permission
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_READ_PERMISSION_CODE);
        } else {
            loadImages();
//            recyclerView = findViewById(R.id.recyclerview_gallery_albums);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//
//        albumList = AlbumGallery.listOfAlbum(MainActivity.this);
//        //adapter = new AlbumAdapter(this, R.layout.albums_item, albumList);
//        adapter = new AlbumAdapter(this, albumList, new AlbumAdapter.AlbumListener() {
//            @Override
//            public void onAlbumClick(Album album) {
//                // Do something with photo
//                Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
//
//                intent.putExtra("albumName", album.getAlbumName());
//                //intent.putExtra("album", album.getAlbumName());
//                startActivity(intent);
////                Toast.makeText(MainActivity.this, ""+path, Toast.LENGTH_SHORT).show();
//            }
//        });
//        recyclerView.setAdapter(adapter);
//        }
        }


//
//        Intent intent = new Intent(MainActivity.this,SharingActivity.class);
//        startActivity(intent);

//        ImageButton ic_back = (ImageButton) findViewById(R.id.ic_back);
//        ic_back.setOnClickListener(new View.OnClickListener() {
//                                       @Override
//                                       public void onClick(View view) {
//                                           Intent intent = new Intent(getApplicationContext(), AlbumActivity.class);
//                                           startActivity(intent);
//                                       }
//                                   }
//        );

        album = (TextView) findViewById(R.id.album);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlbumActivity.class);
                startActivity(intent);
            }
        });

        ImageButton ic_image = (ImageButton) findViewById(R.id.ic_image);
        ic_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SharingActivity.class);
                startActivity(intent);
            }
        });

        ImageButton ic_settings = (ImageButton) findViewById(R.id.ic_settings);
        ic_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
//        recyclerView = findViewById(R.id.recyclerview_gallery_albums);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//
//        albumList = AlbumGallery.listOfAlbum(MainActivity.this);
//        //adapter = new AlbumAdapter(this, R.layout.albums_item, albumList);
//        adapter = new AlbumAdapter(this, albumList, new AlbumAdapter.AlbumListener() {
//            @Override
//            public void onAlbumClick(Album album) {
//                // Do something with photo
//                Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
//
//                intent.putExtra("albumName", album.getAlbumName());
//                //intent.putExtra("album", album.getAlbumName());
//                startActivity(intent);
////                Toast.makeText(MainActivity.this, ""+path, Toast.LENGTH_SHORT).show();
//            }
//        });
//        recyclerView.setAdapter(adapter);
//    }

    // Tải theme và đặt trạng thái này cho ứng dụng
    private void loadTheme() {
        int themeMode = StateManager.getThemeMode(context);
        if (themeMode == AppCompatDelegate.MODE_NIGHT_NO)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    private void loadImages() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        images = ImagesGallery.listOfImages(this, "");
        imageAdapter = new ImageAdapter(this, images, new ImageAdapter.PhotoListener() {
            @Override
            public void onPhotoClick(String path) {
                // Do something with photo
                Intent intent = new Intent(getApplicationContext(), FullScreenImageActivity.class);
                intent.putExtra("path", path);
                startActivity(intent);
//                Toast.makeText(MainActivity.this, ""+path, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(imageAdapter);

        //gallery_number.setText("Photos ("+images.size()+")");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_READ_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read external storage permission granted", Toast.LENGTH_SHORT).show();
                loadImages();
            } else {
                Toast.makeText(this, "Read external storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}