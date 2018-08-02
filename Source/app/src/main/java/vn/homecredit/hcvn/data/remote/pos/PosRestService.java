package vn.homecredit.hcvn.data.remote.pos;

import io.reactivex.Single;
import vn.homecredit.hcvn.data.model.mapdata.model.pos.PosModel;

public interface PosRestService {
    //map pos service
    Single<PosModel> getPosNear(Double lat, Double log);

    Single<PosModel> getVnPosNear(Double lat, Double log);

}
