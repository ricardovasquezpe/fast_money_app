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
    private SparseBooleanArray expandState = new SparseBooleanArray();

    /*
    * isLoading - to set the remote loading and complete status to fix back to back load more call
    * isMoreDataAvailable - to set whether more data from server available or not.
    * It will prevent useless load more request even after all the server data loaded
    * */


    public DataAdapter(Context context, List<job> jobs) {
        this.context = context;
        this.jobs = jobs;
        for (int i = 0; i < jobs.size(); i++) {
            expandState.append(i, false);
        }
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
            ((MovieHolder)holder).item_job_type.setText(item.getJobType());
            ((MovieHolder)holder).item_job_payment.setText(item.getPayment());
            ((MovieHolder)holder).item_job_payment_type.setText(item.getPaymentType());
            ((MovieHolder)holder).item_job_location.setText(item.getCity() + ", " + item.getCountry());

            ((MovieHolder)holder).expandableLayout.setInRecyclerView(true);
            ((MovieHolder)holder).expandableLayout.setInterpolator(item.interpolator);
            ((MovieHolder)holder).expandableLayout.setExpanded(expandState.get(position));
            ((MovieHolder)holder).expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    createRotateAnimator(((MovieHolder)holder).item_job_button_expand, 0f, 180f).start();
                    expandState.put(position, true);
                }

                @Override
                public void onPreClose() {
                    createRotateAnimator(((MovieHolder)holder).item_job_button_expand, 180f, 0f).start();
                    expandState.put(position, false);
                }
            });

            ((MovieHolder)holder).item_job_button_expand.setRotation(expandState.get(position) ? 180f : 0f);
            ((MovieHolder)holder).item_job_button_expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    onClickButton(((MovieHolder)holder).expandableLayout);
                }
            });
        }
        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {
        return jobs.get(position).getTypeLoad();
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    /* VIEW HOLDERS */

    static class MovieHolder extends RecyclerView.ViewHolder{
        public TextView item_job_title;
        public TextView item_job_description_short;
        public TextView item_job_type;
        public TextView item_job_payment;
        public TextView item_job_payment_type;
        public TextView item_job_location;
        public RelativeLayout item_job_button_expand;
        public ExpandableLinearLayout expandableLayout;

        public MovieHolder(View v) {
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

        /*void bindData(MovieModel movieModel){
            tvTitle.setText(movieModel.title);
            tvRating.setText("Rating "+movieModel.rating);
        }*/
    }

    static class LoadHolder extends RecyclerView.ViewHolder{
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
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
