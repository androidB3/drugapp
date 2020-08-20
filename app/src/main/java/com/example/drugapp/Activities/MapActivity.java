package com.example.drugapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drugapp.Entity.DownloadUrl;
import com.example.drugapp.Entity.GetNearbyPlacesData;

import com.example.drugapp.R;
import com.example.drugapp.directionhelpers.FetchURL;
import com.example.drugapp.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    GoogleMap map;
    Location currentLocation;
    Button btnSearch,btnDirect;
    Polyline currentPolyline=null;
    EditText edtStartDes,edtEndDes;
    private MarkerOptions place1, place2;

//    private List<Marker> originMarkers = new ArrayList<>();
//    private List<Marker> destinationMarkers = new ArrayList<>();
//    private List<Polyline> polylinePaths = new ArrayList<>();
//    private ProgressDialog progressDialog;



    int PROXIMITY_RADIUS=10000;

    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        btnSearch=findViewById(R.id.btnSearchStore);
        btnDirect=findViewById(R.id.btnDirect);



        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
//        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
//        mapFragment.getMapAsync(this);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        //map.clear();
                        String hospital="drugstore";
                        String url=getUrl(currentLocation.getLatitude(),currentLocation.getLongitude(),hospital);
                        Object dataTransfer[]=new Object[2];
                        dataTransfer[0]=map;
                        dataTransfer[1]=url;
                        GetNearbyPlacesData getNearbyPlacesData=new GetNearbyPlacesData();
                        getNearbyPlacesData.execute(dataTransfer);
                        Toast.makeText(MapActivity.this,"showing nearby hospital",Toast.LENGTH_LONG).show();

                }


        });



        btnDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  new FetchURL(MapActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");


            }
        });

        place1 = new MarkerOptions().position(new LatLng(27.658143, 85.3199503)).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(27.667491, 85.3208583)).title("Location 2");


    }


    private void fetchLastLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation=location;
                    SupportMapFragment supportMapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
                    supportMapFragment.getMapAsync(MapActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map=googleMap;

        LatLng latLng=new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(" i m here"))
                .setIcon(bitmapDescriptor(getApplicationContext(),R.drawable.ic_baseline_accessibility_new_24));


//        LatLng HCM = new LatLng(10.810643, 106.709136);
//
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(HCM,13));
//        googleMap.addMarker(new MarkerOptions()
//                .position(HCM)
//                .title("Marker in HCM"));

//        LatLng BTH = new LatLng(10.8231, 106.6297);
//
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(BTH,13));
//        googleMap.addMarker(new MarkerOptions()
//                .position(BTH)
//                .title("Marker in Binh Thanh"))
//                .setIcon(bitmapDescriptor(getApplicationContext(),R.drawable.ic_store));


//        String url=getUrlDirect(latLng,BTH,"driving");
        //private static final LatLng MELBOURNE = new LatLng(10.8231, 106.6297);
//icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_baseline_local_hospital_24));
        map.addMarker(place1);
        map.addMarker(place2);
    }

    private BitmapDescriptor bitmapDescriptor(Context context,int VectorResId){
        Drawable vectorDrawable= ContextCompat.getDrawable(context,VectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight());

        Bitmap bitmap=Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }

    private String getUrl(double latitude,double longitude,String nearbyPlace){
        //final String x="AIzaSyBgRTw6KPFWNV0Sr61V8pbAVvJDpLv3JOU";
        StringBuilder googlePlaceUrl=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+getResources().getString(R.string.map_api_key));





        return googlePlaceUrl.toString();
    }


    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.map_api_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);

    }


}
