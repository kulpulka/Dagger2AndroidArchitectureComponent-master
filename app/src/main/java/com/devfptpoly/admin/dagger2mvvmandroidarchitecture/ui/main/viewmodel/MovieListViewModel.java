package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.main.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.Resource;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.dao.MovieDao;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.entity.MovieEntity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.api.MovieApiService;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.repositories.MovieRepository;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class MovieListViewModel extends BaseViewModel {

    @Inject
    public MovieListViewModel(MovieDao movieDao, MovieApiService movieApiService) {
        movieRepository = new MovieRepository(movieDao, movieApiService);
    }

    private String type;
    private MovieRepository movieRepository;
    private MutableLiveData<Resource<List<MovieEntity>>> moviesLiveData = new MutableLiveData<>();

    public void setType(String type) {
        this.type = type;
    }

    public void loadMoreMovies(Long currentPage) {
        movieRepository.loadMoviesByType(currentPage, type)
                .doOnSubscribe(disposable -> addToDisposable(disposable))
                .subscribe(resource -> getMoviesLiveData().postValue(resource));
    }

    public boolean isLastPage() {
        return moviesLiveData.getValue() != null &&
                !moviesLiveData.getValue().data.isEmpty() ?
                moviesLiveData.getValue().data.get(0).isLastPage() :
                false;
    }

    public MutableLiveData<Resource<List<MovieEntity>>> getMoviesLiveData() {
        return moviesLiveData;
    }
}