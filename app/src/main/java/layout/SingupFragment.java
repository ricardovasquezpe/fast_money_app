package layout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import fastmoanyapp.fastmoney.R;
import fastmoanyapp.fastmoney.service.userService;
import fastmoanyapp.fastmoney.utils.DateDialog;
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
    DateDialog dialog;

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
        et_birthdate_su        = (EditText) view.findViewById(R.id.et_birthdate_su);
        dialog = new DateDialog();

        //UserService = RetrofitClient.getClient(utils.API_BASE_URL).create(userService.class);
        btn_sing_up.setEnabled(false);
        addChangeListenerFields();

        btn_sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });

        et_birthdate_su.setKeyListener(null);
        btn_sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, "DatePicker");
            }
        });
        et_birthdate_su.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
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
                        showWrongRegister(response.body().get("data"));
                        et_name_su.setEnabled(true);
                        et_last_name_su.setEnabled(true);
                        et_username_su.setEnabled(true);
                        et_email_su.setEnabled(true);
                        et_password_su.setEnabled(true);
                        et_confirm_password_su.setEnabled(true);
                        et_birthdate_su.setEnabled(true);
                        singupFieldsComplete();
                        return;
                    }
                    FragmentManager fm = getFragmentManager();
                    Fragment singup_fragment = fm.findFragmentById(R.id.singup_fragment);
                    Fragment login_fragment  = fm.findFragmentById(R.id.login_fragment);
                    hideFragment(singup_fragment);
                    showFragment(login_fragment);
                    postRegister();
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

    public void showWrongRegister(JsonElement msgs){
        AlertDialog alertDialog = new AlertDialog.Builder(getView().getContext()).create();
        alertDialog.setTitle("Invalid Register");
        String msg = "";
        if(msgs.isJsonArray()){
            JsonArray msgsArray = msgs.getAsJsonArray();
            for(JsonElement obj : msgsArray){
                msg += obj.getAsJsonObject().get("msg").toString().replace("\"", "") + "\n";
            }
        }else{
            msg = msgs.getAsString();
        }

        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Try again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void postRegister(){
        et_name_su.setEnabled(true);
        et_last_name_su.setEnabled(true);
        et_username_su.setEnabled(true);
        et_email_su.setEnabled(true);
        et_password_su.setEnabled(true);
        et_confirm_password_su.setEnabled(true);
        et_birthdate_su.setEnabled(true);
        et_name_su.setText(null);
        et_last_name_su.setText(null);
        et_username_su.setText(null);
        et_email_su.setText(null);
        et_password_su.setText(null);
        et_confirm_password_su.setText(null);
        et_birthdate_su.setText(null);
        singupFieldsComplete();
    }

    public void hideFragment(final Fragment fragment){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.hide(fragment);
        ft.commit();
    }

    public void showFragment(final Fragment fragment){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.show(fragment);
        ft.commit();
    }
}