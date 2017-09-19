package fastmoanyapp.fastmoney.service.account;

import com.google.gson.JsonObject;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import static fastmoanyapp.fastmoney.utils.utils.API_BASE_URL;

/**
 * Created by Usuario on 29/05/2017.
 */

public interface userService {

    @POST("/api/authenticate")
    @FormUrlEncoded
    Call<JsonObject> authenticate(@Field("username") String username,
                                  @Field("password") String password);

    @POST("/api/register")
    @FormUrlEncoded
    Call<JsonObject> register(@Field("name") String name,
                              @Field("lastname") String lastname,
                              @Field("username") String username,
                              @Field("password") String password,
                              @Field("confirmpassword") String confirmpassword,
                              @Field("email") String email,
                              @Field("type") String type,
                              @Field("birthdate") String birthdate);
}
