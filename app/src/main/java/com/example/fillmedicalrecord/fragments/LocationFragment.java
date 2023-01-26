package com.example.fillmedicalrecord.fragments;


import static android.widget.Toast.LENGTH_SHORT;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fillmedicalrecord.R;
import com.example.fillmedicalrecord.models.StudentModel;
import com.example.fillmedicalrecord.database.ApiClient;
import com.example.fillmedicalrecord.database.ApiInterface;
import com.example.fillmedicalrecord.models.user_data;
import com.example.fillmedicalrecord.models.users_data_model;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.vmadalin.easypermissions.EasyPermissions;
import com.vmadalin.easypermissions.dialogs.SettingsDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationFragment extends Fragment implements EasyPermissions.PermissionCallbacks {
    MapView mMapView;
    private GoogleMap googleMap;
    user_data user_data;
    LatLng latLng;
    SharedPreferences sharedPreferences;
    StudentModel user;
    TextView no_location;
    FloatingActionButton btn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);

        btn = (FloatingActionButton) rootView.findViewById(R.id.refresh);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_user_data();
            }
        });
        getActivity().setTitle("Location Screen");
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("user", "");
        user = gson.fromJson(json, StudentModel.class);
        no_location = (TextView) rootView.findViewById(R.id.no_data_found);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        get_user_data();


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onPermissionsDenied(int i, @NonNull List<String> list) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
            new SettingsDialog.Builder(requireActivity()).build().show();
        } else {
            requestsBackgroundLocationPermission(this);
        }
    }

    @Override
    public void onPermissionsGranted(int i, @NonNull List<String> list) {
        mapready();
        // mMapView.onResume(); // needed to get the map to display immediately
// onGeofenceReady()
        Toast.makeText(
                requireContext(),
                "Permission Granted! Long Press on the Map to add a Geofence.",
                Toast.LENGTH_SHORT
        ).show();

    }

    void requestsBackgroundLocationPermission(Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                    fragment,
                    "Background location permission is essential to this application. Without it we will not be able to provide you with our service.",
                    2,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
            );
        }
    }

    private void mapready() {
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(Double.parseDouble(user_data.getLatitude()), Double.parseDouble(user_data.getLongitude()));
                googleMap.addMarker(new MarkerOptions().position(sydney).title("student location").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(requireContext(), perms)) {
            mapready();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "android.permission.ACCESS_FINE_LOCATION",
                    2, perms);
        }
    }

    private void get_user_data() {

        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Log.i("loginnnn", "   test");
        Call<users_data_model> call = services.get_user_data(user.getDevice_id());

        call.enqueue(new Callback<users_data_model>() {
            @Override
            public void onResponse(Call<users_data_model> call, Response<users_data_model> response) {

                if (response.body().getSuccess().equals("1")) {

                    ArrayList<user_data> data = new ArrayList<>();
                    data = response.body().getData();

                    if (data.size() > 0) {
                        user_data = data.get(0);
                        methodRequiresTwoPermission();
                    }else {
                        no_location.setVisibility(View.VISIBLE);
                        mMapView.setVisibility(View.GONE);
                    }

                }else {
                    no_location.setVisibility(View.VISIBLE);
                    mMapView.setVisibility(View.GONE);
                }

            {
                Toast.makeText(requireActivity(), response.body().getSuccess(), LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure (Call < users_data_model > call, Throwable t){
            Toast.makeText(requireActivity(), t.getMessage(), LENGTH_SHORT).show();
        }
    });
}
}