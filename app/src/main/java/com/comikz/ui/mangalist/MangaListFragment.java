package com.comikz.ui.mangalist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.AnimatorInflaterCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comikz.MangaApplication;
import com.comikz.R;
import com.comikz.model.Manga;
import com.comikz.ui.BaseFragment;
import com.comikz.ui.adapter.MangaListAdapter;
import com.comikz.ui.mangadetail.MangaDetailActivity;
import com.comikz.ui.views.MangaItemView;
import com.comikz.utils.EndlessRecyclerOnScrollListener;
import com.comikz.utils.GridSpacingItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MangaListFragment extends BaseFragment implements MangaListView, MangaListAdapter.Callback {

    //region Constants declaration
    public static final String TAG = MangaListFragment.class.getSimpleName();
    private MangaListAdapter mAdapter;
    //endregion

    //region Variables declaration
    @Inject
    MangaListPresenter mPresenter;

    @BindView(R.id.manga_vox_list_swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.fragment_manga_list_recyclerview)
    RecyclerView mRecyclerView;

    private Animator mFadeOutTop, mFadeOutBottom;
    private Animator mFadeInTop, mFadeInBottom;
    private StaggeredGridLayoutManager mLayoutManager;
    //endregion

    //region MangaListView implementation
    @Override
    public void onAddMangasResults(List<Manga> mangas) {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.addMangas(mangas);
    }

    @Override
    public void onShowError(String error) {
        mSwipeRefreshLayout.setRefreshing(false);
        Snackbar.make(getView(), error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSetMangasResults(List<Manga> mangaList) {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.setMangas(mangaList);
    }

    //endregion

    //region Fragment lifecycle declaration
    public static MangaListFragment newInstance() {
        MangaListFragment fragment = new MangaListFragment();
        fragment.setArguments(new Bundle());
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manga_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupAdapter();

        mPresenter.fetchMangas(0);
        mSwipeRefreshLayout.setRefreshing(true);

        setupAnimations();
    }

    private void setupAdapter() {
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.fetchMangas(0);
            }
        });

        mAdapter = new MangaListAdapter(getActivity(), this);

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(true);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.app_small_padding);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true));

        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.fetchMangas(page);
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    private void setupAnimations() {
        mFadeOutTop = AnimatorInflaterCompat.loadAnimator(getContext(), android.R.animator.fade_out);
        mFadeOutTop.setDuration(250);
        mFadeOutBottom = AnimatorInflaterCompat.loadAnimator(getContext(), android.R.animator.fade_out);
        mFadeOutBottom.setDuration(250);

        mFadeInTop = AnimatorInflaterCompat.loadAnimator(getContext(), android.R.animator.fade_in);
        mFadeInTop.setDuration(250);
        mFadeInBottom = AnimatorInflaterCompat.loadAnimator(getContext(), android.R.animator.fade_in);
        mFadeInBottom.setDuration(250);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestroyView();
    }

    public void onQueryTextSubmit(String query) {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.searchMangas(query);
    }

    public void onSearchViewShown() {
        mPresenter.getMangaSearchResults();
    }

    public void onSearchViewClosed() {
        mPresenter.getMangaDirectoryResults();
    }
    //endregion

    //region Callback implementation
    @Override
    public void onItemClicked(final Manga manga, final MangaItemView view) {
        //I'm using an AnimatorInflator to inflates these animations in the fragment's onCreate method
        mFadeOutBottom.setTarget(view.getBottomOverlayView());
        mFadeOutTop.setTarget(view.getTopOverlayView());

        mFadeInBottom.setTarget(view.getBottomOverlayView());
        mFadeInTop.setTarget(view.getTopOverlayView());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(mFadeOutBottom, mFadeOutTop);


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //When the animation ends then I jump to the detail screen
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        view.getImageView(),
                        ViewCompat.getTransitionName(view.getImageView()));

                startActivity(MangaDetailActivity.getStartIntent(getActivity(), manga), options.toBundle());

                //I tried setting the fade in animation here, but it doesnt work
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(mFadeInBottom, mFadeInTop);
                animatorSet.start();
            }
        });

        animatorSet.start();
    }
    //endregion
}
