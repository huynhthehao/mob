package vn.homecredit.hcvn.ui.offers;

import android.arch.lifecycle.MutableLiveData;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.api.ProfileResp;
import vn.homecredit.hcvn.data.model.offer.ContractOffer;
import vn.homecredit.hcvn.data.repository.OfferRepository;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class OfferViewModel extends BaseViewModel {

    private MutableLiveData<Boolean> modelOffer = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelExpired = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelNoVip = new MutableLiveData<>();
    private final OfferRepository offerRepository;
    private PreferencesHelper preferencesHelper;
    private ProfileResp.Offer offer;

    @Inject
    public OfferViewModel(OfferRepository offerRepository, PreferencesHelper preferencesHelper, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.offerRepository = offerRepository;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void init() {
        super.init();
    }

    public MutableLiveData<Boolean> getModelOffer() {
        return modelOffer;
    }

    public MutableLiveData<Boolean> getModelExpired() {
        return modelExpired;
    }

    public MutableLiveData<Boolean> getModelNoVip() {
        return modelNoVip;
    }

    public void initData(ProfileResp.Offer offer) {
        this.offer = offer;
        if (this.offer == null) {
            modelNoVip.setValue(true);
            return;
        }
        getContractOffer(this.offer.getCamId());

    }

    public void getContractOffer(String campId) {
        if (campId == null) {
            showMessage(R.string.error_system);
            return;
        }
        setIsLoading(true);
        offerRepository.contractOffer(campId).subscribe(contractOffer -> {
            setIsLoading(false);
            if (contractOffer.isSuccess()) {
                modelOffer.setValue(contractOffer.isActive());
                modelExpired.setValue(!contractOffer.isActive());
            }else {
                showMessage(contractOffer.getResponseMessage());
            }
        } , throwable -> {
            setIsLoading(false);
            handleError(throwable);
        });
    }
}
