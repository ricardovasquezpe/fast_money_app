package layout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fastmoanyapp.fastmoney.R;
import java.util.List;
import fastmoanyapp.fastmoney.model.job;

public class JobInfoAdapter extends RecyclerView.Adapter<JobInfoAdapter.ViewHolder> {
    private final List<job> data;
    private Context context;

    public JobInfoAdapter(List<job> data) {
        this.data = data;
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
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_job_title;
        public TextView item_job_description_short;

        public ViewHolder(View v) {
            super(v);
            item_job_title             = (TextView) v.findViewById(R.id.item_job_title);
            item_job_description_short = (TextView) v.findViewById(R.id.item_job_description_short);
        }
    }
}
