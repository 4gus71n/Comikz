package com.comikz.ui.views;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.comikz.R;
import com.comikz.model.Manga;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Agustin Tomas Larghi on 6/10/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaItemView extends RelativeLayout {

    private Manga mItem;
    private OnVoxItemViewClicked mCallback;

    @BindView(R.id.view_manga_item_imageview)
    ImageView mImageView;

    @BindView(R.id.view_manga_item_title_textview)
    TextView mTextView;

    @BindView(R.id.view_manga_item_rate_textview)
    TextView mRate;

    @BindView(R.id.view_manga_item_top_overlay)
    View mTopOverlay;

    public MangaItemView(Context context) {
        super(context);
    }

    public MangaItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MangaItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void loadMangaItem(Manga manga, OnVoxItemViewClicked callback) {
        mItem = manga;
        mCallback = callback;
        updateView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    private void updateView() {
        ViewCompat.setTransitionName(mImageView, mItem.getId() );

        mTextView.setText(mItem.getTitle());
        mRate.setText(mItem.getRanking());
        Glide.with(getContext()).load(mItem.getImage()).into(mImageView);
    }

    @OnClick(R.id.view_manga_item_container)
    void onWholeLayoutClicked() {
        mCallback.onItemClicked(mItem, this);
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public View getBottomOverlayView() {
        return mTextView;
    }

    public View getTopOverlayView() {
        return mTopOverlay;
    }
}
