package vn.homecredit.hcvn.ui.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

import javax.inject.Inject;

import vn.homecredit.hcvn.BR;
import vn.homecredit.hcvn.R;
import vn.homecredit.hcvn.databinding.ActivityPayMapBinding;
import vn.homecredit.hcvn.ui.base.BaseActivity;
import vn.homecredit.hcvn.utils.ResourcesUtil;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class PayMapActivity extends BaseActivity<ActivityPayMapBinding, PayMapViewModel> implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerClickListener {

    //permissions
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    //google direction key
    public static String GG_DIRECTION_KEY = ResourcesUtil.getString(R.string.google_direction_key);
    //Location mode
    public static final int PAYMENT_MODE = 0;
    public static final int DISBURSEMENT_MODE = 1;
    public static final int CASH_LOAN_MODE = 2;
    //Maker type
    public static final String MAKER_MOMO = "momo";
    public static final String MAKER_PAYOO = "payoo";
    public static final String MAKER_EBAY = "epaydisbursement";
    public static final String MAKER_DEFAULT = "";
    public static final String MODE_KEY = "Mode";

    public int currentMode = PAYMENT_MODE;

    @Inject
    PayMapViewModel payMapViewModel;

    private GoogleMap mMap;
    private View mMapView;
    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private LatLng mCenterLocation;
    Toolbar toolbar;
    private Marker marker = null;
    static LatLng defaultLocation = new LatLng(10.787273, 106.749810);

    public static void start(Context context, int modeMap) {
        Intent intent = new Intent(context, PayMapActivity.class);
        intent.putExtra(MODE_KEY, modeMap);
        context.startActivity(intent);
    }

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
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        payMapViewModel.init();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        mMapView = mapFragment.getView();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        currentMode = getIntent().getIntExtra(MODE_KEY, PAYMENT_MODE);
        invalidateOptionsMenu();
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
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(defaultLocation.latitude, defaultLocation.longitude), 15));
        mMap.setOnCameraIdleListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //custom position of current position
        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            rlp.addRule(RelativeLayout.ALIGN_PARENT_END, 0);
            rlp.addRule(RelativeLayout.ALIGN_END, 0);

        }
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rlp.setMargins(50, 0, 0, 50);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            rlp.setMarginStart(50);
        }
        getLocationPermission();

    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            updateLocationUI();
            getDeviceLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{ACCESS_FINE_LOCATION},
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
                        && grantResults[0] == PERMISSION_GRANTED) {
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
                        } catch (Exception ignored) {
                        }

                    } else {
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException ignored) {
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
        } catch (SecurityException ignored) {
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
                payMapViewModel.loadMapPayoo(mMap, getApplicationContext(), mCenterLocation);
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
        this.marker = marker;
        return false;
    }


    public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private View view;

        @SuppressLint("InflateParams")
        CustomInfoWindowAdapter() {
            view = getLayoutInflater().inflate(R.layout.custom_info_window, null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            if ((PayMapActivity.this.marker != null) && PayMapActivity.this.marker.isInfoWindowShown()) {
                PayMapActivity.this.marker.hideInfoWindow();
                PayMapActivity.this.marker.showInfoWindow();
            }
            return null;
        }

        @Override
        public View getInfoWindow(final Marker marker) {
            PayMapActivity.this.marker = marker;

            final ImageView image = view.findViewById(R.id.infor_image);
            image.setVisibility(View.VISIBLE);

            switch (Objects.requireNonNull((String) marker.getTag())) {
                case MAKER_EBAY:
                    image.setImageResource(R.drawable.ic_epay);
                    break;
                case PayMapActivity.MAKER_MOMO:
                    image.setImageResource(R.drawable.ic_momo);
                    break;
                case PayMapActivity.MAKER_PAYOO:
                    image.setImageResource(R.drawable.ic_payoo);
                    break;
                case PayMapActivity.MAKER_DEFAULT:
                    image.setVisibility(View.GONE);
                    break;
                default:
                    image.setVisibility(View.GONE);
                    break;

            }
            final String title = marker.getTitle();
            final TextView titleUi = view.findViewById(R.id.infor_title);
            if (title != null) {
                titleUi.setText(title);
            } else {
                titleUi.setText("");
            }

            final String snippet = marker.getSnippet();
            final TextView snippetUi = view.findViewById(R.id.infor_snipper);
            if (snippet != null) {
                snippetUi.setText(snippet);
            } else {
                snippetUi.setText("");
            }
            return view;
        }

    }
}
