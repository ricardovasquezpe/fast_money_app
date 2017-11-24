package fastmoanyapp.fastmoney.activity;

import android.app.DialogFragment;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fastmoanyapp.fastmoney.R;
import fastmoanyapp.fastmoney.model.job;
import fastmoanyapp.fastmoney.service.jobService;
import fastmoanyapp.fastmoney.utils.RetrofitClient;
import fastmoanyapp.fastmoney.utils.TransparentProgressDialog;
import fastmoanyapp.fastmoney.utils.UserSessionManager;
import fastmoanyapp.fastmoney.utils.utils;
import layout.FilterMainFragment;
import layout.JobInfoAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends FragmentActivity implements FilterMainFragment.filterJobsListener{
    LinearLayout ll_filter;
    FilterMainFragment dialogFragment;
    List<job> jobInfoList = new ArrayList<>();
    RecyclerView rv_job_list;
    JobInfoAdapter jobInfoAdapter;
    SwipeRefreshLayout srl_refresh_jobs;
    public TransparentProgressDialog progress;

    jobService JobService;
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //session = new UserSessionManager(this.getBaseContext());
        Map<String, String> headers = new HashMap<>();
        headers.put("x-access-token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyIkX18iOnsic3RyaWN0TW9kZSI6dHJ1ZSwic2VsZWN0ZWQiOnsiX2lkIjoxLCJ1c2VybmFtZSI6MSwicGFzc3dvcmQiOjF9LCJnZXR0ZXJzIjp7fSwid2FzUG9wdWxhdGVkIjpmYWxzZSwiYWN0aXZlUGF0aHMiOnsicGF0aHMiOnsiZW1haWwiOiJyZXF1aXJlIiwidXNlcm5hbWUiOiJpbml0IiwicGFzc3dvcmQiOiJpbml0IiwiX2lkIjoiaW5pdCJ9LCJzdGF0ZXMiOnsiaWdub3JlIjp7fSwiZGVmYXVsdCI6e30sImluaXQiOnsicGFzc3dvcmQiOnRydWUsInVzZXJuYW1lIjp0cnVlLCJfaWQiOnRydWV9LCJtb2RpZnkiOnt9LCJyZXF1aXJlIjp7ImVtYWlsIjp0cnVlfX0sInN0YXRlTmFtZXMiOlsicmVxdWlyZSIsIm1vZGlmeSIsImluaXQiLCJkZWZhdWx0IiwiaWdub3JlIl19LCJlbWl0dGVyIjp7ImRvbWFpbiI6bnVsbCwiX2V2ZW50cyI6e30sIl9ldmVudHNDb3VudCI6MCwiX21heExpc3RlbmVycyI6MH19LCJpc05ldyI6ZmFsc2UsIl9kb2MiOnsicGFzc3dvcmQiOiIxMjM0NTYiLCJ1c2VybmFtZSI6InJpa2FyZG8zMDgiLCJfaWQiOiI1OTJhMDIyNjk2Yjc5YjI4ZmNlMWJhNzgifSwiJGluaXQiOnRydWUsImlhdCI6MTUxMTUyOTYzOSwiZXhwIjoxNTExNjE2MDM5fQ.BoUBvReNnGi9j0Wb1dCKXjFLdEf6Jn_kCqcpyIQnzbo");
        JobService = RetrofitClient.getClient(utils.API_BASE_URL, headers).create(jobService.class);

        dialogFragment   = new FilterMainFragment ();
        rv_job_list      = (RecyclerView) findViewById(R.id.rv_job_list);
        srl_refresh_jobs = (SwipeRefreshLayout)  findViewById(R.id.srl_refresh_jobs);

        jobInfoAdapter = new JobInfoAdapter(jobInfoList);
        progress = new TransparentProgressDialog(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_job_list.setLayoutManager(mLayoutManager);
        rv_job_list.setItemAnimator(new DefaultItemAnimator());
        rv_job_list.setAdapter(jobInfoAdapter);

        populateJobs();

        ll_filter = (LinearLayout) findViewById(R.id.ll_filter);
        ll_filter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                dialogFragment.show(fm, "Filter Dialog");
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
        progress.show();
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
                    addDataToJobs(jobs);
                }
                jobInfoAdapter.notifyDataSetChanged();
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("RESPONSE", "Unable to submit post to API.");
            }
        });

        //jobInfoAdapter.notifyDataSetChanged();
    }

    public void filterJobs(String description, String typeJob, String country) {
        Log.e("RESPONSE", description);
        Log.e("RESPONSE", typeJob);
        Log.e("RESPONSE", country);
        progress.show();
        JobService.filterjobs(description, typeJob, country).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    Boolean status = Boolean.parseBoolean(response.body().get("status").toString());
                    if(!status){
                        //NO DATA
                        return;
                    }

                    JsonArray jobs = response.body().get("data").getAsJsonArray();
                    addDataToJobs(jobs);
                    jobInfoAdapter.notifyDataSetChanged();
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("RESPONSE", "Unable to submit post to API.");
            }
        });
    }

    public void addDataToJobs(JsonArray jobs){
        jobInfoList.clear();
        for (JsonElement item : jobs) {
            JsonObject itemObj = item.getAsJsonObject();

            String id          = itemObj.get("_id").getAsString();
            String title       = itemObj.get("title").getAsString();
            String description = itemObj.get("description").getAsString();

            JsonObject paymentObj = itemObj.get("payment").getAsJsonArray().get(0).getAsJsonObject();
            String jobType     = paymentObj.get("type").getAsString();

            String payment = "$" + paymentObj.get("amount").getAsString();
            if(jobType.equals(utils.TYPE_JOB_HOURLY_RATE)){
                payment += "/Hr";
            }

            String paymentType = paymentObj.get("method").getAsString();

            JsonObject locObj  = itemObj.get("location").getAsJsonArray().get(0).getAsJsonObject();
            String country     = locObj.get("country").getAsString();
            String city        = locObj.get("city").getAsString();

            job j = new job(id, title, description, jobType, payment, paymentType, country, city, Utils.createInterpolator(Utils.ACCELERATE_DECELERATE_INTERPOLATOR));
            jobInfoList.add(j);
        }
    }
}