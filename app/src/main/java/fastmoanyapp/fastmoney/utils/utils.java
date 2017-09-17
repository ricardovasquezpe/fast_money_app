package fastmoanyapp.fastmoney.utils;

import android.widget.EditText;

/**
 * Created by Usuario on 30/05/2017.
 */

public class utils {
    //public static final String API_BASE_URL = "http://fastmoneyapi.herokuapp.com/api/";
    public static final String API_BASE_URL = "http://192.168.1.27:8000/api/";

    public static boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }
}
