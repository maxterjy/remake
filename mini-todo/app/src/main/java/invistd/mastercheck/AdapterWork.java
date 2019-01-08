package invistd.mastercheck;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;


public class AdapterWork extends RecyclerView.Adapter<ViewHolderWork> {
    int colors[] = {0xff9ff4e9, 0xff97f285, 0xfff3f99d, 0xffffb7e7,  0xff9ba3ff, 0xffff7a7a};
    List<String> mListWork;
    Activity mActivity;

    public AdapterWork(List<String> listWork, Activity activity) {
        mListWork = listWork;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolderWork onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.layout_row_work, viewGroup, false);

        ViewHolderWork holder = new ViewHolderWork(view, mActivity);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderWork holder, int i) {
        String name = mListWork.get(i);
        holder.mTxtName.setText(name);

        int color = colors[i % colors.length];
        holder.mView.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return mListWork.size();
    }

    public void removeItemAtIndex(int i) {
        notifyItemRemoved(i);
        mListWork.remove(i);
    }

    public void moveItem(int from, int to) {
        notifyItemMoved(from, to);
    }

    public void addItem(String item) {
        mListWork.add(item);
        notifyDataSetChanged();
    }
}
