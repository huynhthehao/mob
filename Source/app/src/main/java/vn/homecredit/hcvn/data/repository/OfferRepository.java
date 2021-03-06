package vn.homecredit.hcvn.data.repository;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.model.offer.ContractOfferResp;
import vn.homecredit.hcvn.data.model.offer.OfferDetailResp;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;

public class OfferRepository {

    private RestService restService;
    private PreferencesHelper preferencesHelper;

    @Inject
    public OfferRepository(RestService restService, PreferencesHelper preferencesHelper) {
        this.restService = restService;
        this.preferencesHelper = preferencesHelper;
    }

    public Single<ContractOfferResp> contractOffer(String campId) {
        return restService.contractOffer(campId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Single<OfferDetailResp> offerFormula(String riskGroup, String productCode) {
        return restService.offerFormula(riskGroup, productCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

}
