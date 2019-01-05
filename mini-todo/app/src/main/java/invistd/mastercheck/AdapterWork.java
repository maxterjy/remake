package invistd.mastercheck;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AdapterWork extends RecyclerView.Adapter<AdapterWork.ViewHolderWork> {
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
    }

    @Override
    public int getItemCount() {
        return mListWork.size();
    }
}
