package fastmoanyapp.fastmoney.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import fastmoanyapp.fastmoney.R;

public class LoginActivity extends FragmentActivity {
    TextView tv_singup;
    TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_login  = (TextView) findViewById(R.id.tv_login);
        tv_singup = (TextView) findViewById(R.id.tv_signup);

        FragmentManager fm = getFragmentManager();
        Fragment singup_fragment = fm.findFragmentById(R.id.singup_fragment);
        Fragment login_fragment  = fm.findFragmentById(R.id.login_fragment);
        addShowHideFragment(tv_login, tv_singup, login_fragment, singup_fragment);
        hideFragment(singup_fragment);
        showFragment(login_fragment);
    }

    public void addShowHideFragment(TextView tv_login, TextView tv_singup, final Fragment fragment_login, final Fragment fragment_signup) {
        tv_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.show(fragment_login);
                ft.hide(fragment_signup);
                TextView tv_login_inter  = (TextView) findViewById(R.id.tv_login);
                TextView tv_singup_inter  = (TextView) findViewById(R.id.tv_signup);
                tv_login_inter.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_singup_inter.setTextColor(getResources().getColor(R.color.colorWhiteText));
                ft.commit();
            }
        });

        tv_singup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.hide(fragment_login);
                ft.show(fragment_signup);
                TextView tv_singup_inter  = (TextView) findViewById(R.id.tv_signup);
                TextView tv_login_inter  = (TextView) findViewById(R.id.tv_login);
                tv_singup_inter.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_login_inter.setTextColor(getResources().getColor(R.color.colorWhiteText));
                ft.commit();
            }
        });
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