package vn.homecredit.hcvn.ui.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.mapdata.model.clw.ClwData;
import vn.homecredit.hcvn.data.model.mapdata.model.disbursement.DisbursementData;
import vn.homecredit.hcvn.data.model.mapdata.model.payment.PaymentData;
import vn.homecredit.hcvn.data.repository.MapRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.BitmapUtils;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

import static vn.homecredit.hcvn.ui.map.PayMapActivity.GG_DIRECTION_KEY;

public class PayMapViewModel extends BaseViewModel {

    private MapRepository mMapRepository;
    private List<Polyline> mPolylines = new ArrayList<>();

    @Inject
    public PayMapViewModel(MapRepository mapRepository, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.mMapRepository = mapRepository;
    }

    @Override
    public void init() {
        super.init();
    }

    public void loadClwData(GoogleMap googleMap, Context context, LatLng latLng) {
        Disposable disposable = mMapRepository.getClwModelNear(latLng.latitude, latLng.longitude)
                .toObservable().distinctUntilChanged()
                .subscribe(
                        clwModels -> {
                            if (clwModels.getData().isEmpty()) {
                                return;
                            }
                            for (ClwData data : clwModels.getData()) {
                                drawMaker(PayMapActivity.MAKER_DEFAULT, googleMap, context, new LatLng(data.getLatitude(),
                                        data.getLongitude()), data.getTitle(), data.getAddress());
                            }
                        }, throwable -> {
                        }
                );
        getCompositeDisposable().add(disposable);
    }

    public void loadDisbursement(GoogleMap googleMap, Context context, LatLng latLng) {
        Disposable disposable = mMapRepository.getDisbursementModelNear(latLng.latitude, latLng.longitude)
                .toObservable().distinctUntilChanged()
                .subscribe(
                        disbursementModel -> {
                            if (disbursementModel.getData().isEmpty()) {
                                return;
                            }
                            for (DisbursementData data : disbursementModel.getData()) {
                                drawMaker(data.getBrand(), googleMap, context, new LatLng(data.getLatitude(),
                                        data.getLongitude()), data.getTitle(), data.getAddress());
                            }
                        }, throwable -> {
                        }
                );
        getCompositeDisposable().add(disposable);
    }

    public void loadPayment(GoogleMap googleMap, Context context, LatLng latLng) {
        Disposable disposable = mMapRepository.getPaymentModelNear(latLng.latitude, latLng.longitude)
                .toObservable().distinctUntilChanged()
                .subscribe(
                        paymentModel -> {
                            if (paymentModel.getData().isEmpty()) {
                                return;
                            }
                            for (PaymentData data : paymentModel.getData()) {
                                drawMaker(data.getBrand(), googleMap, context, new LatLng(data.getLatitude(),
                                        data.getLongitude()), data.getTitle(), data.getAddress());
                            }
                        }, throwable -> {
                        }
                );
        getCompositeDisposable().add(disposable);
    }

    public void drawDirection(GoogleMap map, Location mLastKnownLocation, LatLng position, Context context) {

        //clear poly
        for (Polyline polyline : mPolylines) {
            polyline.remove();
        }

        GoogleDirection.withServerKey(GG_DIRECTION_KEY)
                .from(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()))
                .to(new LatLng(position.latitude, position.longitude))
                .avoid(AvoidType.FERRIES)
                .avoid(AvoidType.HIGHWAYS)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            Route route = direction.getRouteList().get(0);
                            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
                            PolylineOptions polylineOptions = DirectionConverter.createPolyline(context, directionPositionList, 5, Color.RED);
                            polylineOptions.color(Color.GRAY).width(17);
                            mPolylines.add(map.addPolyline(polylineOptions));
                            polylineOptions.color(Color.RED).width(14);
                            mPolylines.add(map.addPolyline(polylineOptions));

                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                    }
                });
    }

    public void loadPaymentMap(GoogleMap map, LatLng position, Context context) {
        map.clear();
        loadPayment(map, context, position);
//        loadMapPayoo(map, context, position);
    }

    public void loadDisbursementMap(GoogleMap map, LatLng position, Context context) {
        map.clear();
        loadDisbursement(map, context, position);
    }

    public void loadCashLoanMap(GoogleMap map, LatLng position, Context context) {
        map.clear();
        loadClwData(map, context, position);
    }

    private void drawMaker(String makerType, GoogleMap googleMap, Context context, LatLng latLng, String title, String address) {
        Bitmap icon = null;
        switch (makerType) {
            case PayMapActivity.MAKER_EPAY:
            case PayMapActivity.MAKER_EPAY_POS:
                icon = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_epay);
                break;
            case PayMapActivity.MAKER_MOMO:
                icon = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_momo);
                break;
            case PayMapActivity.MAKER_PAYOO:
                icon = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_payoo);
                break;
            case PayMapActivity.MAKER_DEFAULT:
                break;
            default:
                break;
        }

        MarkerOptions marker = new MarkerOptions().position(latLng)
                .title(title)
                .snippet(address);
        if (icon != null) {
            marker.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

        // adding marker
        Marker maker = googleMap.addMarker(marker);
        maker.setTag(makerType);
    }

}
