package layout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import fastmoanyapp.fastmoney.R;
import java.util.List;
import fastmoanyapp.fastmoney.model.job;

public class JobInfoAdapter extends RecyclerView.Adapter<JobInfoAdapter.ViewHolder> {
    private final List<job> data;
    private Context context;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public JobInfoAdapter(List<job> data) {
        this.data = data;
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.job_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final job item = data.get(position);
        holder.setIsRecyclable(false);

        holder.item_job_title.setText(item.getTitle());
        holder.item_job_description_short.setText(item.getDescription());
        holder.item_job_type.setText(item.getJobType());
        holder.item_job_payment.setText(item.getPayment());
        holder.item_job_payment_type.setText(item.getPaymentType());
        holder.item_job_location.setText(item.getCity() + ", " + item.getCountry());

        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setInterpolator(item.interpolator);
        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(holder.item_job_button_expand, 0f, 180f).start();
                expandState.put(position, true);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(holder.item_job_button_expand, 180f, 0f).start();
                expandState.put(position, false);
            }
        });

        holder.item_job_button_expand.setRotation(expandState.get(position) ? 180f : 0f);
        holder.item_job_button_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout);
            }
        });
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_job_title;
        public TextView item_job_description_short;
        public TextView item_job_type;
        public TextView item_job_payment;
        public TextView item_job_payment_type;
        public TextView item_job_location;
        public RelativeLayout item_job_button_expand;
        public ExpandableLinearLayout expandableLayout;

        public ViewHolder(View v) {
            super(v);
            item_job_title             = (TextView) v.findViewById(R.id.item_job_title);
            item_job_description_short = (TextView) v.findViewById(R.id.item_job_description_short);
            item_job_type              = (TextView) v.findViewById(R.id.item_job_type);
            item_job_payment           = (TextView) v.findViewById(R.id.item_job_payment);
            item_job_payment_type      = (TextView) v.findViewById(R.id.item_job_payment_type);
            item_job_location          = (TextView) v.findViewById(R.id.item_job_location);
            item_job_button_expand     = (RelativeLayout) v.findViewById(R.id.item_job_button_expand);
            expandableLayout           = (ExpandableLinearLayout) v.findViewById(R.id.expandableLayout_expand);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}
