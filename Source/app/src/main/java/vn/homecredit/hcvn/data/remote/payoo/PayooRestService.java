package vn.homecredit.hcvn.data.remote.payoo;

import java.util.List;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.mapdata.model.payoo.PayooData;

public interface PayooRestService {

    //map service
    Single<List<PayooData>> getPayooNear(Double lat, Double lon);

}
