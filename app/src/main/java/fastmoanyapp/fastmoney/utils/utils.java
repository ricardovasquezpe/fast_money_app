package fastmoanyapp.fastmoney.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Usuario on 30/05/2017.
 */

public class utils {
    public static final String API_BASE_URL = "http://fastmoneyapi.herokuapp.com/api/";
    public static final String API_BASE_URL_COUNTRIES = "https://restcountries.eu/";
    //public static final String API_BASE_URL = "http://192.168.1.22:8000/api/";

    public static final String TYPE_JOB_HOURLY_RATE = "Hourly Rate";
    public static final String TYPE_JOB_FIXED_PRICE = "Fixed Price";
    public static final String OPTION_ALL = "All";

    public static boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
