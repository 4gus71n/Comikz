package com.comikz;

import android.app.Application;

import com.comikz.di.component.AppComponent;
import com.comikz.di.component.DaggerAppComponent;
import com.comikz.di.component.DaggerViewInjectorComponent;
import com.comikz.di.component.ViewInjectorComponent;
import com.comikz.di.module.AppModule;
import com.comikz.di.module.NetworkModule;

/**
 * Created by Agustin Tomas Larghi on 2/7/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
public class MangaApplication extends Application {

    private static MangaApplication mInstance;
    private String mHost;
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mHost = BuildConfig.HOST;

        initializeInjectors();
    }

    protected void initializeInjectors() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(mHost))
                .build();
    }

    public ViewInjectorComponent getViewComponent() {
        return DaggerViewInjectorComponent.builder()
                .appComponent(MangaApplication.getInstance().getComponent())
                .build();

    }

    private AppComponent getComponent(){
        return mAppComponent;
    }

    public static MangaApplication getInstance() {
        return mInstance;
    }
}
