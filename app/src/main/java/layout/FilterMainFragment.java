package layout;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.appyvet.rangebar.RangeBar;

import fastmoanyapp.fastmoney.R;
import fastmoanyapp.fastmoney.activity.MainActivity;

public class FilterMainFragment extends DialogFragment{
    Spinner sp_filter_type_job;
    Spinner sp_filter_country;
    ImageView img_close_filter;
    LinearLayout ln_filter_reset;
    LinearLayout ll_filter_filter;
    RangeBar rb_filter_price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filter_main, container, false);
        img_close_filter   = (ImageView) rootView.findViewById(R.id.img_close_filter);
        sp_filter_type_job = (Spinner) rootView.findViewById(R.id.sp_filter_type_job);
        sp_filter_country  = (Spinner) rootView.findViewById(R.id.sp_filter_country);
        rb_filter_price    = (RangeBar) rootView.findViewById(R.id.rb_filter_price);
        ln_filter_reset    = (LinearLayout) rootView.findViewById(R.id.ln_filter_reset);
        ll_filter_filter   = (LinearLayout) rootView.findViewById(R.id.ll_filter_filter);

        ln_filter_reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sp_filter_type_job.setSelection(0);
                sp_filter_country.setSelection(0);
                rb_filter_price.setRangePinsByValue(10, 100);
                //rb_filter_price.setTickEnd(100);
            }
        });

        ll_filter_filter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String typeJob = sp_filter_type_job.getSelectedItem().toString();
                String country = sp_filter_country.getSelectedItem().toString();
                String left    = rb_filter_price.getLeftPinValue();
                String right   = rb_filter_price.getRightPinValue();
            }
        });

        img_close_filter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        String [] valuesJobType = {"All", "Fixed Rate", "Hourly Rate"};
        ArrayAdapter<String> adapterJobType = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesJobType);
        adapterJobType.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp_filter_type_job.setAdapter(adapterJobType);

        String [] valuesCountries = {"All", "Perú", "Argentina"};
        ArrayAdapter<String> adapterCountries = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valuesCountries);
        adapterCountries.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp_filter_country.setAdapter(adapterCountries);

        return rootView;
    }

}
