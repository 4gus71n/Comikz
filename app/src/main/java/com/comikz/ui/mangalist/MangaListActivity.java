package com.comikz.ui.mangalist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.claudiodegio.msv.MaterialSearchView;
import com.claudiodegio.msv.OnSearchViewListener;
import com.comikz.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MangaListActivity extends AppCompatActivity implements OnSearchViewListener {

    //region Variables declaration
    @BindView(R.id.toolbar_serach_view)
    MaterialSearchView mSearchToolbarView;

    @BindView(R.id.activity_toolbar)
    Toolbar mToolbar;
    //endregion

    //region OnSearchViewListener implementation
    @Override
    public void onSearchViewShown() {
        MangaListFragment fragment = (MangaListFragment) getSupportFragmentManager().findFragmentByTag(MangaListFragment.TAG);
        if (fragment != null) {
            fragment.onSearchViewShown();
        }
    }

    @Override
    public void onSearchViewClosed() {
        MangaListFragment fragment = (MangaListFragment) getSupportFragmentManager().findFragmentByTag(MangaListFragment.TAG);
        if (fragment != null) {
            fragment.onSearchViewClosed();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        MangaListFragment fragment = (MangaListFragment) getSupportFragmentManager().findFragmentByTag(MangaListFragment.TAG);
        if (fragment != null) {
            fragment.onQueryTextSubmit(query);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onQueryTextChange(String s) {
        //Nothing to do here
    }
    //endregion

    //region Activity lifecycle methods


    @Override
    public void onBackPressed() {
        if (mSearchToolbarView.isOpen()) {
            mSearchToolbarView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_list);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getTitle());

        mSearchToolbarView.setOnSearchViewListener(this);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MangaListFragment.newInstance(), MangaListFragment.TAG)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manga_list_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mSearchToolbarView.setMenuItem(item);

        return true;
    }
    //endregion


}
