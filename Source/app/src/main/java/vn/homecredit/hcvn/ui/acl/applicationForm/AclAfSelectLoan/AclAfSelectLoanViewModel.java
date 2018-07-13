package vn.homecredit.hcvn.ui.acl.applicationForm.AclAfSelectLoan;

import android.databinding.ObservableField;

import com.annimon.stream.Stream;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.RxSeekBar;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import vn.homecredit.hcvn.data.DataManager;
import vn.homecredit.hcvn.data.acl.AclDataManager;
import vn.homecredit.hcvn.data.model.api.acl.CashLoanResponseCode;
import vn.homecredit.hcvn.data.model.api.acl.ProposeOfferResp;
import vn.homecredit.hcvn.data.model.api.acl.SuggestOfferResp;
import vn.homecredit.hcvn.data.model.message.MessageQuestion;
import vn.homecredit.hcvn.ui.acl.base.AclBaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class AclAfSelectLoanViewModel extends AclBaseViewModel<AclAfSelectLoanNavigator> {

    public static final int CURRENT_STEP = 2;
    private static final long SEEKBAR_THROTTLE_INTERVAL = 500;

    private ProposeOfferResp.ProposeOfferRespData mProposeOffer;
    private SuggestOfferResp.SuggestOfferRespData mSuggestOffer;

    private Double mLoanAmount;
    public ObservableField<String> FormattedLoanAmount = new ObservableField<>("");
    private int mTenor;

    private List<SegmentedElement> mAmountValues;
    private List<SegmentedElement> mTenorValues;
    public ObservableField<Integer> MaxAmount = new ObservableField<>(1);
    public ObservableField<Integer> MaxTenor = new ObservableField<>(1);

    public ObservableField<String> MinimumLoanAmountDisplayValue = new ObservableField<>("");
    public ObservableField<String> MaximumLoanAmountDisplayValue = new ObservableField<>("");

    public ObservableField<String> MinimumLoanTenorDisplayValue = new ObservableField<>("");
    public ObservableField<String> MaximumLoanTenorDisplayValue = new ObservableField<>("");

    public InitialValueObservable<Integer> AmountSliderChangedSubject;

    @Inject
    public AclAfSelectLoanViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, AclDataManager aclDataManager) {
        super(dataManager, schedulerProvider, aclDataManager, CURRENT_STEP);
    }

    public void init() {
        getCompositeDisposable().add(getAclDataManager()
                .getSuggestOffer()
                .doOnSuccess(response -> {

                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    processSuggestOffer(response);

                }, throwable -> {
                    setIsLoading(false);
                }));

        getCompositeDisposable().add(AmountSliderChangedSubject
                .doOnNext((value) -> {

                })
                .debounce(SEEKBAR_THROTTLE_INTERVAL, TimeUnit.MILLISECONDS)
                .toFlowable(BackpressureStrategy.LATEST)
                .observeOn(getSchedulerProvider().ui())
                .subscribe((value) -> {
                    setLoanAmount((Double) mAmountValues.get(value).getValue());
                }, throwable -> {
//                    setIsLoading(false);
                }));
    }

    private void processSuggestOffer(SuggestOfferResp resp) {
        switch (CashLoanResponseCode.parseFromIntValue(resp.getResponseCode())) {
            case APPLICATION_NOT_FOUND:
                String message = String.format("%s %s", resp.getResponseMessage(), "Bạn có chắc chắn muốn thoát?");
                sendMessage(new MessageQuestion("Warning", message, (yes) -> {
                    if (yes) {
                        getNavigator().popToRoot();
                    } else {
                        getNavigator().goToAclValidation();
                    }
                }));
                break;
            case SUCCESSFUL:
                mSuggestOffer = resp.getData();
                setup();
                break;
            default:
                break;
        }
    }

    public void setAmountValues(List<SegmentedElement> amountValues) {
        mAmountValues = amountValues;
        MaxAmount.set(amountValues.size() - 1);

        MinimumLoanAmountDisplayValue.set(amountValues.get(0).getDisplayValue());
        MaximumLoanAmountDisplayValue.set(amountValues.get(MaxAmount.get()).getDisplayValue());
    }

    public void setTenorValues(List<SegmentedElement> tenorValues) {
        mTenorValues = tenorValues;
        MaxTenor.set(tenorValues.size() - 1);

        MinimumLoanTenorDisplayValue.set(tenorValues.get(0).getDisplayValue());
        MaximumLoanTenorDisplayValue.set(tenorValues.get(MaxTenor.get()).getDisplayValue());
    }

    public Double getLoanAmount() {
        return mLoanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        mLoanAmount = loanAmount;
        FormattedLoanAmount.set(String.format("%.0f d", mLoanAmount));
    }

    public int getTenor() {
        return mTenor;
    }

    public void setTenor(int tenor) {
        mTenor = tenor;
    }

    public class SegmentedElement {
        public String getDisplayValue() {
            return mDisplayValue;
        }

        public Object getValue() {
            return mValue;
        }

        public String mDisplayValue;
        public Object mValue;

        public SegmentedElement(String displayValue, Object value) {
            mDisplayValue = displayValue;
            mValue = value;
        }
    }

    private void setup() {
        if (mSuggestOffer.getLoanTenors() != null && mSuggestOffer.getLoanTenors().size() > 0) {
            List<Double> amount = Stream.of(mSuggestOffer.getLoanTenors()).map(x -> x.getAmount()).distinct().toList();
            Collections.sort(amount);
            setAmountValues(Stream.of(amount).map(x -> new SegmentedElement(String.format("%.0f d", x), x)).toList());
            List<Integer> tenors = Stream.of(mSuggestOffer.getLoanTenors()).flatMap(x -> Stream.of(x.getTenorProducts()))
                    .groupBy(xx -> xx.getTenor())
                    .map(xxx -> xxx.getKey())
                    .distinct().sorted().toList();
            setTenorValues(Stream.of(tenors).map(x -> new SegmentedElement(String.format("%s tháng", x), x)).toList());
//            var amount =  mSuggestOffer.getLoanTenors.toList().Select(x => x.Amount).Distinct().ToList();
//            amount.Sort();
//            AmountValues = new ObservableCollection<SegmentedElement>(amount.Select(x => new SegmentedElement
//            {
//                DisplayValue = Helper.GetCurrencyFormatDisplay(x, currency: "₫"),
//                Value = x
//            }));
//            var tenors = SuggestOffer.LoanTenors.SelectMany(x => x.TenorProducts)
//                                         .GroupBy(xx => xx.Tenor)
//                                         .Select(xxx => xxx.Key)
//                                         .Distinct().OrderBy(x => x).ToList();
//
//            TenorValues = new ObservableCollection<SegmentedElement>(tenors.Select(x => new SegmentedElement
//            {
//                DisplayValue = $"{x.ToString()} {AppResources.CLWOCommon_Month}" + (App.EngLishLocale ? "s" : string.Empty),
//                Value = x
//            }));
//
//            CheckInvalidOfferCache();
//
//            //set default selection
//            LoanAmount = Data.Current.ApplicationInfo?.ProposeOffer?.RequiredAmount ?? Convert.ToDecimal(AmountValues.Last().Value);
//            SelectedLoanAmountIndex = AmountValues.ToList().FindIndex(_ => _.Value.Equals(LoanAmount));
//            SetupInvalidAmounts();
//            //Get monthly payment
//            GetMonthlyPayment();
        }
    }
}
