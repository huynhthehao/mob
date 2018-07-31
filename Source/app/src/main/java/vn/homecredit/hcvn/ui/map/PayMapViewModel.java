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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.data.model.mapdata.model.payoo.PayooData;
import vn.homecredit.hcvn.data.model.mapdata.model.pos.PosData;
import vn.homecredit.hcvn.data.repository.MapRepository;
import vn.homecredit.hcvn.ui.base.BaseViewModel;
import vn.homecredit.hcvn.utils.BitmapUtils;
import vn.homecredit.hcvn.utils.Log;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

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




    public void loadMapPayoo(GoogleMap mMap, Context context, LatLng latLng) {
        Disposable disposable = mMapRepository.getPayooNear(latLng.latitude, latLng.longitude)
                .subscribe(
                        payooDataList -> {
                            Log.debug("NNam" + payooDataList);
                            for (PayooData payooData : payooDataList) {
                                // create marker
                                Bitmap icon = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_home);
                                MarkerOptions marker = new MarkerOptions().position(new LatLng(payooData.getLat(),
                                        payooData.getLong()))
                                        .title(payooData.getDisplayName())
                                        .snippet(payooData.getAddress())
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

    public void loadMapPos(GoogleMap googleMap, Context context, LatLng latLng) {
        Disposable disposable = mMapRepository.getPosNear(latLng.latitude, latLng.longitude)
                .subscribe(
                        posModel -> {
                            Log.debug("NNam" + posModel);
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
                        }
                );
        getCompositeDisposable().add(disposable);
    }

    public void loadMapVnPos(GoogleMap googleMap, Context context, LatLng latLng) {
        Disposable disposable = mMapRepository.getVnPosNear(latLng.latitude, latLng.longitude)
                .subscribe(
                        posModel -> {
                            Log.debug("NNam" + posModel);
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
                        }
                );
        getCompositeDisposable().add(disposable);
    }

    public void drawDirection(GoogleMap mMap, Location mLastKnownLocation, LatLng position, Context context) {

        //clear poly
        for (Polyline polyline : mPolylines) {
            polyline.remove();
        }

        GoogleDirection.withServerKey("AIzaSyARvrEdNOBOHlzTRTiPbh0rkZASCDzQUTY")
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

                        } else {
                            // Do something
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                    }
                });
    }

    public void clearMap(GoogleMap mapview){
        mapview.clear();
    }

}
