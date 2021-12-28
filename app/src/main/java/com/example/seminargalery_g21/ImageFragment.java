package com.example.seminargalery_g21;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.seminargalery_g21.database.AlbumDataSource;
import com.example.seminargalery_g21.helper.ImageAdapter;
import com.example.seminargalery_g21.helper.ImagesGallery;
import com.example.seminargalery_g21.helper.Photo;

import java.util.ArrayList;
import java.util.List;

public class ImageFragment extends Fragment {

    RecyclerView recyclerView;
    ImageAdapter imageAdapter;
    List<String> images;
    List<String> loadImagesPhone;
    AlbumDataSource albumDataSource;

    public ImageFragment() { }

    public static ImageFragment newInstance() {
        ImageFragment fragment = new ImageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadImage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_image, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_gallery_images);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        loadImage();
        return rootView;
    }

    private void loadImage() {
        loadImagesPhone = ImagesGallery.listOfImages(getContext(), ""); // Tải ảnh từ bộ nhớ
        images = new ArrayList<>();
        insertSqlite(loadImagesPhone);  // Thêm ảnh vào DB
        imageAdapter = new ImageAdapter(getContext(), loadImagesPhone, new ImageAdapter.PhotoListener() {
            @Override
            public void onPhotoClick(String path) {
                // Do something with photo
                Intent intent = new Intent(getActivity(), FullScreenImageActivity.class);
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(imageAdapter);  // Dùng recyclerView để setAdapter
    }
    // Hàm chèn vòa DB
    public void insertSqlite(List<String> loadImagesPhone) {

        albumDataSource = new AlbumDataSource(getActivity());
        List<Photo> photos = albumDataSource.getPhotos();

        for (int i = 0; i < loadImagesPhone.size(); i++) {
            boolean check = false;
            for (int j = 0; j < photos.size(); j++) {
                if (photos.get(j).getPath().equals(loadImagesPhone.get(i)))
                    check = true;
            }
            if (!check) {
                long r = albumDataSource.insert(loadImagesPhone.get(i));
            }
        }

    }
}