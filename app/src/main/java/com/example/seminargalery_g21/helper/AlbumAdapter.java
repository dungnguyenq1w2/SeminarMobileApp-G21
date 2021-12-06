package com.example.seminargalery_g21.helper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seminargalery_g21.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Album> albums;
    protected AlbumAdapter.AlbumListener albumListener;

    public AlbumAdapter(Context context, ArrayList<Album> albums, AlbumAdapter.AlbumListener albumListener) {
        this.context = context;
        this.albums = albums;
        this.albumListener = albumListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.albums_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = albums.get(position);
        if (albums.get(position).getAlbumImage() != null)
        {
            Glide.with(context).load(album.getAlbumImage()).into(holder.albumImage);
            //Picasso.get().load(album.getAlbumImage()).into(holder.albumImage);
            holder.albumName.setText(album.getAlbumName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    albumListener.onAlbumClick(album);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView albumImage;
        TextView albumName;
        public ViewHolder (View itemView) {
            super(itemView);

            albumImage = itemView.findViewById(R.id.iv_album);
            albumName = itemView.findViewById(R.id.tv_album_name);

        }
    }

    public interface AlbumListener {
        void onAlbumClick(Album album);
    }
}
