package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
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
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.databinding.MoviesListFragmentBinding;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.factory.ViewModelFactory;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.base.BaseFragment;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.base.custom.recyclerview.RecyclerItemClickListener;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.base.custom.recyclerview.RecyclerViewPaginator;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.main.activity.MainActivity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.main.adapter.MoviesListAdapter;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.main.viewmodel.MovieListViewModel;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.utils.NavigationUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class MovieListFragment extends BaseFragment implements RecyclerItemClickListener.OnRecyclerViewItemClickListener {

    @Inject
    ViewModelFactory viewModelFactory;

    MovieListViewModel moviesListViewModel;
    private MoviesListFragmentBinding binding;
    private MoviesListAdapter moviesListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        initialiseViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialiseView();
    }

    private void initialiseView() {
        moviesListAdapter = new MoviesListAdapter(activity);
        binding.moviesList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.moviesList.setAdapter(moviesListAdapter);
        binding.moviesList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), this));

        /* SnapHelper to change the background of the activity based on the list item
         * currently visible */

        //TODO:[HiepHuynhVan] this handle has been deprecated, should be changed for the new update.
//        SnapHelper startSnapHelper = new PagerSnapHelper(position -> {
//            MovieEntity movie = moviesListAdapter.getItem(position);
//            ((MainActivity)activity).updateBackground(movie.getPosterPath());
//        });

        SnapHelper startSnapHelper = new PagerSnapHelper();

        binding.moviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int position = getCurrentItem();
                    MovieEntity movie = moviesListAdapter.getItem(position);
                    ((MainActivity) activity).updateBackground(movie.getPosterPath());
                }
            }
        });

        startSnapHelper.attachToRecyclerView(binding.moviesList);

        /* RecyclerViewPaginator to handle pagination */
        binding.moviesList.addOnScrollListener(new RecyclerViewPaginator(binding.moviesList) {
            @Override
            public boolean isLastPage() {
                return moviesListViewModel.isLastPage();
            }

            @Override
            public void loadMore(Long page) {
                moviesListViewModel.loadMoreMovies(page);
            }

            @Override
            public void loadFirstData(Long page) {
                displayLoader();
                moviesListViewModel.loadMoreMovies(page);
            }
        });
    }

    private int getCurrentItem() {
        return ((LinearLayoutManager) binding.moviesList.getLayoutManager())
                .findFirstVisibleItemPosition();
    }

    private void initialiseViewModel() {
        moviesListViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel.class);
        moviesListViewModel.setType(MENU_MOVIE_ITEM.get(getArguments() == null ? 0 : getArguments().getInt(INTENT_CATEGORY)));

        moviesListViewModel.getMoviesLiveData().observe(this, resource -> {
            if (resource.isLoading()) {

            } else if (!resource.data.isEmpty()) {
                updateMoviesList(resource.data);

            } else handleErrorResponse();
        });
    }

    private void updateMoviesList(List<MovieEntity> movies) {
        hideLoader();
        binding.emptyLayout.emptyContainer.setVisibility(View.GONE);
        binding.moviesList.setVisibility(View.VISIBLE);
        moviesListAdapter.setItems(movies);
    }


    private void handleErrorResponse() {
        hideLoader();
        binding.moviesList.setVisibility(View.GONE);
        binding.emptyLayout.emptyContainer.setVisibility(View.VISIBLE);
        ((MainActivity) activity).clearBackground();
    }


    private void displayLoader() {
        binding.moviesList.setVisibility(View.GONE);
        binding.loaderLayout.rootView.setVisibility(View.VISIBLE);
        binding.loaderLayout.loader.start();
        ((MainActivity) activity).hideToolbar();
    }

    private void hideLoader() {
        binding.moviesList.setVisibility(View.VISIBLE);
        binding.loaderLayout.rootView.setVisibility(View.GONE);
        binding.loaderLayout.loader.stop();
        ((MainActivity) activity).displayToolbar();
    }

    @Override
    public void onItemClick(View parentView, View childView, int position) {
        moviesListViewModel.onStop();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                new Pair<>(childView.findViewById(R.id.image), TRANSITION_IMAGE_NAME));
        NavigationUtils.redirectToDetailScreen(requireActivity(),
                moviesListAdapter.getItem(position),
                options);
    }
}
