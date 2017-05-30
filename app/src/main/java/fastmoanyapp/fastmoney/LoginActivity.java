package fastmoanyapp.fastmoney;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import fastmoanyapp.fastmoney.service.account.userService;
import fastmoanyapp.fastmoney.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static fastmoanyapp.fastmoney.utils.utils.API_BASE_URL;

public class LoginActivity extends Activity {

    userService UserService;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserService = RetrofitClient.getClient(API_BASE_URL).create(userService.class);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login.setText("Loading...");
                btn_login.setBackgroundColor(getResources().getColor(R.color.colorWhiteText));
                btn_login.setTextColor(getResources().getColor(R.color.colorHintText));
                btn_login.setEnabled(false);

                UserService.authenticate("1", "2").enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if(response.isSuccessful()) {
                            Log.i("RESPONSE", "post submitted to API." + response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("RESPONSE", "Unable to submit post to API.");
                    }
                });

            }
        });
    }
}
