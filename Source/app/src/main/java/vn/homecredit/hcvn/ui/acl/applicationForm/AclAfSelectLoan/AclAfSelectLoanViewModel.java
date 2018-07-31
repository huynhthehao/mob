/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/17/18 3:22 PM, by Hien.NguyenM
 */

package vn.homecredit.hcvn.ui.acl.applicationForm.AclAfSelectLoan;

import android.databinding.ObservableField;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.jakewharton.rxbinding2.InitialValueObservable;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import vn.homecredit.hcvn.data.acl.AclDataManager;
import vn.homecredit.hcvn.data.model.api.acl.CashLoanResponseCode;
import vn.homecredit.hcvn.data.model.api.acl.ProposeOfferResp;
import vn.homecredit.hcvn.data.model.api.acl.SuggestOfferResp;
import vn.homecredit.hcvn.ui.acl.base.AclBaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class AclAfSelectLoanViewModel extends AclBaseViewModel<AclAfSelectLoanNavigator> {

    public static final int CURRENT_STEP = 2;
    private static final long SEEKBAR_THROTTLE_INTERVAL = 500;

    private ProposeOfferResp.ProposeOfferRespData mProposeOffer;
    private SuggestOfferResp.SuggestOfferRespData mSuggestOffer;

    private Double mLoanAmount;
    public ObservableField<Integer> SelectedLoanAmountIndex = new ObservableField<>(0);
    public ObservableField<String> FormattedLoanAmount = new ObservableField<>("");

    private int mTenor;
    public ObservableField<Integer> SelectedTenorIndex = new ObservableField<>(0);
    public ObservableField<String> FormattedTenor = new ObservableField<>("");

    private List<SegmentedElement> mAmountValues;
    private List<SegmentedElement> mTenorValues;

    public ObservableField<Integer> MaxAmount = new ObservableField<>(1);
    public ObservableField<Integer> MaxTenor = new ObservableField<>(1);

    public ObservableField<String> MinimumLoanAmountDisplayValue = new ObservableField<>("");
    public ObservableField<String> MaximumLoanAmountDisplayValue = new ObservableField<>("");

    public ObservableField<String> MinimumLoanTenorDisplayValue = new ObservableField<>("");
    public ObservableField<String> MaximumLoanTenorDisplayValue = new ObservableField<>("");

    public InitialValueObservable<Integer> AmountSliderChangedSubject;
    public InitialValueObservable<Integer> TenorSliderChangedSubject;

    public ObservableField<Boolean> RetryGetMonthlyPayment = new ObservableField<>(false);
    public ObservableField<Boolean> IsBusyLoadingMonthlyPayment = new ObservableField<>(false);
    public ObservableField<Boolean> ShowButton = new ObservableField<>(false);

    //    public ObservableField<Integer> MonthlyPayment = new ObservableField<>(0);
    private int mMonthlyPayment = 0;
    public ObservableField<String> FormattedMonthlyPayment = new ObservableField<>("");

    @Inject
    public AclAfSelectLoanViewModel(SchedulerProvider schedulerProvider, AclDataManager aclDataManager) {
        super(schedulerProvider, aclDataManager, CURRENT_STEP);
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
                    SegmentedElement segmentedElement = mAmountValues.get(value);
                    setLoanAmount((Double) segmentedElement.getValue());

                    setupInvalidAmounts();
                    updateMonthlyPayment();
                }, throwable -> {
                    showMessage(throwable.getMessage());
                }));

        getCompositeDisposable().add(TenorSliderChangedSubject
                .doOnNext((value) -> {

                })
                .debounce(SEEKBAR_THROTTLE_INTERVAL, TimeUnit.MILLISECONDS)
                .toFlowable(BackpressureStrategy.LATEST)
                .observeOn(getSchedulerProvider().ui())
                .subscribe((value) -> {
                    SegmentedElement segmentedElement = mTenorValues.get(value);
                    setTenor((Integer) segmentedElement.getValue());

                    setupInvalidAmounts();
                    updateMonthlyPayment();
                }, throwable -> {
                    showMessage(throwable.getMessage());
                }));
    }

    private void processSuggestOffer(SuggestOfferResp resp) {
        switch (CashLoanResponseCode.parseFromIntValue(resp.getResponseCode())) {
            case APPLICATION_NOT_FOUND:
                String message = String.format("%s %s", resp.getResponseMessage(), "Bạn có chắc chắn muốn thoát?");
                showConfirmMessage("Warning", message, (yes) -> {
                    if (yes) {
                        getNavigator().popToRoot();
                    } else {
                        getNavigator().goToAclValidation();
                    }
                });

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
        FormattedLoanAmount.set(String.format("%,dđ", loanAmount.intValue()));
    }

    public int getTenor() {
        return mTenor;
    }

    public void setTenor(int tenor) {
        mTenor = tenor;
        FormattedTenor.set(String.format("%d tháng", tenor));
    }

    public int getMonthlyPayment() {
        return mMonthlyPayment;
    }

    public void setMonthlyPayment(int monthlyPayment) {
        mMonthlyPayment = monthlyPayment;
        FormattedMonthlyPayment.set(String.format("%,dđ ", monthlyPayment));
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
            setAmountValues(Stream.of(amount).map(x -> new SegmentedElement(String.format("%,dđ", x.intValue()), x)).toList());
            List<Integer> tenors = Stream.of(mSuggestOffer.getLoanTenors())
                    .flatMap(x -> Stream.of(x.getTenorProducts()))
                    .groupBy(xx -> xx.getTenor())
                    .map(xxx -> xxx.getKey())
                    .distinct().sorted().toList();
            setTenorValues(Stream.of(tenors).map(x -> new SegmentedElement(String.format("%s tháng", x), x)).toList());

            checkInvalidOfferCache();
//
//            //set default selection

            setLoanAmount((Double) Stream.of(mAmountValues).findLast().get().getValue());
            int selectedAmountIndex = Stream.of(mAmountValues).map(x -> x.getValue()).toList().indexOf(mLoanAmount);
            SelectedLoanAmountIndex.set(selectedAmountIndex);
            setupInvalidAmounts();
            //Get monthly payment
            updateMonthlyPayment();
        }
    }

    private void updateMonthlyPayment() {
        RetryGetMonthlyPayment.set(false);
        if (mLoanAmount == null) return;

        BusyLoading(true);

        Double requestLoanAmount = mLoanAmount;
        int requestLoanTenor = mTenor;

        //TODO: Load From cache
        ProposeOfferResp.ProposeOfferRespData propose = null;//Load cache instead of null

        if (propose == null) {
            String productCode = getCurrentProductCode();
            getCompositeDisposable().add(getAclDataManager()
                    .getMonthlyPaymentAsync(requestLoanAmount, requestLoanTenor, mSuggestOffer.getBoundScore(), productCode)
                    .doOnSuccess(response -> {

                    })
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        ProposeOfferResp.ProposeOfferRespData p = response.getData();
                        updateWithProposeOffer(p, requestLoanAmount, requestLoanTenor);
                    }, throwable -> {
                        BusyLoading(false);
                    }));
        } else {
            updateWithProposeOffer(propose, requestLoanAmount, requestLoanTenor);
            BusyLoading(false);
        }
    }

    private void updateWithProposeOffer(ProposeOfferResp.ProposeOfferRespData propose, double requestLoanAmount, int requestLoanTenor) {
        if (mTenor == requestLoanTenor && mLoanAmount == requestLoanAmount) {
            mProposeOffer = propose;
            setMonthlyPayment(propose.getMonthlyPayment());
            showMessage(String.format("Monthly payment: %,dđ", propose.getMonthlyPayment().intValue()));
        }
        BusyLoading(false);
    }

    private void BusyLoading(boolean isBusy) {
        IsBusyLoadingMonthlyPayment.set(isBusy);
        ShowButton.set(!isBusy);
    }

    private void setupInvalidAmounts() {
        Optional<SuggestOfferResp.LoanTenor> loan = Stream.of(mSuggestOffer.getLoanTenors()).filter(x -> x.getAmount() == mLoanAmount).findFirst();

        if (loan.equals(Optional.empty())) return;

        List<SuggestOfferResp.TenorProduct> validTenor = loan.get().getTenorProducts();

        if (mTenor == 0 && false /*&& Data.Current.ApplicationInfo?.ProposeOffer != null*/) {
//            Tenor = Data.Current.ApplicationInfo.ProposeOffer.Tenor;
        } else {

            if (Stream.of(validTenor).anyMatch(x -> x.getTenor() == mTenor)) {

            } else {
                setTenor(Stream.of(validTenor).findLast().get().getTenor());
            }
//
//            if (mProposeOffer != null) {
//                if (Stream.of(validTenor).anyMatch(xx -> xx.getPaymentDuration() == mProposeOffer.getPaymentDuration()))
//                    setTenor(mProposeOffer.getPaymentDuration());
//                else
//                    setTenor(Stream.of(validTenor).findLast().getValue().getPaymentDuration());
//            } else
//                setTenor(Stream.of(validTenor).findLast().getValue().getPaymentDuration());
        }

        SelectedTenorIndex.set(Stream.of(mTenorValues).map(x -> x.getValue()).toList().indexOf(mTenor));

//        InvalidTenorValues = TenorValues
//                .Select(x = > x.Value)
//                .Where(t = > !validTenor.Any(xx = > xx.Tenor == Convert.ToInt32(t))).ToList();
    }

    private void checkInvalidOfferCache() {
        Boolean invalid = false;

//        foreach (var propose in Data.Current.ProposeOffers)
//        {
//            invalid = !propose.BoundScore.Equals(SuggestOffer.BoundScore) ||
//                    SuggestOffer.LoanTenors.All(x => x.Amount != propose.RequiredAmount) ||
//            SuggestOffer.LoanTenors.All(x => x.TenorProducts.All(xx => xx.ProductCode != propose.ProductCode));
//
//            if (invalid)
//            {
//                break;
//            }
//        }

//        if (invalid) Data.Current.ProposeOffers.Clear();
    }

    public String getCurrentProductCode() {
        Double requestLoanAmount = mLoanAmount;
        int requestLoanTenor = mTenor;

        String productCode = Stream.of(Stream.of(mSuggestOffer.getLoanTenors())
                .filter(x -> x.getAmount() == requestLoanAmount).findFirst().get().getTenorProducts())
                .filter(xxx -> xxx.getTenor() == requestLoanTenor).findFirst().get().getProductCode();

        return productCode;
    }
}
