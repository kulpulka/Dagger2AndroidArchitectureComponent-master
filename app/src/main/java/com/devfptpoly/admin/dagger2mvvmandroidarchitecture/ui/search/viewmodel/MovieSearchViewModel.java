package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.search.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.Resource;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.dao.MovieDao;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.entity.MovieEntity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.api.MovieApiService;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.repositories.MovieRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieSearchViewModel extends ViewModel {

    @Inject
    public MovieSearchViewModel(MovieDao movieDao, MovieApiService movieApiService) {
        movieRepository = new MovieRepository(movieDao, movieApiService);
    }

    private MovieRepository movieRepository;

    private MutableLiveData<Resource<List<MovieEntity>>> moviesLiveData = new MutableLiveData<>();

    public void searchMovie(String text) {
        movieRepository.searchMovies(1l, text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resource -> getMoviesLiveData().postValue(resource));
    }

    public MutableLiveData<Resource<List<MovieEntity>>> getMoviesLiveData() {
        return moviesLiveData;
    }
}
