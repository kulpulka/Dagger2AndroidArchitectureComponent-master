package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.converter.CastListTypeConverter;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.converter.CreditResponseTypeConverter;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.converter.CrewListTypeConverter;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.converter.GenreListTypeConverter;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.converter.MovieListTypeConverter;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.converter.StringListConverter;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.converter.TvListTypeConverter;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.converter.VideoListTypeConverter;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.dao.MovieDao;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.dao.TvDao;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.entity.MovieEntity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.entity.TvEntity;

@Database(entities = {MovieEntity.class, TvEntity.class}, version = 1,  exportSchema = false)
@TypeConverters({GenreListTypeConverter.class, VideoListTypeConverter.class,
        CreditResponseTypeConverter.class, MovieListTypeConverter.class,
        CastListTypeConverter.class, CrewListTypeConverter.class,
        StringListConverter.class, TvListTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    public abstract TvDao tvDao();


    private static volatile AppDatabase INSTANCE;
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, "Entertainment.db")
                .allowMainThreadQueries().build();
    }
}
