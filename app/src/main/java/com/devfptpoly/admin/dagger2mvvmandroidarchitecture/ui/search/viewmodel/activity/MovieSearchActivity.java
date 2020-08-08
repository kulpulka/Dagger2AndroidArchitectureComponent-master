package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.search.viewmodel.activity;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.R;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.entity.MovieEntity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.entity.TvEntity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.factory.ViewModelFactory;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.base.BaseActivity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.base.custom.recyclerview.RecyclerItemClickListener;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.main.activity.MainActivity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.search.adapter.MovieSearchListAdapter;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.search.viewmodel.MovieSearchViewModel;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.utils.AppUtils;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.utils.NavigationUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MovieSearchActivity extends BaseActivity implements SearchView.OnQueryTextListener, RecyclerItemClickListener.OnRecyclerViewItemClickListener {

    @Inject
    ViewModelFactory viewModelFactory;

    MovieSearchViewModel searchViewModel;
    private com.devfptpoly.admin.dagger2mvvmandroidarchitecture.databinding.SearchActivityBinding binding;
    private MovieSearchListAdapter searchListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        initialiseView();
        initialiseViewModel();
    }

    private void initialiseView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        binding.search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        binding.search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        binding.search.setIconifiedByDefault(false);
        binding.search.setOnQueryTextListener(this);

        EditText searchEditText = binding.search.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(android.R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(android.R.color.white));
        Typeface myCustomFont = ResourcesCompat.getFont(getApplicationContext(), R.font.gt_medium);
        searchEditText.setTypeface(myCustomFont);

        searchListAdapter = new MovieSearchListAdapter(this);
        binding.includedLayout.moviesList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.includedLayout.moviesList.setAdapter(searchListAdapter);

        //TODO:[HiepHuynhVan] this handle has been deprecated, should be updated for a new code on this handle.
//        SnapHelper startSnapHelper = new PagerSnapHelper(position -> {
//            MovieEntity trailer = searchListAdapter.getItem(position);
//            updateBackground(trailer.getPosterPath());
//        });

        SnapHelper startSnapHelper = new PagerSnapHelper();

        binding.includedLayout.moviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int position = getCurrentItem();
                    MovieEntity trailer = searchListAdapter.getItem(position);
                    updateBackground(trailer.getPosterPath());
                }
            }
        });

        startSnapHelper.attachToRecyclerView(binding.includedLayout.moviesList);
        binding.includedLayout.moviesList.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), this));
    }

    private int getCurrentItem() {
        return ((LinearLayoutManager) binding.includedLayout.moviesList.getLayoutManager())
                .findFirstVisibleItemPosition();
    }

    private void initialiseViewModel() {
        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieSearchViewModel.class);
    }

    private void updateBackground(String url) {
        binding.overlayLayout.updateCurrentBackground(url);
    }

    private void querySearch(String text) {
        searchViewModel.searchMovie(text);
        searchViewModel.getMoviesLiveData().observe(this, resource -> {
            if (resource.isLoading()) {
                displayLoader();

            } else if (resource.data != null && !resource.data.isEmpty()) {
                handleSuccessResponse(resource.data);

            } else handleErrorResponse();
        });
    }

    private void handleSuccessResponse(List<MovieEntity> movies) {
        hideLoader();
        binding.includedLayout.emptyLayout.emptyContainer.setVisibility(View.GONE);
        binding.includedLayout.moviesList.setVisibility(View.VISIBLE);
        searchListAdapter.setItems(movies);
        new Handler().postDelayed(() -> {
            if (searchListAdapter.getItemCount() > 0) {
                updateBackground(searchListAdapter.getItem(0).getPosterPath());
            }

        }, 400);
    }

    private void handleErrorResponse() {
        hideLoader();
        binding.includedLayout.moviesList.setVisibility(View.GONE);
        binding.includedLayout.emptyLayout.emptyContainer.setVisibility(View.VISIBLE);
    }


    private void displayLoader() {
        binding.includedLayout.moviesList.setVisibility(View.GONE);
        binding.includedLayout.loaderLayout.rootView.setVisibility(View.VISIBLE);
        binding.includedLayout.loaderLayout.loader.start();
    }

    private void hideLoader() {
        binding.includedLayout.moviesList.setVisibility(View.VISIBLE);
        binding.includedLayout.loaderLayout.rootView.setVisibility(View.GONE);
        binding.includedLayout.loaderLayout.loader.stop();
    }

    @Override
    public void onItemClick(View parentView, View childView, int position) {
        searchViewModel.getMoviesLiveData().removeObservers(this);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                new Pair<>(childView.findViewById(R.id.image), TRANSITION_IMAGE_NAME));
        NavigationUtils.redirectToDetailScreen(this,
                searchListAdapter.getItem(position),
                options);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        AppUtils.closeKeyboard(this);
        querySearch(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
