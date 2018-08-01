package vn.homecredit.hcvn.ui.contract.detail;

import javax.inject.Inject;

import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class ContractDetailViewModel extends BaseViewModel {

    @Inject
    public ContractDetailViewModel(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }
}
