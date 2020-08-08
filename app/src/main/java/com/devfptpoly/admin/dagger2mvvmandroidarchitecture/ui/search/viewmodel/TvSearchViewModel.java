package com.devfptpoly.admin.dagger2mvvmandroidarchitecture.ui.search.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.Resource;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.dao.TvDao;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.local.entity.TvEntity;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.remote.api.TvApiService;
import com.devfptpoly.admin.dagger2mvvmandroidarchitecture.data.repositories.TvRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TvSearchViewModel extends ViewModel {

    @Inject
    public TvSearchViewModel(TvDao tvDao, TvApiService tvApiService) {
        tvRepository = new TvRepository(tvDao, tvApiService);
    }

    private TvRepository tvRepository;

    private MutableLiveData<Resource<List<TvEntity>>> tvsLiveData = new MutableLiveData<>();

    public void searchTv(String text) {
        tvRepository.searchTvs(1l, text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resource -> getTvsLiveData().postValue(resource));
    }

    public MutableLiveData<Resource<List<TvEntity>>> getTvsLiveData() {
        return tvsLiveData;
    }
}
