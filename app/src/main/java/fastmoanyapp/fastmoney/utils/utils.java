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
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static String differenceDates(Date d1,Date d2){
        String diffText = "";
        try {
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if(diffDays > 0){
                diffText = diffDays + " day(s) ago";
            }else if(diffHours > 0){
                diffText = diffHours + " Hour(s) ago";
            }else if(diffMinutes > 0){
                diffText = diffMinutes + " Minute(s) ago";
            }else if(diffSeconds > 0){
                diffText = diffSeconds + " Second(s) ago";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return diffText;
    }
}
