package com.example.seminargalery_g21.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seminargalery_g21.R;
import com.example.seminargalery_g21.model.Uploader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageUploadAdapter extends RecyclerView.Adapter<ImageUploadAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Uploader> mUploaders;

    public ImageUploadAdapter(Context context, List<Uploader> uploaders) {
        mContext = context;
        mUploaders = uploaders;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_card, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Uploader uploader = mUploaders.get(position);
        holder.tvName.setText(uploader.getName());
        Picasso.get()
                .load(uploader.getImageUrl())
                .into(holder.ivUpload);
    }

    @Override
    public int getItemCount() {
        return mUploaders.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public ImageView ivUpload;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            ivUpload = itemView.findViewById(R.id.iv_upload);
        }
    }
}
