package fastmoanyapp.fastmoney.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.Utils;
import com.google.android.gms.vision.text.Text;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import fastmoanyapp.fastmoney.R;
import fastmoanyapp.fastmoney.model.job;
import fastmoanyapp.fastmoney.service.bidService;
import fastmoanyapp.fastmoney.service.favoriteService;
import fastmoanyapp.fastmoney.service.jobService;
import fastmoanyapp.fastmoney.utils.RetrofitClient;
import fastmoanyapp.fastmoney.utils.TransparentProgressDialog;
import fastmoanyapp.fastmoney.utils.UserSessionManager;
import fastmoanyapp.fastmoney.utils.utils;
import layout.ApplyJobFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;

public class DetailJobActivity extends AppCompatActivity implements ApplyJobFragment.sendApplyListener{

    //BannerSlider detail_job_images;
    TransparentProgressDialog progress;
    ImageView iv_detaild_job_back;
    ImageView iv_detaild_job_favorite;
    job jobDetailObject;
    TextView detail_job_title;
    TextView detail_job_posted_time;
    TextView detail_job_details;
    TextView detail_job_requirements;
    TextView detail_job_payment_type;
    TextView detail_job_payment_rate;
    TextView detail_job_payment_duration;
    TextView detail_job_payment_method;
    TextView detail_job_country;
    TextView detail_job_city;
    TextView detail_job_street_name;
    TextView detail_job_see_on_gmap;
    LinearLayout detail_job_apply_job;
    ApplyJobFragment applyJobFragment;
    TextView detail_job_apply_job_text;

    UserSessionManager session;
    jobService JobService;
    bidService BidService;
    favoriteService FavoriteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job);
        getSupportActionBar().hide();

        session = new UserSessionManager(this.getBaseContext());
        Map<String, String> headers = new HashMap<>();
        headers.put("x-access-token", session.getSessionToken());
        JobService      = RetrofitClient.getClient(utils.API_BASE_URL, headers).create(jobService.class);
        BidService      = RetrofitClient.getClient(utils.API_BASE_URL, headers).create(bidService.class);
        FavoriteService = RetrofitClient.getClient(utils.API_BASE_URL, headers).create(favoriteService.class);

        applyJobFragment = new ApplyJobFragment();

        progress = new TransparentProgressDialog(this);
        progress.show();

        iv_detaild_job_back         = (ImageView) findViewById(R.id.iv_detaild_job_back);
        iv_detaild_job_favorite     = (ImageView) findViewById(R.id.iv_detaild_job_favorite);
        detail_job_title            = (TextView)  findViewById(R.id.detail_job_title);
        detail_job_posted_time      = (TextView)  findViewById(R.id.detail_job_posted_time);
        detail_job_details          = (TextView)  findViewById(R.id.detail_job_details);
        detail_job_requirements     = (TextView)  findViewById(R.id.detail_job_requirements);
        detail_job_payment_type     = (TextView)  findViewById(R.id.detail_job_payment_type);
        detail_job_payment_rate     = (TextView)  findViewById(R.id.detail_job_payment_rate);
        detail_job_payment_duration = (TextView)  findViewById(R.id.detail_job_payment_duration);
        detail_job_payment_method   = (TextView)  findViewById(R.id.detail_job_payment_method);
        detail_job_country          = (TextView)  findViewById(R.id.detail_job_country);
        detail_job_city             = (TextView)  findViewById(R.id.detail_job_city);
        detail_job_street_name      = (TextView)  findViewById(R.id.detail_job_street_name);
        detail_job_see_on_gmap      = (TextView)  findViewById(R.id.detail_job_see_on_gmap);
        detail_job_apply_job        = (LinearLayout)  findViewById(R.id.detail_job_apply_job);
        detail_job_apply_job_text   = (TextView)  findViewById(R.id.detail_job_apply_job_text);

        iv_detaild_job_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DetailJobActivity.super.onBackPressed();
            }
        });

        iv_detaild_job_favorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                favoriteJob();
            }
        });

        detail_job_see_on_gmap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String strUri = "http://maps.google.com/maps?q=loc:" + jobDetailObject.getGeoLocation().get(0).getAsString() + "," + jobDetailObject.getGeoLocation().get(1).getAsString() + " (" + jobDetailObject.getTitle() + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });

        Bundle b = getIntent().getExtras();
        String id = b.getString("JOB_ID_CLICKED");
        getJobDetails(id);

        detail_job_apply_job.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                applyJobFragment.show(fm, "Apply Job Dialog");
            }
        });

        /*BannerSlider bannerSlider = (BannerSlider) findViewById(R.id.detail_job_images);
        List<Banner> banners=new ArrayList<>();
        //add banner using image url
        banners.add(new RemoteBanner("https://cdn0.tnwcdn.com/wp-content/blogs.dir/1/files/2015/07/freelancer.jpg"));
        banners.add(new RemoteBanner("https://cdn0.tnwcdn.com/wp-content/blogs.dir/1/files/2015/07/freelancer.jpg"));
        banners.add(new RemoteBanner("https://cdn0.tnwcdn.com/wp-content/blogs.dir/1/files/2015/07/freelancer.jpg"));
        //add banner using resource drawable
        bannerSlider.setBanners(banners);

        bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(DetailJobActivity.this, "Banner with position " + String.valueOf(position) + " clicked!", Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    public void getJobDetails(String jobId){
        JobService.jobdetails(jobId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    Boolean status = Boolean.parseBoolean(response.body().get("status").toString());
                    if(!status){
                        return;
                    }

                    JsonObject itemObj = response.body().get("data").getAsJsonObject();

                    String id          = itemObj.get("_id").getAsString();
                    String title       = itemObj.get("title").getAsString();
                    String description = itemObj.get("description").getAsString();

                    JsonObject paymentObj = itemObj.get("payment").getAsJsonArray().get(0).getAsJsonObject();
                    String jobType     = paymentObj.get("type").getAsString();
                    String duration    = paymentObj.get("duration").getAsString();

                    String payment = "$" + paymentObj.get("amount").getAsString();
                    if(jobType.equals(utils.TYPE_JOB_HOURLY_RATE)){
                        payment += "/Hr";
                    }

                    String paymentType = paymentObj.get("method").getAsString();

                    JsonObject locObj  = itemObj.get("location").getAsJsonArray().get(0).getAsJsonObject();
                    String country     = locObj.get("country").getAsString();
                    String city        = locObj.get("city").getAsString();
                    String streetname  = locObj.get("streetName").getAsString();

                    String createdAt   = itemObj.get("created_at").getAsString();
                    JsonArray req      = itemObj.get("requirements").getAsJsonArray();
                    JsonArray geo      = itemObj.get("location_geo").getAsJsonArray();

                    jobDetailObject = new job(id, title, description, jobType, payment, paymentType, country, city, createdAt, duration, req, geo, streetname);
                    setDataToView();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("RESPONSE", "Unable to submit post to API.");
            }
        });
    }

    public void setDataToView(){
        detail_job_title.setText(jobDetailObject.getTitle());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date dateJob = sdf.parse(jobDetailObject.getCreatedAt());
            Date currentDate = new Date();
            String diff = utils.differenceDates(currentDate, dateJob);
            detail_job_posted_time.setText("(Posted " + diff + ")");
            /*sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
            String dateString = sdf.format(date);*/
        }catch (Exception e){}

        detail_job_details.setText(jobDetailObject.getDescription());
        int i = 1;
        String reqs = "";
        for (JsonElement item : jobDetailObject.getRequirements()) {
            reqs += i+". "+item.getAsString()+"\n";
            i++;
        }
        reqs = reqs.substring(0, reqs.length() - 2);
        detail_job_requirements.setText(reqs);
        detail_job_payment_type.setText(jobDetailObject.getJobType());
        detail_job_payment_rate.setText(jobDetailObject.getPayment());
        detail_job_payment_duration.setText(jobDetailObject.getDuration());
        detail_job_payment_method.setText(jobDetailObject.getPaymentType());
        detail_job_country.setText(jobDetailObject.getCountry());
        detail_job_city.setText(jobDetailObject.getCity());
        detail_job_street_name.setText(jobDetailObject.getStreetName());
        progress.dismiss();
    }

    @Override
    public void applyListener(String comment) {
        BidService.createbid(jobDetailObject.getId(), comment).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    Boolean status = Boolean.parseBoolean(response.body().get("status").toString());
                    if(!status){
                        Toast.makeText(getApplicationContext(), "Please Try Again...Sorry for the inconveniences", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    onSuccessbidSent();
                    Toast.makeText(getApplicationContext(), "Your Bid was sent! Good Luck!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("RESPONSE", "Unable to submit post to API.");
            }
        });
    }

    public void onSuccessbidSent(){
        detail_job_apply_job_text.setText("Bid Sent!");
        detail_job_apply_job.setBackgroundColor(Color.parseColor("#b4b4b4"));
        detail_job_apply_job.setOnClickListener(null);
    }

    public void favoriteJob(){
        FavoriteService.createfavorite(jobDetailObject.getId()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    Boolean status = Boolean.parseBoolean(response.body().get("status").toString());
                    if(!status){
                        Toast.makeText(getApplicationContext(), "Please Try Again...Sorry for the inconveniences", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    iv_detaild_job_favorite.setImageResource(R.drawable.ic_favorite_black_48dp);
                    Toast.makeText(getApplicationContext(), "Job added on your favorites", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("RESPONSE", "Unable to submit post to API.");
            }
        });
    }
}