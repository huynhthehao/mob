package vn.homecredit.hcvn.data.repository;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.model.mapdata.model.clw.ClwModel;
import vn.homecredit.hcvn.data.model.mapdata.model.disbursement.DisbursementModel;
import vn.homecredit.hcvn.data.model.mapdata.model.payment.PaymentModel;
import vn.homecredit.hcvn.data.remote.RestService;

public class MapRepositoryImpl implements MapRepository {

    private RestService restService;

    @Inject
    public MapRepositoryImpl(RestService restService) {
        this.restService = restService;
    }

    @Override
    public Single<ClwModel> getClwModelNear(Double lat, Double lon) {
        return restService.getClwNear(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<DisbursementModel> getDisbursementModelNear(Double lat, Double lon) {
        return restService.getDisbursementNear(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<PaymentModel> getPaymentModelNear(Double lat, Double lon) {
        return restService.getPaymenttNear(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
