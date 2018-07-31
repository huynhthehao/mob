package vn.homecredit.hcvn.ui.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.mapdata.payoo.PayooData;
import vn.homecredit.hcvn.data.repository.AccountRepository;
import vn.homecredit.hcvn.data.repository.MapRepository;
import vn.homecredit.hcvn.service.ResourceService;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.BitmapUtils;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class PayMapViewModel extends BaseViewModel {

    private MapRepository mMapRepository;

    @Inject
    public PayMapViewModel(MapRepository mapRepository, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.mMapRepository = mapRepository;
    }

    @Override
    public void init() {
        super.init();
    }

    public void loadMapPayoo(GoogleMap mMap, Context context, LatLng latLng) {
        Disposable disposable = mMapRepository.getPayooNear(latLng.latitude, latLng.longitude)
                .subscribe(
                        payooDataList -> {
                            Log.debug("NNam" + payooDataList);
                            for (PayooData payooData : payooDataList) {
                                // create marker
                                Bitmap icon = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_home);
                                MarkerOptions marker = new MarkerOptions().position(new LatLng(payooData.getLat(),
                                        payooData.getLong())).title(payooData.getAddress())
                                        .icon(BitmapDescriptorFactory.fromBitmap(icon));

                                // adding marker
                                mMap.addMarker(marker);
                            }
                        },
                        throwable -> {
                            Log.debug("NNam" + throwable.getMessage());
                        }
                );
        getCompositeDisposable().add(disposable);
    }

}
