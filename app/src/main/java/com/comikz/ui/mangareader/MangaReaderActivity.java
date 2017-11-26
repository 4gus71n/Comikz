package com.comikz.ui.mangareader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.comikz.R;
import com.comikz.model.MangaChapter;
import com.comikz.utils.Constants;

/**
 * Created by Agustin Tomas Larghi on 4/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_empty);

        MangaChapter mangaChapter = null;

        if (getIntent().getExtras() != null) {
            mangaChapter = getIntent().getExtras().getParcelable(Constants.ARG_BUNDLE_MANGA_CHAPTER);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MangaReaderFragment.newInstance(mangaChapter), MangaReaderFragment.TAG)
                    .commit();
        }
    }

    public static Intent getStartIntent(Context context, MangaChapter mangaChapter) {
        Intent intent = new Intent(context, MangaReaderActivity.class);
        intent.putExtra(Constants.ARG_BUNDLE_MANGA_CHAPTER, mangaChapter);
        return intent;
    }

}
