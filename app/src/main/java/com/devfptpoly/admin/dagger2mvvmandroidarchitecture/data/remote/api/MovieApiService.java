package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.api;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.entity.MovieEntity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.model.CreditResponse;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.model.MovieApiResponse;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.model.ReviewApiResponse;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.model.VideoResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("movie/{type}?language=en-US&region=US")
    Observable<MovieApiResponse> fetchMoviesByType(@Path("type") String type,
                                                   @Query("page") long page);


    @GET("/3/movie/{movieId}")
    Observable<MovieEntity> fetchMovieDetail(@Path("movieId") String movieId);


    @GET("/3/movie/{movieId}/videos")
    Observable<VideoResponse> fetchMovieVideo(@Path("movieId") String movieId);

    @GET("/3/movie/{movieId}/credits")
    Observable<CreditResponse> fetchCastDetail(@Path("movieId") String movieId);


    @GET("/3/movie/{movieId}/similar")
    Observable<MovieApiResponse> fetchSimilarMovie(@Path("movieId") String movieId,
                                                   @Query("page") long page);


    @GET("/3/movie/{movieId}/reviews")
    Observable<ReviewApiResponse> fetchMovieReviews(@Path("movieId") String movieId);


    @GET("/3/search/movie")
    Observable<MovieApiResponse> searchMoviesByQuery(@Query("query") String query,
                                                     @Query("page") String page);
}
