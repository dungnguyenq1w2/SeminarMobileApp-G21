package com.example.seminargalery_g21.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seminargalery_g21.R;
import com.example.seminargalery_g21.model.Album;

import java.util.List;

public class AlbumAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Album> albums;

    public AlbumAdapter(Context context, int layout, List<Album> albums) {
        this.context = context;
        this.layout = layout;
        this.albums = albums;
    }

    @Override
    public int getCount() {
        return albums.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        ImageView ivAlbum;
        TextView tvAlbumName;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();

            holder.tvAlbumName = (TextView) view.findViewById(R.id.tv_album_name);
            holder.ivAlbum = (ImageView) view.findViewById(R.id.iv_album);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        Album album = albums.get(i);

        holder.tvAlbumName.setText(album.getAlbumName());
        holder.ivAlbum.setImageResource(album.getAlbumImage());

        return view;
    }
}
