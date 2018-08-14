package vn.homecredit.hcvn.data.repository;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.ui.support.model.SupportResp;

public class SupportRepositoryImpl implements SupportRepository {
    private final RestService restService;

    @Inject
    public SupportRepositoryImpl(RestService restService) {
        this.restService = restService;
    }

    @Override
    public Single<SupportResp> submitFeedback(String subject, String description, String phoneNumber, String contractId) {
        return restService.submitFeedback(subject, description, phoneNumber, contractId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
