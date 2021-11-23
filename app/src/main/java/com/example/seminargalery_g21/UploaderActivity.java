package com.example.seminargalery_g21;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seminargalery_g21.model.Uploader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UploaderActivity extends AppCompatActivity {

    private ProgressBar pbUpload;
    private ImageView ivPreview;
    private ImageButton ibAdd;
    private ImageButton ibUpload;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploader);

        pbUpload = (ProgressBar) findViewById(R.id.pb_upload);
        ivPreview = (ImageView) findViewById(R.id.iv_preview);
        ibAdd = (ImageButton) findViewById(R.id.ib_add);
        ibUpload = (ImageButton) findViewById(R.id.ib_send);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mFirestore = FirebaseFirestore.getInstance();

        // nhận ảnh đã chọn từ intent
        ActivityResultLauncher<Intent> addImageResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK &&
                            result.getData() != null &&
                            result.getData().getData() != null) {
                            mImageUri = result.getData().getData();

                            Picasso.get().load(mImageUri).placeholder(R.drawable.bg_upload).into(ivPreview);
                        }
                    }
                }
        );

        // intent chọn ảnh
        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                addImageResultLauncher.launch(intent);
            }
        });

        // tải ảnh lên firebase
        ibUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImageUri != null) {
                    StorageReference reference = mStorageRef.child(System.currentTimeMillis() +
                            "." + getFileExtension(mImageUri));
                    reference.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {    // xử lý khi tải thành công
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            pbUpload.setProgress(0);
                                        }
                                    }, 50);

                                    Toast.makeText(UploaderActivity.this, "Upload successfully", Toast.LENGTH_SHORT).show();
                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Uploader uploader = new Uploader(
                                                    mImageUri.toString(),
                                                    uri.toString());

                                            Map<String, Object> image = new HashMap<>();
                                            image.put("imageUrl", uploader.getImageUrl());
                                            image.put("name", uploader.getName());

                                            mFirestore.collection("images").add(image);
                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() { // xử lý khi bị lỗi
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UploaderActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {  // xử lý khi đang trong quá trình
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    pbUpload.setProgress((int)progress);
                                }
                            });
                }
                else {
                    Toast.makeText(UploaderActivity.this, "Please choose an image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // lấy phần mở rộng của file thông qua Uri
    private String getFileExtension(Uri uri) {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }
}