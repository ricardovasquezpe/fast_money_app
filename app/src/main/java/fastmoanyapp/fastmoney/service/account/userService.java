package fastmoanyapp.fastmoney.service.account;

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
    Call<String> authenticate(@Field("username") String title,
                              @Field("password") String body);
}
