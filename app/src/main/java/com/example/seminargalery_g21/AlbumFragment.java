package com.example.seminargalery_g21;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.seminargalery_g21.helper.Album;
import com.example.seminargalery_g21.helper.AlbumAdapter;
import com.example.seminargalery_g21.helper.AlbumGallery;
import com.example.seminargalery_g21.helper.ImageAdapter;
import com.example.seminargalery_g21.helper.ImagesGallery;

import java.util.ArrayList;

public class AlbumFragment extends Fragment {

    RecyclerView recyclerView;
    ListView lvAlbums;
    ArrayList<Album> albumList;
    AlbumAdapter adapter;
    TextView gallery;
    TextView album;

    public AlbumFragment() { }

    public static AlbumFragment newInstance() {
        AlbumFragment fragment = new AlbumFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAlbum();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_album, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_gallery_albums);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        albumList = AlbumGallery.listOfAlbum(getActivity());
        //adapter = new AlbumAdapter(this, R.layout.albums_item, albumList);
        adapter = new AlbumAdapter(getActivity(), albumList, new AlbumAdapter.AlbumListener() {
            @Override
            public void onAlbumClick(Album album) {
                // Do something with photo
                Intent intent = new Intent(getActivity(), ImageActivity.class);

                intent.putExtra("albumName", album.getAlbumName());
                startActivity(intent);
//                Toast.makeText(MainActivity.this, ""+path, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    private void loadAlbum() {
        albumList = AlbumGallery.listOfAlbum(getActivity());
        //adapter = new AlbumAdapter(this, R.layout.albums_item, albumList);
        adapter = new AlbumAdapter(getActivity(), albumList, new AlbumAdapter.AlbumListener() {
            @Override
            public void onAlbumClick(Album album) {
                // Do something with photo
                Intent intent = new Intent(getActivity(), ImageActivity.class);

                intent.putExtra("albumName", album.getAlbumName());
                startActivity(intent);
//                Toast.makeText(MainActivity.this, ""+path, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}