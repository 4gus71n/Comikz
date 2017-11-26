package com.comikz.ui.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agustin Tomas Larghi on 26/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaStripeImageView extends AppCompatImageView {

    private List<Integer> yEdges = new ArrayList<>(), xEdges = new ArrayList<>();

    public MangaStripeImageView(Context context) {
        super(context);
    }

    public MangaStripeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MangaStripeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int x : xEdges) {
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStrokeWidth(1);
            canvas.drawLine(x, 0, x, canvas.getHeight(), paint);
        }
        for (int y : yEdges) {
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStrokeWidth(1);
            //canvas.drawLine(0, y, canvas.getWidth(), y, paint);
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        for (int x = 0; x < bm.getWidth(); x++) {
            if (isHorizontalLineDivision(x, bm)) {
                markHorizontalLineDivision(x);
            }
        }

        for (int y = 0; y < bm.getHeight(); y++) {
            if (isVerticalLineDivision(y, bm)) {
                markVerticalLineDivison(y);
            }
        }

        super.setImageBitmap(bm);
    }



    private void markVerticalLineDivison(int y) {
        yEdges.add(y);
    }

    private boolean isVerticalLineDivision(int y, Bitmap bm) {
        int pixelTotal = 0;
        for (int x = 0; x < bm.getWidth(); x++) {
            pixelTotal += bm.getPixel(x, y);
        }
        return (pixelTotal / bm.getHeight()) >= 200;
    }

    private void markHorizontalLineDivision(int x) {
        xEdges.add(x);
    }

    private boolean isHorizontalLineDivision(int x, Bitmap bm) {
        int pixelTotal = 0;
        for (int y = 0; y < bm.getHeight(); y++) {
            pixelTotal += bm.getPixel(x, y);
        }
        return (pixelTotal / bm.getHeight()) >= 200;
    }

    public void setImageUrl(String imageUrl) {
        onLoadImageFromUrl(imageUrl);
    }

    protected void onLoadImageFromUrl(String imageUrl) {
        Glide.with(getContext()).load(imageUrl).asBitmap().into(this);
    }
}
