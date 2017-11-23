package fastmoanyapp.fastmoney.activity;

import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;

import java.util.ArrayList;
import java.util.List;

import fastmoanyapp.fastmoney.R;
import fastmoanyapp.fastmoney.model.job;
import layout.FilterMainFragment;
import layout.JobInfoAdapter;

public class mainBackup extends FragmentActivity {

    /*private GoogleMap mMap;
    LocationManager locationManager;
    String lattitude, longitude;
    public static final int REQUEST_LOCATION = 99;*/
    LinearLayout ll_filter;
    FilterMainFragment dialogFragment;

    private List<job> jobInfoList = new ArrayList<>();
    private RecyclerView rv_job_list;
    JobInfoAdapter jobInfoAdapter;
    SwipeRefreshLayout srl_refresh_jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialogFragment   = new FilterMainFragment ();
        rv_job_list      = (RecyclerView) findViewById(R.id.rv_job_list);
        srl_refresh_jobs = (SwipeRefreshLayout)  findViewById(R.id.srl_refresh_jobs);

        jobInfoAdapter = new JobInfoAdapter(jobInfoList);

        populateJobs();

        ll_filter = (LinearLayout) findViewById(R.id.ll_filter);
        ll_filter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                dialogFragment.show(fm, "Sample Fragment");
            }
        });

        srl_refresh_jobs.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshJobs();
            }
        });

        //getLocationAction();
        /*btn_current_location = (FloatingActionButton) findViewById(R.id.btn_current_location);
        btn_current_location.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));*/
        /*btn_current_location.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LatLng sydney = new LatLng(-33.852, 151.211);0
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        });*/
    }

    public void refreshJobs(){


        srl_refresh_jobs.setRefreshing(false);
    }

    public void populateJobs(){

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_job_list.setLayoutManager(mLayoutManager);
        rv_job_list.setItemAnimator(new DefaultItemAnimator());
        rv_job_list.setAdapter(jobInfoAdapter);

        /*job j = new job(
                "Id",
                "Title",
                "Description, Description, Description",
                Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR));
        jobInfoList.add(j);
        jobInfoList.add(j);
        jobInfoList.add(j);
        jobInfoList.add(j);*/
        jobInfoAdapter.notifyDataSetChanged();

    }

    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_small);
        LatLng sydney = new LatLng(-33.852, 151.211);
        MarkerOptions markerOptions = new MarkerOptions().position(sydney)
                .title("Marker in Sydney")
                .snippet("snippet snippet snippet snippet snippet...")
                .icon(icon);
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //mMap.setMyLocationEnabled(true);
    }

    protected void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Toast.makeText(this,"Your current location is"+ "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude,Toast.LENGTH_SHORT).show();
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            }else{
                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getLocationAction(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }
    }*/
}