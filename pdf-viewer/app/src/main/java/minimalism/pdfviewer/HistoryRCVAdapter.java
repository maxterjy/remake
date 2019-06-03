package minimalism.pdfviewer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HistoryRCVAdapter extends RecyclerView.Adapter<HistoryRCVAdapter.ViewHolder> {

    public interface HistoryItemCallback{
        void onClick(int pos);
    }

    Context mContext;
    List<HistoryItem> mItems;
    HistoryItemCallback mHistoryItemCallback = null;

    public HistoryRCVAdapter(List<HistoryItem> items) {
        mItems = items;
    }

    public void setHistoryItemCallback(HistoryItemCallback callback) {
        mHistoryItemCallback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View outView = inflater.inflate(R.layout.history_item_view, viewGroup, false);

        mContext = viewGroup.getContext();

        ViewHolder holder = new ViewHolder(outView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        HistoryItem item = mItems.get(i);
        viewHolder.setName(item.getFileName());

        final int pos = i;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHistoryItemCallback != null)
                    mHistoryItemCallback.onClick(pos);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public HistoryItem getItem(int pos) {
        return mItems.get(pos);
    }

    void removeItem(int pos) {
        mItems.remove(pos);
        notifyItemRemoved(pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvName = itemView.findViewById(R.id.tv_history_item_name);
        }

        public void setName(String str) {
            mTvName.setText(str);
        }
    }
}
