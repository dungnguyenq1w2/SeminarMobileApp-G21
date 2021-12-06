package com.example.seminargalery_g21;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.seminargalery_g21.helper.StateManager;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout llDarkTheme;
    private TextView tvDarkTheme;
    private LinearLayout llFontSize;
    private LinearLayout llShare;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        llDarkTheme = (LinearLayout) findViewById(R.id.ll_dark_theme);
        tvDarkTheme = (TextView) findViewById(R.id.tv_dark_theme);
//        llFontSize = (LinearLayout) findViewById(R.id.ll_font_size);
        llShare = (LinearLayout) findViewById(R.id.ll_share);

        // context dùng để truy xuất, chỉnh sửa SharedPreferences
        context = SettingsActivity.this;
        setTitle("Settings");

        // lấy trạng thái theme hiện tại
        int themeMode = StateManager.getThemeMode(context);
        if (themeMode == AppCompatDelegate.MODE_NIGHT_NO)
            tvDarkTheme.setText(R.string.enable_dark_theme);    // nếu đang light theme thì hiện 'Enable dark theme'
        else
            tvDarkTheme.setText(R.string.disable_dark_theme);   // nếu đang dark theme thì hiện 'Disable dark theme'

        // sự kiện click vào llDarkTheme để toggle giữa hai trạng thái
        llDarkTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (themeMode == AppCompatDelegate.MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    StateManager.setThemeMode(context, AppCompatDelegate.MODE_NIGHT_YES);   // cập nhật trạng thái sang dark
                    tvDarkTheme.setText(R.string.disable_dark_theme);
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    StateManager.setThemeMode(context, AppCompatDelegate.MODE_NIGHT_NO);    // cập nhật trạng thái sang light
                    tvDarkTheme.setText(R.string.enable_dark_theme);
                }
            }
        });

        // sự kiện click vào llShare để tiến hành chia sẻ URL của ứng dụng
        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "URL to app will be showed here");
                startActivity(Intent.createChooser(intent, "Share via"));
            }
        });
    }
}