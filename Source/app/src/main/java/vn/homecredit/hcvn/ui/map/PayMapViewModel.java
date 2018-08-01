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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.mapdata.model.clw.ClwData;
import vn.homecredit.hcvn.data.model.mapdata.model.disbursement.DisbursementData;
import vn.homecredit.hcvn.data.model.mapdata.model.payment.PaymentData;
import vn.homecredit.hcvn.data.model.mapdata.model.payoo.PayooData;
import vn.homecredit.hcvn.data.model.mapdata.model.pos.PosData;
import vn.homecredit.hcvn.data.repository.MapRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.BitmapUtils;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class PayMapViewModel extends BaseViewModel {

    private MapRepository mMapRepository;
    private List<Polyline> mPolylines = new ArrayList<>();
    private static String GG_MAP_DIRECTION_KEY = "AIzaSyARvrEdNOBOHlzTRTiPbh0rkZASCDzQUTY";

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
                            for (PayooData payooData : payooDataList) {
                                drawMaker(PayMapActivity.MAKER_PAYOO, mMap, context, new LatLng(payooData.getLat(),
                                        payooData.getLong()), payooData.getDisplayName(), payooData.getAddress());
                            }
                        },
                        throwable -> {
                        }
                );
        getCompositeDisposable().add(disposable);
    }

    public void loadMapPos(GoogleMap googleMap, Context context, LatLng latLng) {
        Disposable disposable = mMapRepository.getPosNear(latLng.latitude, latLng.longitude)
                .subscribe(
                        posModel -> {
                            for (PosData posData : posModel.getData()) {
                                // create marker
                                Bitmap icon = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_contract_creditcard);
                                MarkerOptions marker = new MarkerOptions().position(new LatLng(posData.getLatitude(),
                                        posData.getLongitude()))
                                        .title(posData.getTitle())
                                        .snippet(posData.getAddress())
                                        .icon(BitmapDescriptorFactory.fromBitmap(icon));
                                // adding marker
                                googleMap.addMarker(marker);
                            }
                        }, throwable ->
                        {
                        });
        getCompositeDisposable().add(disposable);
    }

    public void loadMapVnPos(GoogleMap googleMap, Context context, LatLng latLng) {
        Disposable disposable = mMapRepository.getVnPosNear(latLng.latitude, latLng.longitude)
                .subscribe(
                        posModel -> {
                            for (PosData posData : posModel.getData()) {
                                // create marker
                                Bitmap icon = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_contract_cashloan);
                                MarkerOptions marker = new MarkerOptions().position(new LatLng(posData.getLatitude(),
                                        posData.getLongitude()))
                                        .title(posData.getTitle())
                                        .snippet(posData.getAddress())
                                        .icon(BitmapDescriptorFactory.fromBitmap(icon));
                                // adding marker
                                googleMap.addMarker(marker);
                            }
                        }, throwable -> {
                        }
                );
        getCompositeDisposable().add(disposable);
    }

    public void loadClwData(GoogleMap googleMap, Context context, LatLng latLng) {
        Disposable disposable = mMapRepository.getClwModelNear(latLng.latitude, latLng.longitude)
                .subscribe(
                        clwModels -> {
                            for (ClwData posData : clwModels.getData()) {
                                drawMaker(PayMapActivity.MAKER_DEFAULT, googleMap, context, new LatLng(posData.getLatitude(), posData.getLongitude()), posData.getTitle(), posData.getAddress());
                            }
                        }, throwable -> {
                        }
                );
        getCompositeDisposable().add(disposable);
    }

    public void loadDisbursement(GoogleMap googleMap, Context context, LatLng latLng) {
        Disposable disposable = mMapRepository.getDisbursementModelNear(latLng.latitude, latLng.longitude)
                .subscribe(
                        disbursementModel -> {
                            for (DisbursementData posData : disbursementModel.getData()) {
                                // create marker
                                drawMaker(posData.getBrand(), googleMap, context, new LatLng(posData.getLatitude(), posData.getLongitude()), posData.getTitle(), posData.getAddress());

                            }
                        }, throwable -> {
                        }
                );
        getCompositeDisposable().add(disposable);
    }

    public void loadPayment(GoogleMap googleMap, Context context, LatLng latLng) {
        Disposable disposable = mMapRepository.getPaymentModelNear(latLng.latitude, latLng.longitude)
                .subscribe(
                        paymentModel -> {
                            for (PaymentData posData : paymentModel.getData()) {
                                drawMaker(posData.getBrand(), googleMap, context, new LatLng(posData.getLatitude(), posData.getLongitude()), posData.getTitle(), posData.getAddress());
                            }
                        }, throwable -> {
                        }
                );
        getCompositeDisposable().add(disposable);
    }

    public void drawDirection(GoogleMap mMap, Location mLastKnownLocation, LatLng position, Context context) {

        //clear poly
        for (Polyline polyline : mPolylines) {
            polyline.remove();
        }

        GoogleDirection.withServerKey(GG_MAP_DIRECTION_KEY)
                .from(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()))
                .to(new LatLng(position.latitude, position.longitude))
                .avoid(AvoidType.FERRIES)
                .avoid(AvoidType.HIGHWAYS)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            // Do something
                            Route route = direction.getRouteList().get(0);
                            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
                            mPolylines.add(mMap.addPolyline(DirectionConverter.createPolyline(context, directionPositionList, 5, Color.RED)));

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
        loadMapPayoo(map, context, position);
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
            case PayMapActivity.MAKER_EBAY:
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
