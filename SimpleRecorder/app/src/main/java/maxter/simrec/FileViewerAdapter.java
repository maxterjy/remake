package maxter.simrec;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class FileViewerAdapter extends RecyclerView.Adapter<FileViewerAdapter.RecordViewHolder> {


    DBHelper mDBHelper;

    public FileViewerAdapter(Context context) {
        mDBHelper = DBHelper.getInstance();

        Log.i("FileViewerAdapter", "ctor");
        mDBHelper.setOnDatabaseChangedListener(new DBHelper.OnDatabaseChangedListener() {
            @Override
            public void onNewEntryAdded() {
                Log.i("FileViewerAdapter", "onNewEntryAdded");
                notifyItemInserted(getItemCount()-1);
            }
        });
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_record_view, viewGroup, false);

        RecordViewHolder holder = new RecordViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int index) {
        Log.i("FileViewerAdapter", "onBindViewHolder: " + index);

        RecordInfo record = getRecordInfoAt(index);
        holder.mTvName.setText(record.mName);
        holder.mTvLength.setText(Integer.toString(record.mLength));
        holder.mTvCreatedTime.setText(Long.toString(record.mCreatedTime));
    }

    @Override
    public int getItemCount() {
        return mDBHelper.getRecordCount();
    }

    RecordInfo getRecordInfoAt(int index) {
        return mDBHelper.getRecordInfoAt(index);
    }

//------------------------------------ViewHolder-------------------------------//
    public static class RecordViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvName;
        public TextView mTvLength;
        public TextView mTvCreatedTime;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.tvName);
            mTvLength = itemView.findViewById(R.id.tvLength);
            mTvCreatedTime = itemView.findViewById(R.id.mTvCreatedTime);
        }
    }
}
