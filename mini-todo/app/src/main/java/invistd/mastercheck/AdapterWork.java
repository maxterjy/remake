package invistd.mastercheck;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;


public class AdapterWork extends RecyclerView.Adapter<ViewHolderWork> {
    int colors[] = {0xff9ff4e9, 0xff97f285, 0xfff3f99d, 0xffffb7e7,  0xff9ba3ff, 0xffff7a7a};
    List<String> mListWork;
    Fragment mFragment;

    public AdapterWork(List<String> listWork, Fragment fragment) {
        mListWork = listWork;
        mFragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolderWork onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        final View view = inflater.inflate(R.layout.layout_row_work, viewGroup, false);
        ViewHolderWork holder = new ViewHolderWork(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderWork holder, final int i) {
        String name = mListWork.get(i);
        holder.mTxtName.setText(name);

        int color = colors[i % colors.length];
        holder.mView.setBackgroundColor(color);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.mView.getContext(), ActivityAddWork.class);
                int index = i;
                String title = mListWork.get(index);

                intent.putExtra("work_index", index);
                intent.putExtra("work_title", title);

                mFragment.startActivityForResult(intent, FragmentMain.REQUEST_EDIT_WORK);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListWork.size();
    }

    public void removeItemAtIndex(int i) {
        mListWork.remove(i);
        notifyItemRemoved(i);
    }

    public void moveItem(int from, int to) {
        notifyItemMoved(from, to);
    }

    public void addItem(String value) {
        mListWork.add(value);
    }

    public void editItem(int index, String newValue) {
        mListWork.set(index, newValue);
        notifyItemChanged(index);
    }
}
