package vn.homecredit.hcvn.ui.offers;

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

    private final OfferRepository offerRepository;
    private PreferencesHelper preferencesHelper;
    private String campId;

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

    public void setCampId(String campId) {
        if (campId == null) {
            this.campId = getCampIdFromProfile();
        }else {
            this.campId = campId;
        }
        getContractOffer(this.campId);
    }

    public void getContractOffer(String campId) {
        if (campId == null) {
            showMessage(R.string.error_system);
            return;
        }
        setIsLoading(true);
        offerRepository.contractOffer(campId).subscribe(contractOffer -> {
                    setIsLoading(false);
                } , throwable -> {
                    setIsLoading(false);
                    handleError(throwable);
        });
    }

    private String getCampIdFromProfile() throws NullPointerException{
        if (preferencesHelper.getProfile() == null ||
                preferencesHelper.getProfile().getOffer() == null ||
                preferencesHelper.getProfile().getOffer().getCamId() == null)
            return null;

        return preferencesHelper.getProfile().getOffer().getCamId();
    }
}
