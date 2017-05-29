package fastmoanyapp.fastmoney;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {

    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login.setText("Loading...");
                btn_login.setBackgroundColor(getResources().getColor(R.color.colorWhiteText));
                btn_login.setTextColor(getResources().getColor(R.color.colorHintText));
                btn_login.setEnabled(false);
            }
        });
    }
}
