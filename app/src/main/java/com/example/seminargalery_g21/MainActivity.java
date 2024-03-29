package com.example.seminargalery_g21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seminargalery_g21.database.AlbumDataSource;
import com.example.seminargalery_g21.helper.ImageAdapter;
import com.example.seminargalery_g21.helper.ImagesGallery;
import com.example.seminargalery_g21.helper.Photo;
import com.example.seminargalery_g21.helper.StateManager;
import com.example.seminargalery_g21.helper.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public boolean  permission = false;
    TextView album;
    TextView gallery;
    RecyclerView recyclerView;
    ImageAdapter imageAdapter;
    List<String> images;
    String albumName = "";
    List<String> loadImagesPhone;
    AlbumDataSource albumDataSource;
    private static final int MY_READ_PERMISSION_CODE = 101;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        // Check form permission
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_READ_PERMISSION_CODE);
        } else {
            permission = true;
        }

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

        if (permission) {
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            mViewPager.setAdapter(viewPagerAdapter);

            mTabLayout.setupWithViewPager(mViewPager);
        }
        // Lấy context để tải theme
        context = MainActivity.this;
        loadTheme();

//        //gallery_number = findViewById(R.id.gallery_number);
//        recyclerView = findViewById(R.id.recyclerview_gallery_images);



//        // Thay đổi màu cho textView ở trên thanh taskbar
//        gallery = findViewById(R.id.gallery);
//        SpannableString content = new SpannableString("Gallery");
//        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        gallery.setText(content);
//        gallery.setTextColor(Color.parseColor("#FF038f3d"));
//
//        // Xử lý chuyển qua màn hình AlbumActivity
//        album = (TextView) findViewById(R.id.album);
//        album.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), AlbumActivity.class);
//                startActivity(intent);
//            }
//        });
//
        // Xử lý chuyển qua màn hình SharingActivity
        ImageButton ic_image = (ImageButton) findViewById(R.id.ic_image);
        ic_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SharingActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý chuyển qua màn hình SettingsActivity
        ImageButton ic_settings = (ImageButton) findViewById(R.id.ic_settings);
        ic_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
    // Tải theme và đặt trạng thái này cho ứng dụng
    private void loadTheme() {
        int themeMode = StateManager.getThemeMode(context);
        if (themeMode == AppCompatDelegate.MODE_NIGHT_NO)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
//
    // Tải ảnh từ bộ nhớ và thêm vào database
//    private void loadImages() {
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//
//        loadImagesPhone = ImagesGallery.listOfImages(this, ""); // Tải ảnh từ bộ nhớ
//        images = new ArrayList<>();
//        insertSqlite(loadImagesPhone);  // Thêm ảnh vào DB
//        imageAdapter = new ImageAdapter(this, loadImagesPhone, new ImageAdapter.PhotoListener() {
//            @Override
//            public void onPhotoClick(String path) {
//                // Do something with photo
//                Intent intent = new Intent(getApplicationContext(), FullScreenImageActivity.class);
//                intent.putExtra("path", path);
//                startActivity(intent);
//            }
//        });
//        recyclerView.setAdapter(imageAdapter);  // Dùng recyclerView để setAdapter
//    }

    // Xin phép quyền truy cập vào bộ nhớ
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_READ_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read external storage permission granted", Toast.LENGTH_SHORT).show();
//                //loadImages();
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
                mViewPager.setAdapter(viewPagerAdapter);
                mTabLayout.setupWithViewPager(mViewPager);
                permission = true;
            } else {
                Toast.makeText(this, "Read external storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Hàm chèn vòa DB
//    public void insertSqlite(List<String> loadImagesPhone) {
//
//        albumDataSource = new AlbumDataSource(context);
//        List<Photo> photos = albumDataSource.getPhotos();
//
//        for (int i = 0; i < loadImagesPhone.size(); i++) {
//            boolean check = false;
//            for (int j = 0; j < photos.size(); j++) {
//                if (photos.get(j).getPath().equals(loadImagesPhone.get(i)))
//                    check = true;
//            }
//            if (!check) {
//                long r = albumDataSource.insert(loadImagesPhone.get(i));
//            }
//        }
//    }
}