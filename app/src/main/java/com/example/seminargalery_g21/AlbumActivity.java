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
        //lvAlbums.setAdapter(adapter);

//        lvAlbums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
    }

//    private void mapping() {
//        lvAlbums = findViewById(R.id.lv_albums);
//        albumList = new ArrayList<>();
//
//        for(int i = 0; i < albumList.size(); i++) {
//            albumList.add(new Album(albumList.get(i).getAlbumName(), albumList.get(i).getAlbumImage(), ), R.drawable.ic_all_photos_folder));
//        }
//
////        albumList.add(new Album("Photos", R.drawable.ic_all_photos_folder));
////        albumList.add(new Album("Favorites", R.drawable.ic_favorite_folder));
////        albumList.add(new Album("Shared", R.drawable.ic_share_folder));
////        albumList.add(new Album("Recycle bin", R.drawable.ic_bin_folder));
//    }
}