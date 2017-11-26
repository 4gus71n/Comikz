package com.comikz.ui.mangadetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.comikz.R;
import com.comikz.model.Manga;
import com.comikz.utils.Constants;

/**
 * Created by Agustin Tomas Larghi on 2/7/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_empty);

        Manga manga = null;

        if (getIntent().getExtras() != null) {
            manga = getIntent().getExtras().getParcelable(Constants.ARG_BUNDLE_MANGA);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MangaDetailFragment.newInstance(manga), MangaDetailFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent getStartIntent(Context context, Manga manga) {
        Intent intent = new Intent(context, MangaDetailActivity.class);
        intent.putExtra(Constants.ARG_BUNDLE_MANGA, manga);
        return intent;
    }
}
