package com.example.seminargalery_g21;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seminargalery_g21.helper.Album;
import com.example.seminargalery_g21.helper.AlbumAdapter;
import com.example.seminargalery_g21.helper.AlbumGallery;
import com.example.seminargalery_g21.helper.ImageAdapter;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ListView lvAlbums;
    ArrayList<Album> albumList;
    AlbumAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.album_list);

        recyclerView = findViewById(R.id.recyclerview_gallery_albums);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        albumList = AlbumGallery.listOfAlbum(AlbumActivity.this);
        //adapter = new AlbumAdapter(this, R.layout.albums_item, albumList);
        adapter = new AlbumAdapter(this, albumList, new AlbumAdapter.AlbumListener() {
            @Override
            public void onAlbumClick(Album album) {
                // Do something with photo
                Intent intent = new Intent(getApplicationContext(), ImageActivity.class);

                intent.putExtra("albumName", album.getAlbumName());
                //intent.putExtra("album", album.getAlbumName());
                startActivity(intent);
//                Toast.makeText(MainActivity.this, ""+path, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
