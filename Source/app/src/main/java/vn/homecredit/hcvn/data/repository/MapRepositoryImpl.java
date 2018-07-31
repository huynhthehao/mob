package vn.homecredit.hcvn.data.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.model.mapdata.model.payoo.PayooData;
import vn.homecredit.hcvn.data.model.mapdata.model.pos.PosModel;
import vn.homecredit.hcvn.data.remote.ApiHeader;
import vn.homecredit.hcvn.data.remote.payoo.PayooRestService;
import vn.homecredit.hcvn.data.remote.pos.PosRestService;

public class MapRepositoryImpl implements MapRepository {
    private ApiHeader mApiHeader;
    private PayooRestService mPayooRestService;
    private PosRestService mPosRestService;

    @Inject
    public MapRepositoryImpl(PayooRestService mPayooRestService, PosRestService posRestService, ApiHeader apiHeader) {
        this.mApiHeader = apiHeader;
        this.mPayooRestService = mPayooRestService;
        this.mPosRestService = posRestService;
    }

    @Override
    public Single<List<PayooData>> getPayooNear(Double lat, Double lon) {
        return mPayooRestService.getPayooNear(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<PosModel> getPosNear(Double lat, Double lon) {
        return mPosRestService.getPosNear(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<PosModel> getVnPosNear(Double lat, Double lon) {
        return mPosRestService.getVnPosNear(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
