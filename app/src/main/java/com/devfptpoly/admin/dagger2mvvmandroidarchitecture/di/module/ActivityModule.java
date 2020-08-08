package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.di.module;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.detail.activity.MovieDetailActivity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.detail.activity.TvDetailActivity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.main.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector()
    abstract MovieDetailActivity contributeMovieDetailActivity();

    @ContributesAndroidInjector()
    abstract TvDetailActivity contributeTvDetailActivity();

    @ContributesAndroidInjector()
    abstract com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.search.viewmodel.activity.MovieSearchActivity contributeMovieSearchActivity();

    @ContributesAndroidInjector()
    abstract com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.search.viewmodel.activity.TvSearchActivity contributeTvSearchActivity();
}
