package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.di.module;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.main.fragment.MovieListFragment;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.main.fragment.TvListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract MovieListFragment contributeMovieListFragment();

    @ContributesAndroidInjector
    abstract TvListFragment contributeTvListFragment();
}
