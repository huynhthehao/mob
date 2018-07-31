package vn.homecredit.hcvn.data.repository;

import java.util.List;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.mapdata.model.payoo.PayooData;
import vn.homecredit.hcvn.data.model.mapdata.model.pos.PosModel;

public interface MapRepository {

    Single<List<PayooData>> getPayooNear(Double lat, Double lon);

    Single<PosModel> getPosNear(Double lat, Double lon);

    Single<PosModel> getVnPosNear(Double lat, Double lon);
}
