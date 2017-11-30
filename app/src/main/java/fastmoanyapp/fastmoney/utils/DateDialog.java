package fastmoanyapp.fastmoney.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.DatePicker;
import android.widget.EditText;

import fastmoanyapp.fastmoney.R;
import layout.SingupFragment;

/**
 * Created by FTF-ANDREA on 20/11/2017.
 */

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    EditText birthDate;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Dialog onCreateDialog(Bundle saveInstanceState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR) - 18;
        int month = c.get(Calendar.MONDAY);
        int day = c.get(Calendar.DAY_OF_MONTH);
        birthDate = (EditText) getActivity().findViewById(R.id.et_birthdate_su);
        return new DatePickerDialog(getActivity(), R.style.datepicker, this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        birthDate.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
    }
}
