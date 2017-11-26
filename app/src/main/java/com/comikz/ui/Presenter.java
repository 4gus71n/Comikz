package com.comikz.ui;

import android.os.Bundle;

/**
 * Created by Agustin Tomas Larghi on 2/7/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public interface Presenter<T extends View> {

    /**
     * Executed when the presenter is first attached to the view
     * @param view The view
     * @param bundle The arguments
     */
    void onViewCreated(T view, Bundle bundle);

    /**
     * Executed when the view triggered a {Fragment{@link #onSaveInstance(Bundle)}} event
     * @param bundle
     */
    void onSaveInstance(Bundle bundle);

    /**
     * Executed when the view is destroyed
     */
    void onDestroyView();

}
