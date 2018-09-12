package vn.homecredit.hcvn.data.repository;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.model.api.HcApiException;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.offer.ContractOffer;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;

import static vn.homecredit.hcvn.data.model.api.HcApiException.ERROR_CODE_INTERNAL;

public class OfferRepository {

    private RestService restService;
    private PreferencesHelper preferencesHelper;

    @Inject
    public OfferRepository(RestService restService, PreferencesHelper preferencesHelper) {
        this.restService = restService;
        this.preferencesHelper = preferencesHelper;
    }

    public Single<ContractOffer> contractOffer(String campId) {
        return restService.contractOffer(campId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
