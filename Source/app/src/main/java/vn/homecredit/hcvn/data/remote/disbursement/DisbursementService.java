package vn.homecredit.hcvn.data.remote.disbursement;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.mapdata.model.disbursement.DisbursementModel;

public interface DisbursementService {
    Single<DisbursementModel> getDisbursementNear(Double lat, Double lon);
}
