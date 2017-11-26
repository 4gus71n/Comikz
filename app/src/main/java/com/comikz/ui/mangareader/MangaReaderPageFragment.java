package com.comikz.ui.mangareader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.comikz.MangaApplication;
import com.comikz.R;
import com.comikz.model.MangaChapter;
import com.comikz.model.MangaPage;
import com.comikz.ui.BaseFragment;
import com.comikz.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Agustin Tomas Larghi on 26/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaReaderPageFragment extends BaseFragment implements MangaReaderPageView {

    //region Variables declaration
    @BindView(R.id.fragment_manga_page_imageview)
    ImageView mPageImageView;

    @Inject
    MangaReaderPagePresenter mPresenter;
    //endregion

    //region MangaReaderPageView implementation

    @Override
    public void onPageFetched(MangaPage mangaPage) {
        Glide.with(getContext()).load(mangaPage.getImageUrl()).into(mPageImageView);
    }

    @Override
    public void onShowError(String error) {
        Snackbar.make(getView(), error, Snackbar.LENGTH_LONG).show();
    }

    //endregion

    //region Fragment lifecycle methods declaration
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manga_page, container, false);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mPresenter.fetchPage();
    }

    public static MangaReaderPageFragment newInstance(MangaChapter mangaChapter, int currentPage) {
        MangaReaderPageFragment fragment = new MangaReaderPageFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_BUNDLE_MANGA_CHAPTER_PAGE_NUMBER, currentPage);
        args.putParcelable(Constants.ARG_BUNDLE_MANGA_CHAPTER, mangaChapter);
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

}
