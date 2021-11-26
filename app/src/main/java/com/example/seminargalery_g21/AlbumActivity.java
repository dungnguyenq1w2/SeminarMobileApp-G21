package com.example.seminargalery_g21;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seminargalery_g21.model.Album;
import com.example.seminargalery_g21.helper.AlbumAdapter;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {
    ListView lvAlbums;
    ArrayList<Album> albumList;
    AlbumAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.album_list);

        mapping();
        adapter = new AlbumAdapter(this, R.layout.albums_item, albumList);
        lvAlbums.setAdapter(adapter);

        lvAlbums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mapping() {
        lvAlbums = findViewById(R.id.lv_albums);
        albumList = new ArrayList<>();

        albumList.add(new Album("Photos", R.drawable.ic_all_photos_folder));
        albumList.add(new Album("Favorites", R.drawable.ic_favorite_folder));
        albumList.add(new Album("Shared", R.drawable.ic_share_folder));
        albumList.add(new Album("Recycle bin", R.drawable.ic_bin_folder));
    }
}