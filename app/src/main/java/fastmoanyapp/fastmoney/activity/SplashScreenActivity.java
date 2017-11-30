package fastmoanyapp.fastmoney.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import java.util.Timer;
import java.util.TimerTask;
import fastmoanyapp.fastmoney.R;
import fastmoanyapp.fastmoney.utils.UserSessionManager;

public class SplashScreenActivity extends Activity {
    private static final long SPLASH_SCREEN_DELAY = 3000;
    UserSessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);
        session = new UserSessionManager(this.getBaseContext());
        //session.logoutUser();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(!session.isUserLoggedIn()){
                    Intent loginIntent = new Intent().setClass(
                            SplashScreenActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }else {
                    Intent mainIntent = new Intent().setClass(
                            SplashScreenActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
}
