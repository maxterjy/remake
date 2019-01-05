package invistd.mastercheck;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AdapterWork extends RecyclerView.Adapter<AdapterWork.ViewHolderWork> {
    int colors[] = {0xff9ff4e9, 0xff97f285, 0xfff3f99d, 0xffffb7e7,  0xff9ba3ff, 0xffff7a7a};
    List<String> mListWork;

    public class ViewHolderWork extends  RecyclerView.ViewHolder {
        View mLayout;
        TextView mTxtName;

        public ViewHolderWork(View view) {
            super(view);
            mLayout = view;
            mTxtName = view.findViewById(R.id.textview_work_name);
        }
    }

    public AdapterWork(List<String> listWork) {
        mListWork = listWork;
    }

    @NonNull
    @Override
    public ViewHolderWork onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.layout_row_work, viewGroup, false);

        ViewHolderWork holder = new ViewHolderWork(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderWork holder, int i) {
        String name = mListWork.get(i);
        holder.mTxtName.setText(name);

        int color = colors[i % colors.length];
        holder.mLayout.setBackgroundColor(color);
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
}
