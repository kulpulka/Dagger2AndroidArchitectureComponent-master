package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.AppConstants;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.R;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.entity.MovieEntity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.entity.TvEntity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.detail.activity.MovieDetailActivity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.detail.activity.TvDetailActivity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.detail.activity.VideoActivity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.search.viewmodel.activity.MovieSearchActivity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.search.viewmodel.activity.TvSearchActivity;

public class NavigationUtils implements AppConstants {
    public static void redirectToMovieSearchScreen(Activity activity) {
        Intent intent = new Intent(activity, MovieSearchActivity.class);
        activity.startActivity(intent);
    }

    public static void redirectToTvSearchScreen(Activity activity) {
        Intent intent = new Intent(activity, TvSearchActivity.class);
        activity.startActivity(intent);
    }

    public static void redirectToVideoScreen(Context context,
                                             String videoKey) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(INTENT_VIDEO_KEY, videoKey);
        context.startActivity(intent);
    }


    public static void redirectToDetailScreen(Activity activity,
                                              MovieEntity movie,
                                              ActivityOptionsCompat options) {
        Intent intent = new Intent(activity, MovieDetailActivity.class);
        intent.putExtra(INTENT_MOVIE, movie);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static void redirectToTvDetailScreen(Activity activity,
                                                TvEntity tvEntity,
                                                ActivityOptionsCompat options) {
        Intent intent = new Intent(activity, TvDetailActivity.class);
        intent.putExtra(INTENT_MOVIE, tvEntity);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static void replaceFragment(Activity activity,
                                       int navId,
                                       int selectedPosition) {
        Bundle bundle = new Bundle();
        bundle.putInt(INTENT_CATEGORY, selectedPosition);
        Navigation.findNavController(activity, R.id.fragment_nav_host)
                .navigate(navId, bundle, new NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setEnterAnim(R.anim.flip_right_in)
                        .setExitAnim(R.anim.flip_right_out)
                        .setPopEnterAnim(R.anim.flip_left_in)
                        .setPopExitAnim(R.anim.flip_left_out)
                        .build());
    }
}
