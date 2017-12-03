package layout;

import android.animation.ObjectAnimator;
import android.content.Context;
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
    public final int TYPE_MOVIE = 0;
    public final int TYPE_LOAD = 1;

    static Context context;
    List<job> jobs;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    public DataAdapter(Context context, List<job> jobs) {
        this.context = context;
        this.jobs = jobs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType==TYPE_MOVIE){
            return new MovieHolder(inflater.inflate(R.layout.job_row,parent,false));
        }else{
            return new LoadHolder(inflater.inflate(R.layout.load_more,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if(getItemViewType(position)==TYPE_MOVIE){
            final job item = jobs.get(position);
            holder.setIsRecyclable(false);

            ((MovieHolder)holder).item_job_title.setText(item.getTitle());
            ((MovieHolder)holder).item_job_description_short.setText(item.getDescription());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return jobs.get(position).getTypeLoad();
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    /* VIEW HOLDERS */

    static class MovieHolder extends RecyclerView.ViewHolder{
        public TextView item_job_title;
        public TextView item_job_description_short;

        public MovieHolder(View v) {
            super(v);
            item_job_title             = (TextView) v.findViewById(R.id.item_job_title);
            item_job_description_short = (TextView) v.findViewById(R.id.item_job_description_short);
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder{
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
