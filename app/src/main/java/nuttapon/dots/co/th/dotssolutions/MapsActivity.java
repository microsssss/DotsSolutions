package nuttapon.dots.co.th.dotssolutions;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latADouble=0;
    private double lngADouble=0;
    private double endLaADouble;
    private double endLnADouble;
    private LocationManager locationManager;  // เปิด Service
    private Criteria criteria;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

//        SetUp Location
        setUpLocation();


        // การสร้าง Fragment
        createFragment();

//        Create Toolbar
        createToolbar();


    } /// Main Mathod

    @Override
    protected void onResume() {  // ถ้าช่วงใช้งานมีโทรศัพย์เข้ามาจะหยุดชั่วคราว
        super.onResume();

        findLatLng();

    }

    private void findLatLng() {

//        for Network หาพิกัดโดย Internet

        Location networkLocation=myFindLocation(LocationManager.NETWORK_PROVIDER);
        if (networkLocation != null) {
            latADouble=networkLocation.getLatitude();
            lngADouble=networkLocation.getLongitude();
        }

//        for Card GPS หาพิกัดโดย GPS
        Location gpsLocation=myFindLocation(LocationManager.GPS_PROVIDER);
        if (networkLocation != null) {
            latADouble=gpsLocation.getLatitude();
            lngADouble=gpsLocation.getLongitude();
        }

        Log.d("6SepV1","Lat ==> "+ latADouble);
        Log.d("6SepV1","Lat ==> "+ lngADouble);

    }

    @Override
    protected void onStop() {  // จะทำการปิด Service Location ก่อนปิด Program
        super.onStop();
        locationManager.removeUpdates(locationListener);

    }

    public Location myFindLocation(String providerString) {

        Location location=null;

            if (locationManager.isProviderEnabled(providerString)){  // ถ้าเชื่อต่อกับ Card ์Network ผ่านทาง Internet ได้จะได้ True
                locationManager.requestLocationUpdates(providerString,
                        1000,
                        10,
                        locationListener);
                location=locationManager.getLastKnownLocation(providerString);
            }


        return location;
    }

    public LocationListener locationListener =new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {    // ถ้าขยับจะให้ทำอะไร

            latADouble=location.getLatitude();
            lngADouble=location.getLongitude();


        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {   //สพาวะที่ ต่อ หรือ ต่อไม่ได้ จะให้ทำอะไร

        }

        @Override
        public void onProviderEnabled(String provider) {   //  ถ้าเชื่อมต่อกับ Provider ได้จะให้ทำอะไร

        }

        @Override
        public void onProviderDisabled(String provider) {  //ถ้าไม่เชื่อมต่อกับ Provider ได้จะให้ทำอะไร

        }
    };



    private void setUpLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);   // ขอเปิด Location Service
        criteria=new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);    //  ACCURACY_FINE หาพิกัดแบบค่าผิดพลาด น้อยที่สุด
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
    }

    private void createFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void createToolbar() {
        Toolbar toolbar =findViewById(R.id.toolbarMap);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(getString(R.string.choose_point));

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_action_point_bar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MapsActivity.this, ServiceActivity.class);
                intent.putExtra("Lat",endLaADouble);
                intent.putExtra("Lng",endLnADouble);
                setResult(50,intent);    // ตอนมาส่งค่า 50 มา  ตอนกลับก็ต้องส่งค่า 50 กลับด้วย
                finish();
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {   //คือ ทำงานหลังจากที่แผนที่เกิดขึ้นมาแล้ว
        mMap = googleMap;

//        Crecate Center Map
        if (latADouble != 0) {

            LatLng centerLatLng =new LatLng(latADouble,lngADouble);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centerLatLng,16));  // เลข 16 คือระยะที่ให้สูงจากพื้นเวลาโชว์
            createMarker(centerLatLng);

        } else {
            findLatLng();
        }


        // mMap Controller  ทำให้คลิกบน แผนที่
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();                 // Clear ค่า Marker ของเก่าออก
                createMarker(latLng);

            }
        });


    }  // On Map

    private void createMarker(LatLng centerLatLng) {  //การใส่ Marker

        endLaADouble=centerLatLng.latitude;
        endLnADouble=centerLatLng.longitude;
        Log.d("6SepV1","endLat ==>" + endLnADouble);
        Log.d("6SepV1","endLng ==>" + endLaADouble);

        MarkerOptions markerOptions=new MarkerOptions()
                .position(centerLatLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_marker));   // เปลี่ยน Icon

        mMap.addMarker(markerOptions);


    }


} /// Main Class
