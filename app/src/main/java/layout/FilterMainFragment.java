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
import android.widget.Spinner;

import fastmoanyapp.fastmoney.R;

/**
 * Created by FTF-ANDREA on 21/11/2017.
 */

public class FilterMainFragment extends DialogFragment{
    Spinner sp_filter_type_job;
    ImageView img_close_filter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filter_main, container, false);
        getDialog().setTitle("Filter Main");

        String [] values = {"All", "Fixed Rate", "Hourly Rate"};
        sp_filter_type_job = (Spinner) rootView.findViewById(R.id.sp_filter_type_job);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp_filter_type_job.setAdapter(adapter);
        img_close_filter = (ImageView) rootView.findViewById(R.id.img_close_filter);
        img_close_filter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

}
