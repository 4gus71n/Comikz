package com.comikz.ui.mangadetail;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.comikz.MangaApplication;
import com.comikz.R;
import com.comikz.model.Manga;
import com.comikz.model.MangaChapter;
import com.comikz.model.MangaDetail;
import com.comikz.ui.BaseFragment;
import com.comikz.ui.adapter.MangaChaptersAdapter;
import com.comikz.ui.mangareader.MangaReaderActivity;
import com.comikz.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Agustin Tomas Larghi on 2/7/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaDetailFragment extends BaseFragment implements MangaDetailView,
        MangaChaptersAdapter.Callback {

    //region Constant variables declaration
    public static final String TAG = MangaDetailFragment.class.getSimpleName();
    //ednregion

    //region Variables declaration
    @BindView(R.id.fragment_manga_detail_title)
    TextView mTitleTextView;

    @BindView(R.id.fragment_manga_detail_description)
    TextView mDescriptionTextView;

    @BindView(R.id.fragment_manga_detail_image)
    ImageView mImageView;

    @BindView(R.id.fragment_manga_detail_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_manga_detail_progressbar)
    ProgressBar mProgressBar;

    @Inject
    MangaDetailPresenter mPresenter;

    private MangaChaptersAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    //endregion

    //regionVoxDetailView implementation
    @Override
    public void onMangaDetailFetched(MangaDetail mangaDetail) {
        Glide.with(getContext()).load(mangaDetail.getImageUrl()).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                mImageView.setImageDrawable(resource);
            }
        });

        mDescriptionTextView.setText(Html.fromHtml(mangaDetail.getDescription()));
        mDescriptionTextView.setVisibility(View.VISIBLE);

        mAdapter.addMangaChapters(mangaDetail.getChapters());

        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onShowError(String string) {

    }
    //endregion


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }

        MangaApplication.getInstance().getViewComponent().inject(this);

        if (getArguments() != null) {
            mPresenter.onViewCreated(this, getArguments());
        }

        if (savedInstanceState != null) {
            mPresenter.onViewCreated(this, savedInstanceState);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manga_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new MangaChaptersAdapter(getContext(), this);

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mTitleTextView.setText(mPresenter.getManagaTitle());
        mDescriptionTextView.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mImageView.setTransitionName(mPresenter.getMangaId());
        }

        Glide.with(getContext()).load(mPresenter.getMangaCoverImage()).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                mImageView.setImageDrawable(resource);
                startPostponedEnterTransition();
            }
        });


        mPresenter.fetchMangaDetails();
    }

    //region MangaChaptersAdapter.Callback implementation
    @Override
    public void onMangaChapterClicked(MangaChapter mangaChapter) {
        Intent intent = MangaReaderActivity.getStartIntent(getContext(), mangaChapter);
        startActivity(intent);
    }
    //endregion

    private void scrollToPositionAndAnimate(final int position) {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isPositionBetweenLastAndFirst(position)) {
                    animateViewInPosition(recyclerView, position);
                    recyclerView.clearOnScrollListeners();
                }
            }
        });

        mRecyclerView.smoothScrollToPosition(position);
    }

    private boolean isPositionBetweenLastAndFirst(int position) {
        return mLayoutManager.findFirstVisibleItemPosition() <= position &&
                mLayoutManager.findLastVisibleItemPosition() >= position;
    }

    private void animateViewInPosition(RecyclerView recyclerView, int position) {
        //VoxCommentView view = (VoxCommentView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView;
        //view.animateBackground();
    }

    public static MangaDetailFragment newInstance(Manga manga) {
        MangaDetailFragment mangaDetailFragment = new MangaDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.ARG_BUNDLE_MANGA, manga);
        mangaDetailFragment.setArguments(args);
        return mangaDetailFragment;
    }

}
