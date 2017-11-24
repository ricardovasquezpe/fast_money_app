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

    @GET("/api/alljobs")
    Call<JsonObject> alljobs();

    @POST("/api/filterjobs")
    @FormUrlEncoded
    Call<JsonObject> filterjobs(@Field("description") String description,
                                @Field("type") String type,
                                @Field("country") String country);

}
