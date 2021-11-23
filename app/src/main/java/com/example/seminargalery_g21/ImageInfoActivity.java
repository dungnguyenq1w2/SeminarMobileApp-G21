package com.example.seminargalery_g21;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

public class ImageInfoActivity extends AppCompatActivity {
    Uri targetUri = null;
    String filepath;
    String size;
    File file;
    TextView name;
    TextView fileSize;
    TextView dateTime;
    TextView height;
    TextView width;
    TextView device;
    TextView txtFilepath;
    Button chooseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_info);
        chooseBtn = (Button) findViewById(R.id.choose_btn);
//        chooseBtn.setOnClickListener(chooseButtonOnClickListener);
        name = (TextView) findViewById(R.id.name);
        dateTime = (TextView) findViewById(R.id.date_time);
        height = (TextView) findViewById(R.id.height);
        width = (TextView) findViewById(R.id.width);
        device = (TextView) findViewById(R.id.device);
        fileSize = (TextView) findViewById(R.id.size);
        txtFilepath = (TextView) findViewById(R.id.filepath);

        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
    }

//    View.OnClickListener chooseButtonOnClickListener =
//            new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//                    intent.addCategory(Intent.CATEGORY_OPENABLE);
//                    intent.setType("image/*");
//                    startActivityForResult(intent, 1);
//                }
//            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri dataUri = data.getData();
            targetUri = dataUri;
            showExif(targetUri);
        }
    }

    // Tách tên image từ filepath
    private String setName(String filepath) {
        int pos = filepath.lastIndexOf("/");
        String title = filepath;

        if (-1 != pos) {
            title = filepath.substring(pos + 1);
        }
        return title;
    }

    void showExif(Uri photoUri) {
        if (photoUri != null) {

            ParcelFileDescriptor parcelFileDescriptor = null;
            String[] split = photoUri.getPath().split(":");
            file = new File(photoUri.getPath());
            filepath = split[1];
            //How to retrieve info of file https://developer.android.com/training/secure-file-sharing/retrieve-info#java
            Cursor returnCursor =
                    getContentResolver().query(photoUri, null, null, null, null);
            returnCursor.moveToFirst();
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            double sizeMB = returnCursor.getDouble(sizeIndex);
            sizeMB = sizeMB / (1024 * 1024);
            size = new DecimalFormat("#.0#").format(sizeMB);
            /*
            How to convert the Uri to FileDescriptor, refer to the example in the document:
            https://developer.android.com/guide/topics/providers/document-provider.html
             */
            try {
                parcelFileDescriptor = getContentResolver().openFileDescriptor(photoUri, "r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                ExifInterface exifInterface = new ExifInterface(fileDescriptor);
                name.setText("Tên ảnh: " + setName(filepath));
                txtFilepath.setText("Vị trí: " + filepath);
                fileSize.setText("Dung lượng: " + size + "MB");
                height.setText("Độ dài: " + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH) + "px");
                width.setText("Độ rộng: " + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH) + "px");
                dateTime.setText("Ngày giờ chụp: " + exifInterface.getAttribute(ExifInterface.TAG_DATETIME));
                device.setText("Thiết bị chụp: " + exifInterface.getAttribute(ExifInterface.TAG_MAKE) + " " + exifInterface.getAttribute(ExifInterface.TAG_MODEL));
                parcelFileDescriptor.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Something wrong:\n" + e.toString(),
                        Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Something wrong:\n" + e.toString(),
                        Toast.LENGTH_LONG).show();
            }

            String strPhotoPath = photoUri.getPath();

        } else {
            Toast.makeText(getApplicationContext(),
                    "Image Uri is null",
                    Toast.LENGTH_LONG).show();
        }
    }

    ;

}