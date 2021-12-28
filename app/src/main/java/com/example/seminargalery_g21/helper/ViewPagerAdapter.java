package com.example.seminargalery_g21.helper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.seminargalery_g21.AlbumFragment;
import com.example.seminargalery_g21.ImageFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ImageFragment();
            case 1:
                return new AlbumFragment();
            default:
                return new ImageFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch(position) {
            case 0:
                title = "Image";
                break;
            case 1:
                title = "Album";
                break;
        }

        return title;
    }
}
