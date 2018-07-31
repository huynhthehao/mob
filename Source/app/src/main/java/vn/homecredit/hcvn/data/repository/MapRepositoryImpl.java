package vn.homecredit.hcvn.data.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.model.mapdata.payoo.PayooData;
import vn.homecredit.hcvn.data.remote.ApiHeader;
import vn.homecredit.hcvn.data.remote.payoo.PayooRestService;

public class MapRepositoryImpl implements MapRepository {
    private ApiHeader mApiHeader;
    private PayooRestService payooRestService;

    @Inject
    public MapRepositoryImpl(PayooRestService payooRestService, ApiHeader apiHeader) {
        this.mApiHeader = apiHeader;
        this.payooRestService = payooRestService;
    }

    @Override
    public Single<List<PayooData>> getPayooNear(Double lat, Double lon) {
        return payooRestService.getPayooNear(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
