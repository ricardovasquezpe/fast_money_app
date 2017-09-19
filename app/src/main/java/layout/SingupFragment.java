package layout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import fastmoanyapp.fastmoney.R;
import fastmoanyapp.fastmoney.service.account.userService;
import fastmoanyapp.fastmoney.utils.RetrofitClient;
import fastmoanyapp.fastmoney.utils.utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingupFragment extends Fragment {

    EditText et_name_su;
    EditText et_last_name_su;
    EditText et_username_su;
    EditText et_email_su;
    EditText et_password_su;
    EditText et_confirm_password_su;
    EditText et_birthdate_su;
    Button btn_sing_up;
    userService UserService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_singup, container, false);

        btn_sing_up            = (Button) view.findViewById(R.id.btn_sing_up);
        et_name_su             = (EditText) view.findViewById(R.id.et_name_su);
        et_last_name_su        = (EditText) view.findViewById(R.id.et_last_name_su);
        et_username_su         = (EditText) view.findViewById(R.id.et_username_su);
        et_email_su            = (EditText) view.findViewById(R.id.et_email_su);
        et_password_su         = (EditText) view.findViewById(R.id.et_password_su);
        et_confirm_password_su = (EditText) view.findViewById(R.id.et_confirm_password_su);
        et_birthdate_su      = (EditText) view.findViewById(R.id.et_birthdate_su);

        UserService = RetrofitClient.getClient(utils.API_BASE_URL).create(userService.class);
        btn_sing_up.setEnabled(false);
        addChangeListenerFields();

        btn_sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });

        return  view;
    }

    public void doRegister(){
        et_name_su.setEnabled(false);
        et_last_name_su.setEnabled(false);
        et_username_su.setEnabled(false);
        et_email_su.setEnabled(false);
        et_password_su.setEnabled(false);
        et_confirm_password_su.setEnabled(false);
        et_birthdate_su.setEnabled(false);
        btn_sing_up.setEnabled(false);
        btn_sing_up.setText("Loading...");
        btn_sing_up.setBackgroundColor(getResources().getColor(R.color.colorWhiteText));
        btn_sing_up.setTextColor(getResources().getColor(R.color.colorHintText));

        UserService.register(et_name_su.getText().toString(),
                et_last_name_su.getText().toString(),
                et_username_su.getText().toString(),
                et_password_su.getText().toString(),
                et_confirm_password_su.getText().toString(),
                et_email_su.getText().toString(),
                "user",
                et_birthdate_su.getText().toString()).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    Boolean status = Boolean.parseBoolean(response.body().get("status").toString());
                    if(!status){
                        showWrongRegister(response.body().get("data").getAsJsonArray());
                        return;
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("RESPONSE", "Unable to submit post to API.");
            }
        });
    }

    public void singupFieldsComplete(){
        if(!utils.isEmpty(et_name_su) && !utils.isEmpty(et_last_name_su) && !utils.isEmpty(et_username_su) && !utils.isEmpty(et_email_su) && !utils.isEmpty(et_password_su)
                && !utils.isEmpty(et_confirm_password_su) && !utils.isEmpty(et_birthdate_su)){
            btn_sing_up.setText("SIGN UP");
            btn_sing_up.setEnabled(true);
            btn_sing_up.setBackground(getResources().getDrawable(R.drawable.outline_button_full));
            btn_sing_up.setTextColor(getResources().getColor(R.color.colorWhiteText));
        }else{
            btn_sing_up.setText("SIGN UP");
            btn_sing_up.setBackground(getResources().getDrawable(R.drawable.outline_button));
            btn_sing_up.setTextColor(getResources().getColor(R.color.colorWhiteText));
            btn_sing_up.setEnabled(false);
        }
    }

    public void addChangeListenerFields(){
        et_name_su.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                singupFieldsComplete();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        et_last_name_su.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                singupFieldsComplete();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        et_username_su.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                singupFieldsComplete();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        et_email_su.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                singupFieldsComplete();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        et_password_su.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                singupFieldsComplete();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        et_confirm_password_su.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                singupFieldsComplete();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        et_birthdate_su.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                singupFieldsComplete();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    public void showWrongRegister(JsonArray msg){
        AlertDialog alertDialog = new AlertDialog.Builder(getView().getContext()).create();
        alertDialog.setTitle("Invalid Register");
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