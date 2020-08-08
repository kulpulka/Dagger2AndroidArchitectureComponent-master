package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.detail.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.dao.MovieDao;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.entity.MovieEntity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.api.MovieApiService;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.repositories.MovieRepository;

import javax.inject.Inject;

public class MovieDetailViewModel extends ViewModel {

    @Inject
    public MovieDetailViewModel(MovieDao movieDao, MovieApiService movieApiService) {
        movieRepository = new MovieRepository(movieDao, movieApiService);
    }

    private MovieRepository movieRepository;

    private MutableLiveData<MovieEntity> movieDetailsLiveData = new MutableLiveData<>();

    public void fetchMovieDetail(MovieEntity movieEntity) {
        movieRepository.fetchMovieDetails(movieEntity.getId())
                .subscribe(resource -> {
                    if(resource.isLoaded()) getMovieDetailsLiveData().postValue(resource.data);
                });
    }

    public MutableLiveData<MovieEntity> getMovieDetailsLiveData() {
        return movieDetailsLiveData;
    }
}
