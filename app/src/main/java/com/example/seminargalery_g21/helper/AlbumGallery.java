package com.example.seminargalery_g21.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class AlbumGallery {
    public static ArrayList<Album> listOfAlbum(Context context) {
        ArrayList <Album> albums = new ArrayList<>();
        ArrayList <String> albumsNames = new ArrayList<>();
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
//        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        String bucketName ;

        while (cursor.moveToNext()) {
            bucketName = cursor.getString(column_index_folder_name);
            absolutePathOfImages = cursor.getString(column_index_data);

            Image image = new Image();
            image.setAlbumName(bucketName);
            image.setPhotoUri(absolutePathOfImages);

            if ( albumsNames.contains( bucketName ) ) {
                for ( Album album : albums ) {
                    if ( album.getAlbumName().equals( bucketName ) ) {
                        album.getAlbumPhotosUri().add( image );
                        Log.i( "DeviceImageManager", "A photo was added to album => " + bucketName );
                        break;
                    }
                }
            } else {
                Album album = new Album(null, null, null);
                Log.i( "DeviceImageManager", "A new album was created => " + bucketName );
                album.setAlbumName( bucketName );
                album.setAlbumImage( image.getPhotoUri() );
                album.getAlbumPhotosUri().add( image );
                Log.i( "DeviceImageManager", "A photo was added to album => " + bucketName );

                albums.add( album );
                albumsNames.add( bucketName );
            }

//            listOfAllImages.add(absolutePathOfImages);
        }
        return albums;
    }
}
