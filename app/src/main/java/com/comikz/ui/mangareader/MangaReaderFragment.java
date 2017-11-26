package com.comikz.ui.mangareader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comikz.MangaApplication;
import com.comikz.R;
import com.comikz.model.MangaChapter;
import com.comikz.model.MangaPage;
import com.comikz.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Agustin Tomas Larghi on 4/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaReaderFragment extends Fragment implements MangaReaderView {

    //region Constant variables declaration
    public static final String TAG = MangaReaderFragment.class.getSimpleName();
    //endregion

    //region Variables declaration
    @BindView(R.id.fragment_manga_reader_viewpager)
    ViewPager mViewPager;

    @Inject
    MangaReaderPresenter mPresenter;
    private MangaReaderAdapter mAdapter;
    //endregion

    //region Fragment lifecycle methods
    @Override
    public void onShowError(String error) {
        Snackbar.make(getView(), error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onFirstMangaPageFetched(MangaChapter mangaChapter, MangaPage firstPage) {
        mAdapter = new MangaReaderAdapter(getFragmentManager(), mangaChapter, firstPage.getTotalPages());
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MangaApplication.getInstance().getViewComponent().inject(this);

        if (getArguments() != null) {
            mPresenter.onViewCreated(this, getArguments());
        }

        if (savedInstanceState != null) {
            mPresenter.onViewCreated(this, savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstance(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manga_reader, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mPresenter.fetchFirstMangaPage();
    }

    public static Fragment newInstance(MangaChapter mangaChapter) {
        MangaReaderFragment mangaReaderFragment = new MangaReaderFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.ARG_BUNDLE_MANGA_CHAPTER, mangaChapter);
        mangaReaderFragment.setArguments(args);
        return mangaReaderFragment;
    }
    //endregion
}
