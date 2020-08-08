package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.main.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.Resource;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.dao.TvDao;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.entity.TvEntity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.api.TvApiService;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.repositories.TvRepository;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class TvListViewModel extends BaseViewModel {

    @Inject
    public TvListViewModel(TvDao tvDao, TvApiService tvApiService) {
        tvRepository = new TvRepository(tvDao, tvApiService);
    }

    private String type;
    private TvRepository tvRepository;
    private MutableLiveData<Resource<List<TvEntity>>> tvsLiveData = new MutableLiveData<>();

    public void setType(String type) {
        this.type = type;
    }

    public void loadMoreTvs(Long currentPage) {
        tvRepository.loadTvsByType(currentPage, type)
                .doOnSubscribe(disposable -> addToDisposable(disposable))
                .subscribe(resource -> getTvsLiveData().postValue(resource));
    }

    public boolean isLastPage() {
        return tvsLiveData.getValue() != null &&
                !tvsLiveData.getValue().data.isEmpty() ?
                tvsLiveData.getValue().data.get(0).isLastPage() :
                false;
    }

    public MutableLiveData<Resource<List<TvEntity>>> getTvsLiveData() {
        return tvsLiveData;
    }
}
