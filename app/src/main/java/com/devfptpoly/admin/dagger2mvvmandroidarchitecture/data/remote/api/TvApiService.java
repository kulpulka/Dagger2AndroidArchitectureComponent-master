package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.api;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.entity.TvEntity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.model.CreditResponse;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.model.ReviewApiResponse;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.model.TvApiResponse;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.model.VideoResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvApiService {

    @GET("tv/{type}?language=en-US&region=US")
    Observable<TvApiResponse> fetchTvListByType(@Path("type") String type,
                                                @Query("page") long page);


    @GET("/3/tv/{tvId}")
    Observable<TvEntity> fetchTvDetail(@Path("tvId") String tvId);


    @GET("/3/tv/{tvId}/videos")
    Observable<VideoResponse> fetchTvVideo(@Path("tvId") String tvId);

    @GET("/3/tv/{tvId}/credits")
    Observable<CreditResponse> fetchCastDetail(@Path("tvId") String tvId);


    @GET("/3/tv/{tvId}/similar")
    Observable<TvApiResponse> fetchSimilarTvList(@Path("tvId") String tvId,
                                                 @Query("page") long page);

    @GET("/3/tv/{tvId}/reviews")
    Observable<ReviewApiResponse> fetchTvReviews(@Path("tvId") String tvId);


    @GET("/3/search/tv")
    Observable<TvApiResponse> searchTvsByQuery(@Query("query") String query,
                                               @Query("page") String page);
}
