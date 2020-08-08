package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.di.module;

import android.app.Application;

import androidx.room.Room;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.AppDatabase;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.dao.MovieDao;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.dao.TvDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;

@Module
public class DbModule {

    @Provides
    @Singleton
    AppDatabase provideDatabase(@NonNull Application application) {
        return Room.databaseBuilder(application,
                AppDatabase.class, "Entertainment.db")
                .allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    MovieDao provideMovieDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.movieDao();
    }


    @Provides
    @Singleton
    TvDao provideTvDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.tvDao();
    }
}