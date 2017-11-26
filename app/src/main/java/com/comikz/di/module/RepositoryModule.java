package com.comikz.di.module;

import com.comikz.repositories.MangaRepository;
import com.comikz.repositories.MangaRepositoryImp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    MangaRepository provideVoxRepository(Retrofit retrofit) {
        return new MangaRepositoryImp(retrofit);
    }

}
