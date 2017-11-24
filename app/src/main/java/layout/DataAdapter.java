package layout;

import android.animation.ObjectAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.List;

import fastmoanyapp.fastmoney.R;
import fastmoanyapp.fastmoney.model.job;

public class DataAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<job> data;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public DataAdapter(List<job> jobs, RecyclerView recyclerView) {
        data = jobs;
        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.job_row, parent, false);

            vh = new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.load_more, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            final job item = data.get(position);
            holder.setIsRecyclable(false);

            ((ViewHolder) holder).item_job_title.setText(item.getTitle());
            ((ViewHolder) holder).item_job_description_short.setText(item.getDescription());
            ((ViewHolder) holder).item_job_type.setText(item.getJobType());
            ((ViewHolder) holder).item_job_payment.setText(item.getPayment());
            ((ViewHolder) holder).item_job_payment_type.setText(item.getPaymentType());
            ((ViewHolder) holder).item_job_location.setText(item.getCity() + ", " + item.getCountry());

            ((ViewHolder) holder).expandableLayout.setInRecyclerView(true);
            ((ViewHolder) holder).expandableLayout.setInterpolator(item.interpolator);
            ((ViewHolder) holder).expandableLayout.setExpanded(expandState.get(position));
            ((ViewHolder) holder).expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    createRotateAnimator(((ViewHolder) holder).item_job_button_expand, 0f, 180f).start();
                    expandState.put(position, true);
                }

                @Override
                public void onPreClose() {
                    createRotateAnimator(((ViewHolder) holder).item_job_button_expand, 180f, 0f).start();
                    expandState.put(position, false);
                }
            });

            ((ViewHolder) holder).item_job_button_expand.setRotation(expandState.get(position) ? 180f : 0f);
            ((ViewHolder) holder).item_job_button_expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    onClickButton(((ViewHolder) holder).expandableLayout);
                }
            });

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    //
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

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.loading_more_jobs);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}
