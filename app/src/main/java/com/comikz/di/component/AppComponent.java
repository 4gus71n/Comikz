package com.comikz.di.component;

import android.content.Context;

import com.google.gson.Gson;
import com.comikz.MangaApplication;
import com.comikz.di.module.AppModule;
import com.comikz.di.module.NetworkModule;
import com.comikz.di.module.RepositoryModule;
import com.comikz.repositories.MangaRepository;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.Cache;

@Singleton
@Component(
        modules = {
                RepositoryModule.class,
                AppModule.class,
                NetworkModule.class
        }
)
public interface AppComponent {

    Gson getGson();

    Cache getCache();

    retrofit2.Retrofit getRetrofit();

    MangaApplication getReadMeApplication();

    Context getApplicationContext();

    MangaRepository getNotesNetworkRepository();

}
