package vn.homecredit.hcvn.data.repository;

import java.util.List;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.mapdata.payoo.PayooData;

public interface MapRepository {

    Single<List<PayooData>> getPayooNear(Double lat, Double lon);

}
