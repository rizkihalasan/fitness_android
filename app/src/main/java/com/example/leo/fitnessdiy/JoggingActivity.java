package com.example.leo.fitnessdiy;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leo.fitnessdiy.Utilities.NetworkUtils;
import com.example.leo.fitnessdiy.model.Jogging;
import com.example.leo.fitnessdiy.routes.api;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class JoggingActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{
    private static final String TAG = JoggingActivity.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;

    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 5;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;
    private TextView infoJoggingStart;
    private TextView infoJoggingEnd;
    private Button mButton;
    private String startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // save instance state
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        setContentView(R.layout.activity_jogging);

        mGeoDataClient = Places.getGeoDataClient(this, null);

        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        infoJoggingStart = (TextView) findViewById(R.id.tv_info_start);

        infoJoggingEnd = (TextView) findViewById(R.id.tv_info_end);

        mButton = (Button) findViewById(R.id.control_button);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View infoView = getLayoutInflater().inflate(R.layout.custom_info_location,
                        (FrameLayout) findViewById(R.id.map), false);

                TextView title = ((TextView) infoView.findViewById(R.id.title));
                title.setText(marker.getTitle());

                TextView snippet = ((TextView) infoView.findViewById(R.id.snippet));
                snippet.setText(marker.getSnippet());

                return infoView;
            }
        });

        getLocationPermission();

        updateLocationUI();

        getDeviceLocation();
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            LatLng position = new LatLng(mLastKnownLocation.getLatitude(),
                                    mLastKnownLocation.getLongitude());
//                            mMap.addMarker(
//                                    new MarkerOptions().position(position).title("Your Position"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,
                                    DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current Location is null. Using Defaults");
                            Log.e(TAG, "Exception: %s", task.getException());

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation,
                                    DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();

        MarkerOptions mp = new MarkerOptions();

        mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
        mp.title("Your Position");

        mMap.addMarker(mp);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 16
        ));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * Sets up the options menu.
     * @param menu The options menu.
     * @return Boolean.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.current_place_menu, menu);
        return true;
    }

    /**
     * Handles a click on the menu option to get a place.
     * @param item The menu item to handle.
     * @return Boolean.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.option_get_place) {
            showCurrentPlace();
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
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
        } catch (SecurityException e){
            e.printStackTrace();
        }
    }

    private void showCurrentPlace() {
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            @SuppressWarnings("MissingPermission") final
            Task<PlaceLikelihoodBufferResponse> placeResult = mPlaceDetectionClient
                    .getCurrentPlace(null);
            placeResult.addOnCompleteListener(
                    new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                PlaceLikelihoodBufferResponse likelihoods = task.getResult();

                                int count;

                                if (likelihoods.getCount() < M_MAX_ENTRIES) {
                                    count = likelihoods.getCount();
                                } else {
                                    count = M_MAX_ENTRIES;
                                }

                                int i = 0;
                                mLikelyPlaceNames = new String[count];
                                mLikelyPlaceAddresses = new String[count];
                                mLikelyPlaceAttributions = new String[count];
                                mLikelyPlaceLatLngs = new LatLng[count];

                                for (PlaceLikelihood placeLikelihood : likelihoods){
                                    mLikelyPlaceNames[i] = (String) placeLikelihood.getPlace()
                                            .getName();
                                    mLikelyPlaceLatLngs[i] = placeLikelihood.getPlace()
                                            .getLatLng();
                                    mLikelyPlaceAddresses[i] = (String) placeLikelihood.getPlace()
                                            .getAddress();
                                    mLikelyPlaceAttributions[i] = (String) placeLikelihood.getPlace()
                                            .getAttributions();
                                    i++;
                                    if (i > count - 1) {
                                        break;
                                    }
                                }

                                likelihoods.release();

                                openPlacesDialog();
                            } else {
                                Log.d(TAG, "Exception: %s", task.getException());
                            }
                        }
                    }
            );
        } else {
            Log.i(TAG, "The user did not grant location permission");
            mMap.addMarker(new MarkerOptions().title("Default Position")
                .position(mDefaultLocation)
                .snippet("No places found, because location permission is disabled."));

            getLocationPermission();
        }
    }

    public void openPlacesDialog() {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LatLng markerLatLng = mLikelyPlaceLatLngs[i];
                String markerSnippet = mLikelyPlaceAddresses[i];
                if (mLikelyPlaceAttributions[i] != null) {
                    markerSnippet = markerSnippet + "\n" + mLikelyPlaceAttributions[i];
                }

                mMap.addMarker(new MarkerOptions().title(mLikelyPlaceNames[i])
                    .position(markerLatLng)
                    .snippet(markerSnippet));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng, DEFAULT_ZOOM));
            }
        };

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Choose a place!")
                .setItems(mLikelyPlaceNames, listener)
                .show();
    }

    public String getCurrentLocation() {
        String loc = "";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        String errorMessage = "";
        try {
            addresses = geocoder.getFromLocation(mLastKnownLocation.getLatitude(),
                    mLastKnownLocation.getLongitude(), 1);

            loc = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            errorMessage = "using invalid latitude and longitude data";

            Log.e(TAG, errorMessage + ". " +
                    "Latitude = " + mLastKnownLocation.getLatitude() +
                    ", Longitude = " +
                    mLastKnownLocation.getLongitude(), e);
        }

        return loc;
    }
    public void startJogging(View view) {
        infoJoggingStart.setText(getCurrentLocation());
        mButton.setText("STOP");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopJogging(view);
            }
        });
        mButton.setBackgroundColor(Color.parseColor("#FF0000"));
        startTime = new SimpleDateFormat("HH:mm:ss")
                .format(Calendar.getInstance().getTime());
    }

    public void stopJogging(View view) {
        infoJoggingEnd.setText(getCurrentLocation());
        mButton.setText("START");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startJogging(view);
            }
        });
        mButton.setBackgroundColor(Color.parseColor("#4CFF00"));

        // TODO : Distance itung berapa langkah
        float distance = 1000;
        Calendar c = Calendar.getInstance();
        Jogging jogging = new Jogging(
                new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()),
                startTime,
                new SimpleDateFormat("HH:mm:ss").format(c.getTime()),
                distance,
                infoJoggingStart.getText().toString(),
                infoJoggingEnd.getText().toString()
        );
        // TODO : id_user sesuai id user yang login
        int id_user = 1;
        String url = api.newJoggingHistory(
                id_user,
                jogging.getDate(),
                jogging.getStart(),
                jogging.getEnd(),
                jogging.getDistance(),
                jogging.getStartingPoint(),
                jogging.getEndPoint()
        );
        Log.d(TAG, url);
        try {
            String response = NetworkUtils.getResponseFromHttpUrl(
                    new URL(url)
            );
            Log.d(TAG, response);
//            JSONObject jsonObject = new JSONObject(response);
//            if (jsonObject.getString("error") == null) {
//                Log.d(TAG, "Inserting new record successful");
//            } else {
//                Log.e(TAG, jsonObject.getString("error"));
//            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private class AddNewJogging extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//        }
//    }
}
