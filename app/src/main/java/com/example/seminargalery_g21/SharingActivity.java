package com.example.seminargalery_g21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.seminargalery_g21.helper.ImageUploadAdapter;
import com.example.seminargalery_g21.model.Uploader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Vector;

public class SharingActivity extends AppCompatActivity {

    private RecyclerView rvImages;

    private ImageUploadAdapter mImageUploadAdapter;

    private DatabaseReference mDatabaseRef;
    private List<Uploader> mUploaders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        rvImages = (RecyclerView) findViewById(R.id.rv_images);
        rvImages.setHasFixedSize(true);
        rvImages.setLayoutManager(new LinearLayoutManager(SharingActivity.this));

        mUploaders = new Vector<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("/uploads");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Uploader uploader = postSnapshot.getValue(Uploader.class);
                    mUploaders.add(uploader);
                }

                mImageUploadAdapter = new ImageUploadAdapter(SharingActivity.this, mUploaders);
                rvImages.setAdapter(mImageUploadAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SharingActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}