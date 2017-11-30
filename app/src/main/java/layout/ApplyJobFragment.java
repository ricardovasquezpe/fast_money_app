package layout;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import fastmoanyapp.fastmoney.R;

public class ApplyJobFragment extends DialogFragment{
    ImageView iv_apply_job_close;
    EditText et_apply_job_comment;
    LinearLayout ll_apply_job_send_apply;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_apply_job, container, false);
        iv_apply_job_close      = (ImageView) rootView.findViewById(R.id.iv_apply_job_close);
        et_apply_job_comment    = (EditText) rootView.findViewById(R.id.et_apply_job_comment);
        ll_apply_job_send_apply = (LinearLayout) rootView.findViewById(R.id.ll_apply_job_send_apply);

        ll_apply_job_send_apply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String comment = et_apply_job_comment.getText().toString();
                sendApplyListener listener = (sendApplyListener) getActivity();
                listener.applyListener(comment);
                dismiss();
            }
        });

        iv_apply_job_close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

    public interface sendApplyListener {
        void applyListener(String comment);
    }

}
