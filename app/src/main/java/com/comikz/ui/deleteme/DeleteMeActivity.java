package com.comikz.ui.deleteme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.comikz.R;
import com.comikz.ui.views.MangaStripeImageView;

/**
 * Created by Agustin Tomas Larghi on 26/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class DeleteMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteme);

        MangaStripeImageView deleteMeImageView = (MangaStripeImageView) findViewById(R.id.deleteme_imageview);
        deleteMeImageView.setImageUrl("https://lmfcdn.secure.footprint.net/store/manga/11362/TBD-083.0/compressed/q083-015.jpg?token=70a3e0d77fff40be622565fb050eea05e4b806d7&ttl=1511748000");

    }
}
