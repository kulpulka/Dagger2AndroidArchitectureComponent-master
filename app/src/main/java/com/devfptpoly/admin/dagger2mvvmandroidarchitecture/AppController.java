package com.devfptpoly.admin.dagger2mvvmandroidarchitecture;

import android.app.Activity;
import android.app.Application;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.di.components.DaggerApiComponent;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.di.module.ApiModule;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.di.module.DbModule;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class AppController extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApiComponent.builder()
                .application(this)
                .apiModule(new ApiModule())
                .dbModule(new DbModule())
                .build()
                .inject(this);
    }
}
