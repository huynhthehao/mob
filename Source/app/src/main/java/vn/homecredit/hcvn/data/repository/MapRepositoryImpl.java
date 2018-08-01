package vn.homecredit.hcvn.data.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.schedulers.SchedulerWhen;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.model.mapdata.model.clw.ClwModel;
import vn.homecredit.hcvn.data.model.mapdata.model.disbursement.DisbursementModel;
import vn.homecredit.hcvn.data.model.mapdata.model.payment.PaymentModel;
import vn.homecredit.hcvn.data.model.mapdata.model.payoo.PayooData;
import vn.homecredit.hcvn.data.model.mapdata.model.pos.PosModel;
import vn.homecredit.hcvn.data.remote.ApiHeader;
import vn.homecredit.hcvn.data.remote.clwmap.ClwMapService;
import vn.homecredit.hcvn.data.remote.disbursement.DisbursementService;
import vn.homecredit.hcvn.data.remote.payment.PaymentService;
import vn.homecredit.hcvn.data.remote.payoo.PayooRestService;
import vn.homecredit.hcvn.data.remote.pos.PosRestService;

public class MapRepositoryImpl implements MapRepository {
    private ApiHeader mApiHeader;
    private PayooRestService mPayooRestService;
    private PosRestService mPosRestService;
    private ClwMapService mClwMapService;
    private DisbursementService mDisbursementService;
    private PaymentService mPaymentService;

    @Inject
    public MapRepositoryImpl(PayooRestService payooRestService, PosRestService posRestService, ClwMapService clwMapService, DisbursementService disbursementService, PaymentService paymentService, ApiHeader apiHeader) {
        mApiHeader = apiHeader;
        mPayooRestService = payooRestService;
        mPosRestService = posRestService;
        mClwMapService = clwMapService;
        mDisbursementService = disbursementService;
        mPaymentService = paymentService;
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

    @Override
    public Single<ClwModel> getClwModelNear(Double lat, Double lon) {
        return mClwMapService.getClwNear(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<DisbursementModel> getDisbursementModelNear(Double lat, Double lon) {
        return mDisbursementService.getDisbursementNear(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<PaymentModel> getPaymentModelNear(Double lat, Double lon) {
        return mPaymentService.getDisbursementNear(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
