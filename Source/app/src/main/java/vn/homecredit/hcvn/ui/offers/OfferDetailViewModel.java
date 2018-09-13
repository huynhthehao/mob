package vn.homecredit.hcvn.ui.offers;

import android.databinding.ObservableField;

import java.text.NumberFormat;
import java.util.List;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.model.offer.OfferDetailData;
import vn.homecredit.hcvn.helpers.prefs.PreferencesHelper;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.CommonUtils;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class OfferDetailViewModel extends BaseViewModel {
    private final PreferencesHelper prefHelper;

    private double rate;
    private ObservableField<OfferDetailData> offerDetail = new ObservableField<>();
    private ObservableField<String> tallNumber = new ObservableField<>("");
    private ObservableField<Integer> minLoan = new ObservableField<Integer>(offerDetail) {
        @Override
        public Integer get() {
            OfferDetailData data = offerDetail.get();
            if (data == null)
                return 0;
            return data.getOfferModel().getMinLoanAmount() / 1000000;
        }
    };
    private ObservableField<Integer> maxLoan = new ObservableField<Integer>(offerDetail) {
        @Override
        public Integer get() {
            OfferDetailData data = offerDetail.get();
            if (data == null)
                return 0;
            return data.getOfferModel().getMaxLoanAmount() / 1000000;
        }
    };
    private ObservableField<Integer> totalMonth = new ObservableField<Integer>(offerDetail) {
        @Override
        public Integer get() {
            if (super.get() != null)
                return super.get();
            OfferDetailData data = offerDetail.get();
            if (data == null)
                return 0;
            List<OfferDetailData.Rate> rates = data.getRateList();
            OfferDetailData.Rate maxRate = rates.get(rates.size() - 1);
            rate = (double) maxRate.interest / 100 / 12;
            set(Integer.parseInt(maxRate.month));
            return super.get();
        }
    };
    private ObservableField<Integer> loanAmount = new ObservableField<>(0);
    private ObservableField<String> paymentPerMonth = new ObservableField<String>(loanAmount) {
        @Override
        public String get() {
            OfferDetailData data = offerDetail.get();
            if (data == null)
                return "";
            NumberFormat nf = CommonUtils.getDefaultNumberFormmater();
            double denominator = Math.pow(1 + rate, totalMonth.get()) - 1;
            return nf.format((rate + (rate / denominator)) * loanAmount.get());
        }
    };

    @Inject
    public OfferDetailViewModel(PreferencesHelper preferencesHelper, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.prefHelper = preferencesHelper;
    }

    @Override
    public void init() {
        super.init();
        tallNumber.set(prefHelper.getVersionRespData().getCustomerSupportPhone());
    }

    public ObservableField<String> getTallNumber() {
        return tallNumber;
    }

    public ObservableField<OfferDetailData> getOfferDetail() {
        return offerDetail;
    }

    public ObservableField<Integer> getMinLoan() {
        return minLoan;
    }

    public ObservableField<Integer> getMaxLoan() {
        return maxLoan;
    }

    public ObservableField<Integer> getTotalMonth() {
        return totalMonth;
    }

    public ObservableField<Integer> getLoanAmount() {
        return loanAmount;
    }

    public ObservableField<String> getPaymentPerMonth() {
        return paymentPerMonth;
    }
}
