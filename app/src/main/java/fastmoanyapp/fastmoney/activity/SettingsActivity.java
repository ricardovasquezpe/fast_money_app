package fastmoanyapp.fastmoney.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import fastmoanyapp.fastmoney.R;
import fastmoanyapp.fastmoney.utils.UserSessionManager;

public class SettingsActivity extends AppCompatActivity {
    LinearLayout nav_bar_setting_search;
    LinearLayout ll_setting_logout;
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();
        session = new UserSessionManager(this.getBaseContext());

        nav_bar_setting_search = (LinearLayout) findViewById(R.id.nav_bar_setting_search);
        ll_setting_logout      = (LinearLayout) findViewById(R.id.ll_setting_logout);

        nav_bar_setting_search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        ll_setting_logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                session.logoutUser();
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
