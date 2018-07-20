package vn.homecredit.hcvn.ui.home;

import javax.inject.Inject;

import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class HomeViewModel extends BaseViewModel {
    private final AccountRepository accountRepository;
    private String mUserName;

    @Inject
    public HomeViewModel(SchedulerProvider schedulerProvider, AccountRepository accountRepository) {
        super(schedulerProvider);
        this.accountRepository = accountRepository;
        if (accountRepository != null
                && accountRepository.getCachedProfile() != null
                && accountRepository.getCachedProfile().getFullName() != null) {
            mUserName = accountRepository.getCachedProfile().getFullName();
        }else {
            mUserName = "";
        }
    }

    public String getUserName() {
        return mUserName;
    }
}
