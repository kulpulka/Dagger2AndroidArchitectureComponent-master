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
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.entity.TvEntity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.databinding.MoviesListFragmentBinding;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.factory.ViewModelFactory;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.base.BaseFragment;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.base.custom.recyclerview.RecyclerItemClickListener;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.base.custom.recyclerview.RecyclerViewPaginator;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.main.activity.MainActivity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.main.adapter.TvListAdapter;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.main.viewmodel.TvListViewModel;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.utils.NavigationUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TvListFragment extends BaseFragment implements RecyclerItemClickListener.OnRecyclerViewItemClickListener {

    @Inject
    ViewModelFactory viewModelFactory;

    TvListViewModel tvListViewModel;
    private TvListAdapter tvListAdapter;
    private MoviesListFragmentBinding binding;

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
        tvListAdapter = new TvListAdapter(activity);
        binding.moviesList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.moviesList.setAdapter(tvListAdapter);
        binding.moviesList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), this));

        //TODO[HiepHuynhVan] this code has been deprecated for the new version, shoule be changed for this code.
//        SnapHelper startSnapHelper = new PagerSnapHelper(position -> {
//            TvEntity movie = tvListAdapter.getItem(position);
//            ((MainActivity)activity).updateBackground(movie.getPosterPath());
//        });

        SnapHelper startSnapHelper = new PagerSnapHelper();


        binding.moviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int position = getCurrentItem();
                    TvEntity movie = tvListAdapter.getItem(position);
                    ((MainActivity) activity).updateBackground(movie.getPosterPath());
                }
            }
        });


        startSnapHelper.attachToRecyclerView(binding.moviesList);

        binding.moviesList.addOnScrollListener(new RecyclerViewPaginator(binding.moviesList) {
            @Override
            public boolean isLastPage() {
                return tvListViewModel.isLastPage();
            }

            @Override
            public void loadMore(Long page) {
                tvListViewModel.loadMoreTvs(page);
            }

            @Override
            public void loadFirstData(Long page) {
                displayLoader();
                tvListViewModel.loadMoreTvs(page);
            }
        });
    }

    private int getCurrentItem() {
        return ((LinearLayoutManager) binding.moviesList.getLayoutManager())
                .findFirstVisibleItemPosition();
    }

    private void initialiseViewModel() {
        tvListViewModel = ViewModelProviders.of(this, viewModelFactory).get(TvListViewModel.class);
        tvListViewModel.setType(MENU_TV_ITEM.get(getArguments() == null ? 0 : getArguments().getInt(INTENT_CATEGORY)));
        tvListViewModel.getTvsLiveData().observe(this, resource -> {
            if (resource.isLoading()) {

            } else if (!resource.data.isEmpty()) {
                updateTvsList(resource.data);

            } else handleErrorResponse();
        });
    }

    private void updateTvsList(List<TvEntity> movies) {
        hideLoader();
        binding.emptyLayout.emptyContainer.setVisibility(View.GONE);
        binding.moviesList.setVisibility(View.VISIBLE);
        tvListAdapter.setItems(movies);
//        new Handler().postDelayed(() -> {
//            if(tvListAdapter.getItemCount() > 0) {
//                ((MainActivity) activity).updateBackground(tvListAdapter.getItem(0).getPosterPath());
//            }
//
//        }, 400);
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
        tvListViewModel.onStop();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                new Pair<>(childView.findViewById(R.id.image), TRANSITION_IMAGE_NAME));
        NavigationUtils.redirectToTvDetailScreen(requireActivity(),
                tvListAdapter.getItem(position),
                options);
    }
}
