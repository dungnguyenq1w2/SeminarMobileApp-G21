package com.example.seminargalery_g21;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ImageInfoActivity extends AppCompatActivity {

    TextView txtName;
    TextView txtSize;
    TextView txtDateTime;
    TextView txtHeight;
    TextView txtWidth;
    TextView txtDevice;
    TextView txtDeviceTitle;
    TextView txtFilepath;
    String size;
    File imageFile;
    ImageButton ic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_info);
        getSupportActionBar().hide();

        // Lấy dữ liệu từ FullScreenImage
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        //
        txtName = (TextView) findViewById(R.id.name);
        txtDateTime = (TextView) findViewById(R.id.date_time);
        txtHeight = (TextView) findViewById(R.id.height);
        txtWidth = (TextView) findViewById(R.id.width);
        txtDevice = (TextView) findViewById(R.id.device);
        txtSize = (TextView) findViewById(R.id.size);
        txtFilepath = (TextView) findViewById(R.id.filepath);
        txtDeviceTitle = (TextView) findViewById(R.id.device_title) ;
        // Mũi tên quay lại
        ic_back = (ImageButton) findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (path != null) {
            showExif(path);
        } else {
            Toast.makeText(getApplicationContext(), "Can't get Image Path.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    // Tách tên ảnh từ filepath
    private String getName(String filepath) {
        int pos = filepath.lastIndexOf("/");
        String title = filepath;
        if (-1 != pos) {
            title = filepath.substring(pos + 1);
        }
        return title;
    }

    // Tính kích thước ảnh
    private String getFileSize(File file) {
        String res;
        double sizeKB = Double.parseDouble(String.valueOf(file.length() * 1.0 / 1024));
        if (sizeKB > 1024) {
            double sizeMB = sizeKB / 1024;
            res = new DecimalFormat("#.0#").format(sizeMB) + " MB";
        } else {
            res = new DecimalFormat("#.0#").format(sizeKB) + " KB";
        }
        return res;
    }

    // Định dạng thời gian
    private String formatDate(String exifDateTime, File file) {
        String res = "01/01/2001";
        if (exifDateTime == null) {
            Date lastModDate = new Date(file.lastModified());
            res = DateFormat.getInstance().format(lastModDate);
        } else {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.US);
                Date newDate = simpleDateFormat.parse(exifDateTime);
                res = DateFormat.getInstance().format(newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    // Định dạng lại vị trí ảnh
    private String formatFilePath(String filePath) {
        String res = "/Bộ nhớ trong/";
        String defaultPath = "/storage/emulated/0/";
        int pos = filePath.indexOf(defaultPath);
        if (pos != -1) {
            int lastDash = filePath.lastIndexOf("/");
            String subStr = filePath.substring(defaultPath.length(), lastDash);
            res = res + subStr;
        } else {
            res = filePath;
        }
        return res;
    }

    // Hàm hiển thị các thông tin của bức ảnh
    // tham khảo từ: http://android-er.blogspot.com/2017/04/read-exif-tag-of-jpg-using.html
    public void showExif(String path) {
        imageFile = new File(path);
        Uri imageUri = (Uri) Uri.fromFile(imageFile);
        size = getFileSize(imageFile);

        try {
            ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(imageUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            ExifInterface exifInterface = new ExifInterface(fileDescriptor);

            // Định dạng lại các thông tin trước khi hiện ra màn hình
            String filePath = formatFilePath(path);
            String dateTime = (String) exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            dateTime = formatDate(dateTime, imageFile);
            String make = exifInterface.getAttribute(ExifInterface.TAG_MAKE);
            String model = exifInterface.getAttribute(ExifInterface.TAG_MODEL);
            int height = Integer.parseInt(exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH));
            int width = Integer.parseInt(exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH));
            // Lấy thông tin chiều cao, chiều rộng, link tham khảo https://stackoverflow.com/questions/16440863/can-i-get-image-file-width-and-height-from-uri-in-android/16440953
            if (height <= 0 || width <= 0) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, options);
                width = options.outWidth;
                height = options.outHeight;
            }
            //
//            txtName.setText("Tên ảnh: " + getName(path));
//            txtFilepath.setText("Vị trí: " + filePath);
//            txtSize.setText("Dung lượng: " + size);
//            txtHeight.setText("Chiều cao: " + Integer.toString(height) + "px");
//            txtWidth.setText("Chiều rộng: " + Integer.toString(width) + "px");
//            txtDateTime.setText("Ngày giờ chụp: " + dateTime);
//            if (make != null && model != null) {
//                txtDevice.setText("Thiết bị chụp: " + make + " " + model);
//            } else {
//                txtDevice.setVisibility(View.INVISIBLE);
//            }
            txtName.setText( getName(path));
            txtFilepath.setText(filePath);
            txtSize.setText(size);
            txtHeight.setText(Integer.toString(height) + "px");
            txtWidth.setText(Integer.toString(width) + "px");
            txtDateTime.setText(dateTime);
            if (make != null && model != null) {
                txtDevice.setText(make + " " + model);
            } else {
                txtDevice.setVisibility(View.INVISIBLE);
                txtDeviceTitle.setVisibility(View.INVISIBLE);
            }
            parcelFileDescriptor.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error FileNotFoundException:\n" + e.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error IOException:\n" + e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}