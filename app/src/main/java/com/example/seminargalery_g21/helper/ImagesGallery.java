package com.example.seminargalery_g21.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.seminargalery_g21.database.AlbumDataSource;

import java.util.ArrayList;
import java.util.List;

public class ImagesGallery {

    public static ArrayList<String> listOfImages(Context context, String albumName) {

        AlbumDataSource albumDataSource;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<>();
        String absolutePathOfImages;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        String orderBy = MediaStore.Video.Media.DATE_TAKEN;
        cursor = context.getContentResolver().query(uri, projection, null,null, orderBy+" DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        //get folder name
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        String bucketName ;

        albumDataSource = new AlbumDataSource(context);
        List<Photo> photos = albumDataSource.getPhotos();

        albumDataSource.close();

        while (cursor.moveToNext()) {
            bucketName = cursor.getString(column_index_folder_name);
            absolutePathOfImages = cursor.getString(column_index_data);

            if ((albumName == ""|| bucketName.equals(albumName))) {
                listOfAllImages.add(absolutePathOfImages);
               for (int i = 0 ; i < photos.size(); i++) {
                   if (photos.get(i).getPath().equals(absolutePathOfImages) && photos.get(i).getRecycleBin() == 1) {
                       listOfAllImages.remove(listOfAllImages.size() - 1);
                   }
               }
            }
        }
        return listOfAllImages;
    }
}
