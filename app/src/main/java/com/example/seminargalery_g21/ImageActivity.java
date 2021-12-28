package com.example.seminargalery_g21;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seminargalery_g21.database.AlbumDataSource;
import com.example.seminargalery_g21.helper.ImageAdapter;
import com.example.seminargalery_g21.helper.ImagesGallery;
import com.example.seminargalery_g21.helper.Photo;
import com.example.seminargalery_g21.helper.StateManager;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageAdapter imageAdapter;
    List<String> images;
    TextView gallery_number;
    String albumName = "";
    AlbumDataSource albumDataSource;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_image);

        // Lấy context để tải theme
        context = ImageActivity.this;
        loadTheme();

        gallery_number = findViewById(R.id.gallery_number);
        recyclerView = findViewById(R.id.recyclerview_gallery_images);

        // Xử lý Button Back
        ImageButton ic_back = (ImageButton) findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImages();
    }

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
        Intent intent = getIntent();

        albumName = intent.getExtras().getString("albumName");
        //Toast.makeText(this, albumName,Toast.LENGTH_SHORT).show();
        if(albumName.equals("Favorites")){      // Xử lý cho album Yêu thích
            images = addFavorite();
        }
        else if(albumName.equals("Recycle Bin")) // Xử lý cho thùng rác
        {
            images = addRecycle();
        }
        else
        {
            images = ImagesGallery.listOfImages(this, albumName);
        }

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

        gallery_number.setText(albumName + " (" +images.size()+")");
    }

    private List<String> addRecycle() {
        List<String> temp = new ArrayList<>();;
        albumDataSource = new AlbumDataSource(context);
        List<Photo> photos = albumDataSource.getPhotos();
        for(int i = 0; i < photos.size(); i++)
        {
            if(photos.get(i).getRecycleBin() == 1)
            {
                temp.add(photos.get(i).getPath());
            }
        }
        return temp;
    }

    public List<String> addFavorite() {
        List<String> temp = new ArrayList<>();;
        albumDataSource = new AlbumDataSource(context);
        List<Photo> photos = albumDataSource.getPhotos();
        for(int i = 0; i < photos.size(); i++)
        {
            if(photos.get(i).getReact() == 1 && photos.get(i).getRecycleBin() == 0)
            {
                temp.add(photos.get(i).getPath());
            }
        }
        return temp;
    }
}