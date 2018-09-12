package vn.homecredit.hcvn.ui.offers;

import android.arch.lifecycle.MutableLiveData;

import javax.inject.Inject;

import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.offer.ContractOfferData;
import vn.homecredit.hcvn.data.repository.OfferRepository;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.ui.notification.model.OfferModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class OfferViewModel extends BaseViewModel {

    private MutableLiveData<Boolean> modelOffer = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelExpired = new MutableLiveData<>();
    private MutableLiveData<Boolean> modelNoVip = new MutableLiveData<>();
    private final OfferRepository offerRepository;
    private PreferencesHelper preferencesHelper;
    private OfferModel offer;

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

    public OfferModel getOffer() {
        return offer;
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

    public void initData(OfferModel offer) {
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
        offerRepository.contractOffer(campId).subscribe(contractOfferResp -> {
            setIsLoading(false);
            if (contractOfferResp.isSuccess()) {
                ContractOfferData contractOfferData = contractOfferResp.getData();
                if (contractOfferData != null && contractOfferData.isActive()) {
                   modelOffer.setValue(true);
                }else {
                    modelNoVip.setValue(true);
                }
            }else {
                showMessage(contractOfferResp.getResponseMessage());
            }
        } , throwable -> {
            setIsLoading(false);
            handleError(throwable);
        });
    }
}
