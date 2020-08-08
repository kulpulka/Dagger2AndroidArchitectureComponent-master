package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.factory.ViewModelFactory;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.detail.viewmodel.MovieDetailViewModel;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.detail.viewmodel.TvDetailViewModel;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.main.viewmodel.MovieListViewModel;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.main.viewmodel.TvListViewModel;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.search.viewmodel.MovieSearchViewModel;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.search.viewmodel.TvSearchViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel.class)
    protected abstract ViewModel movieListViewModel(MovieListViewModel moviesListViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel.class)
    protected abstract ViewModel movieDetailViewModel(MovieDetailViewModel moviesDetailViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(MovieSearchViewModel.class)
    protected abstract ViewModel movieSearchViewModel(MovieSearchViewModel movieSearchViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(TvListViewModel.class)
    protected abstract ViewModel tvListViewModel(TvListViewModel tvListViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(TvDetailViewModel.class)
    protected abstract ViewModel tvDetailViewModel(TvDetailViewModel tvDetailViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(TvSearchViewModel.class)
    protected abstract ViewModel tvSearchViewModel(TvSearchViewModel tvSearchViewModel);
}
