package vn.homecredit.hcvn.ui.payment.summary;

import android.arch.lifecycle.MutableLiveData;

import javax.inject.Inject;

import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class PaymentSummaryViewModel extends BaseViewModel {
    private MutableLiveData<Boolean> doneClickEvent = new MutableLiveData<>();

    @Inject
    public PaymentSummaryViewModel(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    public void onDoneClick() {
        doneClickEvent.setValue(true);
    }

    public MutableLiveData<Boolean> getDoneClickEvent() {
        return doneClickEvent;
    }
}
