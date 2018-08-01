package vn.homecredit.hcvn.ui.map;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityPayMapBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;

public class PayMapActivity extends BaseActivity<ActivityPayMapBinding, PayMapViewModel> implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerClickListener {

    //permissions
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    //Location mode
    public static final int PAYMENT_MODE = 0;
    public static final int DISBURSEMENT_MODE = 1;
    public static final int CASH_LOAN_MODE = 2;
    //Maker type
    public static final String MAKER_MOMO = "momo";
    public static final String MAKER_EBAY = "epaydisbursement";
    public static final String MAKER_POS = "";

    public int currentMode = PAYMENT_MODE;

    @Inject
    PayMapViewModel payMapViewModel;

    private GoogleMap mMap;
    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private LatLng mCenterLocation;
    Toolbar toolbar;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_map;
    }

    @Override
    public PayMapViewModel getViewModel() {
        return payMapViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        payMapViewModel.init();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (currentMode) {
            case PAYMENT_MODE:
                menu.findItem(R.id.payment).setChecked(true);
                toolbar.setTitle(R.string.map_payment_location);
                break;
            case DISBURSEMENT_MODE:
                menu.findItem(R.id.disbursement).setChecked(true);
                toolbar.setTitle(R.string.map_disbursement_location);
                break;
            case CASH_LOAN_MODE:
                menu.findItem(R.id.cash_loan).setChecked(true);
                toolbar.setTitle(R.string.map_cash_loan_location);
                break;
            default:
                menu.findItem(R.id.payment).setChecked(true);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.payment:
                currentMode = PAYMENT_MODE;
                if (mCenterLocation != null) {
                    payMapViewModel.loadPaymentMap(mMap, mCenterLocation, getApplicationContext());
                }
                break;
            case R.id.disbursement:
                currentMode = DISBURSEMENT_MODE;
                if (mCenterLocation != null) {
                    payMapViewModel.loadDisbursementMap(mMap, mCenterLocation, getApplicationContext());
                }
                break;
            case R.id.cash_loan:
                currentMode = CASH_LOAN_MODE;
                if (mCenterLocation != null) {
                    payMapViewModel.loadCashLoanMap(mMap, mCenterLocation, getApplicationContext());
                }
                break;
            default:
                break;
        }
        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng defaultLocation = new LatLng(10.787273, 106.749810);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(defaultLocation.latitude, defaultLocation.longitude), 15));
        mMap.setOnCameraIdleListener(this);
        mMap.setOnMarkerClickListener(this);
        getLocationPermission();

    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            updateLocationUI();
            getDeviceLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }

    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = (Location) task.getResult();
                        try {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), 15));
                            mCenterLocation = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                        } catch (Exception e) {
                            //
                        }

                    } else {
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException e) {
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
        }
    }

    @Override
    public void onCameraIdle() {
        //on map scroll end
        mCenterLocation = mMap.getCameraPosition().target;
        if (mCenterLocation == null) {
            return;
        }
        switch (currentMode) {
            case PAYMENT_MODE:
                payMapViewModel.loadPayment(mMap, getApplicationContext(), mCenterLocation);
                break;
            case DISBURSEMENT_MODE:
                payMapViewModel.loadDisbursement(mMap, getApplicationContext(), mCenterLocation);
                break;
            case CASH_LOAN_MODE:
                payMapViewModel.loadClwData(mMap, getApplicationContext(), mCenterLocation);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (mLastKnownLocation != null) {
            payMapViewModel.drawDirection(mMap, mLastKnownLocation, marker.getPosition(), getApplicationContext());
        }
        return false;
    }
}
