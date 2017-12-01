package fastmoanyapp.fastmoney.service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Usuario on 29/05/2017.
 */

public interface favoriteService {

    @POST("/api/createfavorite")
    @FormUrlEncoded
    Call<JsonObject> createfavorite(@Field("id_job") String id_job);

    @POST("/api/removefavorite")
    @FormUrlEncoded
    Call<JsonObject> removefavorite(@Field("id_favorite") String id_favorite);
}
