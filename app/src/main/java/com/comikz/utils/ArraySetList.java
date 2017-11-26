package com.comikz.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Agustin Tomas Larghi on 5/11/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class ArraySetList<T> extends ArrayList<T> {

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T e : c) {
            add(e);
        }
        return true;
    }

    @Override
    public boolean add(T e) {
        if (contains(e)) {
            set(indexOf(e), e);
            return true;
        } else {
            return super.add(e);
        }
    }
}
