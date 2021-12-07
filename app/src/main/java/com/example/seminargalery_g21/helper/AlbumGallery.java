package com.example.seminargalery_g21.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import com.example.seminargalery_g21.R;
import java.util.ArrayList;

public class AlbumGallery {
    public static ArrayList<Album> listOfAlbum(Context context) {
        ArrayList <Album> albums = new ArrayList<>();
        ArrayList <String> albumsNames = new ArrayList<>();
        
        // Add Favorites and Recycle Bin Album
        String imageUri1, imageUri2;
        imageUri1 = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.ic_favorite_folder).toString();
        imageUri2 = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.ic_bin_folder).toString();
        albums.add(new Album("Favorites", imageUri1, new ArrayList<>()));
        albums.add(new Album("Recycle Bin", imageUri2, new ArrayList<>()));
        
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
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
                        break;
                    }
                }
            } else {
                Album album = new Album(null, null, null);
                album.setAlbumName( bucketName );
                album.setAlbumImage( image.getPhotoUri() );
                album.getAlbumPhotosUri().add( image );

                albums.add( album );
                albumsNames.add( bucketName );
            }

        }
        return albums;
    }
}
