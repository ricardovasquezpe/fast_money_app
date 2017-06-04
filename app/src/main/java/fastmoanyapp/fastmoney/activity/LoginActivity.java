package fastmoanyapp.fastmoney.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import fastmoanyapp.fastmoney.R;
import fastmoanyapp.fastmoney.service.account.userService;
import fastmoanyapp.fastmoney.utils.RetrofitClient;
import fastmoanyapp.fastmoney.utils.UserSessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import fastmoanyapp.fastmoney.utils.utils;

public class LoginActivity extends FragmentActivity {

    userService UserService;
    Button btn_login;
    EditText et_email;
    EditText et_password;

    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new UserSessionManager(getBaseContext());
        UserService = RetrofitClient.getClient(utils.API_BASE_URL).create(userService.class);

        btn_login   = (Button) findViewById(R.id.btn_login);
        et_email    = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_login.setEnabled(false);

        et_email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                emailPasswordComplete();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        et_password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                emailPasswordComplete();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_email.setEnabled(false);
                et_password.setEnabled(false);
                btn_login.setEnabled(false);
                btn_login.setText("Loading...");
                btn_login.setBackgroundColor(getResources().getColor(R.color.colorWhiteText));
                btn_login.setTextColor(getResources().getColor(R.color.colorHintText));

                UserService.authenticate(et_email.getText().toString(), et_password.getText().toString()).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()) {
                            Boolean status = Boolean.parseBoolean(response.body().get("status").toString());
                            if(!status){
                                btn_login.setText("SIGN IN");
                                btn_login.setBackground(getResources().getDrawable(R.drawable.outline_button));
                                btn_login.setTextColor(getResources().getColor(R.color.colorWhiteText));
                                et_email.setEnabled(true);
                                et_password.setEnabled(true);
                                btn_login.setEnabled(true);
                                showWrongLogin();
                                return;
                            }

                            session.createUserLoginSession(response.body().get("data").getAsJsonObject().get("username").toString(), response.body().get("data").getAsJsonObject().get("password").toString(), response.body().get("token").toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.e("RESPONSE", "Unable to submit post to API.");
                    }
                });

            }
        });
    }

    public void emailPasswordComplete(){
        if(!utils.isEmpty(et_email) && !utils.isEmpty(et_password)){
            btn_login.setText("SIGN IN");
            btn_login.setEnabled(true);
            btn_login.setBackground(getResources().getDrawable(R.drawable.outline_button_full));
            btn_login.setTextColor(getResources().getColor(R.color.colorWhiteText));
        }else{
            btn_login.setText("SIGN IN");
            btn_login.setBackground(getResources().getDrawable(R.drawable.outline_button));
            btn_login.setTextColor(getResources().getColor(R.color.colorWhiteText));
        }
    }

    public void showWrongLogin(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Contraseña incorrecta");
        alertDialog.setMessage("The user or password are not correct. Try again");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Try again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
