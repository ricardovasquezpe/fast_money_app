package fastmoanyapp.fastmoney.service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Usuario on 29/05/2017.
 */

public interface jobService {

    /*@POST("/api/alljobs")
    @FormUrlEncoded
    Call<JsonObject> alljobs(@Field("lastdate") String lastdate);*/

    @POST("/api/filterjobs")
    @FormUrlEncoded
    Call<JsonObject> filterjobs(@Field("lastdate") String lastdate,
                                @Field("description") String description,
                                @Field("type") String type,
                                @Field("country") String country);

    @POST("/api/jobdetails")
    @FormUrlEncoded
    Call<JsonObject> jobdetails(@Field("id_job") String id_job);

}
