package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.detail.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.dao.TvDao;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.entity.TvEntity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.api.TvApiService;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.repositories.TvRepository;

import javax.inject.Inject;

public class TvDetailViewModel extends ViewModel {

    @Inject
    public TvDetailViewModel(TvDao tvDao, TvApiService tvApiService) {
        tvRepository = new TvRepository(tvDao, tvApiService);
    }

    private TvRepository tvRepository;

    private MutableLiveData<TvEntity> tvDetailsLiveData = new MutableLiveData<>();


    public void fetchMovieDetail(TvEntity tvEntity) {
        tvRepository.fetchTvDetails(tvEntity.getId())
                .subscribe(resource -> {
                    if(resource.isLoaded()) getTvDetailsLiveData().postValue(resource.data);
                });
    }

    public MutableLiveData<TvEntity> getTvDetailsLiveData() {
        return tvDetailsLiveData;
    }
}
