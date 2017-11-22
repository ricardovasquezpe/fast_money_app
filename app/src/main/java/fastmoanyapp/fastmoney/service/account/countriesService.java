package fastmoanyapp.fastmoney.service.account;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Usuario on 29/05/2017.
 */

public interface countriesService {

    @GET("/rest/v2/all?fields=name")
    @FormUrlEncoded
    Call<JsonObject> allCountries();

}
