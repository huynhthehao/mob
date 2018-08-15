package vn.homecredit.hcvn.data.repository;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.api.support.SupportResp;

public interface SupportRepository {

    Single<SupportResp> submitFeedback(String subject, String description, String phoneNumber, String contractId);
}
