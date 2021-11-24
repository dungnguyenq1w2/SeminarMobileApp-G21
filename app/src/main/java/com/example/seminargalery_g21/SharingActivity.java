package com.example.seminargalery_g21;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seminargalery_g21.helper.ImageUploadAdapter;
import com.example.seminargalery_g21.model.Uploader;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Vector;

public class SharingActivity extends AppCompatActivity {

    private RecyclerView rvImages;

    private ImageUploadAdapter mImageUploadAdapter;

    private FirebaseFirestore mFirestore;
    private List<Uploader> mUploaders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        rvImages = (RecyclerView) findViewById(R.id.rv_images);
        rvImages.setHasFixedSize(true);
        rvImages.setLayoutManager(new LinearLayoutManager(SharingActivity.this));

        mUploaders = new Vector<>();

        mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("images")
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            mUploaders.add(new Uploader(
                               documentChange.getDocument().get("name").toString(),
                               documentChange.getDocument().get("imageUrl").toString()
                            ));
                        }

                        mImageUploadAdapter = new ImageUploadAdapter(SharingActivity.this, mUploaders);
                        mImageUploadAdapter.notifyDataSetChanged();
                        rvImages.setAdapter(mImageUploadAdapter);
                    }
                });
    }
}