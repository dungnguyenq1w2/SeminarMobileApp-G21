package com.example.seminargalery_g21;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout llTheme;
    private LinearLayout llFontSize;
    private LinearLayout llShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        llTheme = (LinearLayout) findViewById(R.id.ll_theme);
        llFontSize = (LinearLayout) findViewById(R.id.ll_font_size);
        llShare = (LinearLayout) findViewById(R.id.ll_share);

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