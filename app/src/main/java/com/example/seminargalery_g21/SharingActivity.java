package com.example.seminargalery_g21;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seminargalery_g21.helper.ImageUploadAdapter;
import com.example.seminargalery_g21.model.Uploader;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Vector;

public class SharingActivity extends Activity {

    private Button btnToCreatePost;
    private RecyclerView rvImages;

    private ImageUploadAdapter mImageUploadAdapter;

    private FirebaseFirestore mFirestore;
    private List<Uploader> mUploaders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        btnToCreatePost = (Button) findViewById(R.id.ib_to_create_post);
        rvImages = (RecyclerView) findViewById(R.id.rv_images);
        rvImages.setHasFixedSize(true);
        rvImages.setLayoutManager(new LinearLayoutManager(SharingActivity.this));

        mFirestore = FirebaseFirestore.getInstance();

        btnToCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SharingActivity.this, UploaderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() { // tải ảnh mỗi khi resume, hoặc khởi động activity lần đầu, hoặc back từ activity đăng ảnh
        super.onResume();

        mUploaders = new Vector<>();    // khởi tạo lại vector tránh trùng lap75 dữ liệu

        mFirestore.collection("images")
                .orderBy("pubDate", Query.Direction.DESCENDING)
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() { // phương thức bắt ảnh realtime
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            mUploaders.add(new Uploader(
                                    documentChange.getDocument().getString("caption"),
                                    documentChange.getDocument().getString("imageUrl"),
                                    documentChange.getDocument().getTimestamp("pubDate")
                            ));
                        }

                        mImageUploadAdapter = new ImageUploadAdapter(SharingActivity.this, mUploaders);
                        rvImages.setAdapter(mImageUploadAdapter);
                    }
                });
    }
}