package fastmoanyapp.fastmoney.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fastmoanyapp.fastmoney.R;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;

public class DetailJobActivity extends AppCompatActivity{

    BannerSlider detail_job_images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job);
        getSupportActionBar().hide();

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
}