package com.comikz.di.component;


import com.comikz.di.ActivityScope;
import com.comikz.di.module.InteractorModule;
import com.comikz.di.module.PresenterModule;
import com.comikz.ui.mangadetail.MangaDetailFragment;
import com.comikz.ui.mangalist.MangaListFragment;
import com.comikz.ui.mangareader.MangaReaderFragment;
import com.comikz.ui.mangareader.MangaReaderPageFragment;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = {
                PresenterModule.class,
                InteractorModule.class
        }
)
public interface ViewInjectorComponent {

        void inject(MangaReaderPageFragment fragment);

        void inject(MangaReaderFragment fragment);

        void inject(MangaListFragment fragment);

        void inject(MangaDetailFragment fragment);

}
