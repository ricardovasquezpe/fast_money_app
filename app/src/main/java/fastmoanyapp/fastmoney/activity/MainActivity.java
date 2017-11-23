package fastmoanyapp.fastmoney.activity;

import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.github.aakira.expandablelayout.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import fastmoanyapp.fastmoney.R;
import fastmoanyapp.fastmoney.model.job;
import fastmoanyapp.fastmoney.service.jobService;
import fastmoanyapp.fastmoney.utils.RetrofitClient;
import fastmoanyapp.fastmoney.utils.utils;
import layout.FilterMainFragment;
import layout.JobInfoAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends FragmentActivity {
    LinearLayout ll_filter;
    FilterMainFragment dialogFragment;
    List<job> jobInfoList = new ArrayList<>();
    RecyclerView rv_job_list;
    JobInfoAdapter jobInfoAdapter;
    SwipeRefreshLayout srl_refresh_jobs;

    jobService JobService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JobService = RetrofitClient.getClient(utils.API_BASE_URL).create(jobService.class);

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
    }

    public void refreshJobs(){


        srl_refresh_jobs.setRefreshing(false);
    }

    public void populateJobs(){
        JobService.alljobs().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    Boolean status = Boolean.parseBoolean(response.body().get("status").toString());
                    if(!status){
                        //NO DATA
                        return;
                    }

                    JsonArray jobs = response.body().get("data").getAsJsonArray();
                    for (JsonElement item : jobs) {
                        JsonObject itemObj = item.getAsJsonObject();

                        String id          = itemObj.get("_id").getAsString();
                        String title       = itemObj.get("title").getAsString();
                        String description = itemObj.get("description").getAsString();

                        JsonObject paymentObj = itemObj.get("payment").getAsJsonArray().get(0).getAsJsonObject();
                        String jobType     = paymentObj.get("type").getAsString();
                        String payment     = "10/hr";
                        String paymentType = paymentObj.get("method").getAsString();

                        JsonObject locObj  = itemObj.get("location").getAsJsonArray().get(0).getAsJsonObject();
                        String country     = locObj.get("country").getAsString();
                        String city        = locObj.get("city").getAsString();

                        job j = new job(id, title, description, jobType, payment, paymentType, country, city, Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR));
                        jobInfoList.add(j);
                    }
                    jobInfoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("RESPONSE", "Unable to submit post to API.");
            }
        });

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
}