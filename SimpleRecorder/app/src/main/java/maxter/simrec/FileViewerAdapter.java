package maxter.simrec;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FileViewerAdapter extends RecyclerView.Adapter<FileViewerAdapter.RecordViewHolder> {

    Context mContext;
    DBHelper mDBHelper;
    LinearLayoutManager mLayoutManager;

    public FileViewerAdapter(Context context, LinearLayoutManager layoutManager) {
        mContext = context;
        mDBHelper = DBHelper.getInstance();
        mLayoutManager = layoutManager;

        mDBHelper.setOnDatabaseChangedListener(new DBHelper.OnDatabaseChangedListener() {
            @Override
            public void onNewEntryAdded() {
                notifyItemInserted(0);
                mLayoutManager.scrollToPosition(0);
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
    public void onBindViewHolder(@NonNull RecordViewHolder holder, final int index) {
        final RecordInfo record = getRecordInfoAt(index);
        holder.mTvName.setText(record.mName);

        long minute = TimeUnit.MILLISECONDS.toMinutes(record.mLength);
        long second = TimeUnit.MILLISECONDS.toSeconds(record.mLength) - TimeUnit.MINUTES.toSeconds(minute);

        holder.mTvLength.setText(String.format("%02d:%02d", minute, second));

        String strCreatedTime = DateUtils.formatDateTime(mContext, record.mCreatedTime,
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_YEAR);
        holder.mTvCreatedTime.setText(strCreatedTime);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlaybackFragment fragment = PlaybackFragment.newInstance(record);
                    FragmentTransaction transaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                    fragment.show(transaction, "dialog_playback");

                } catch (Exception ex){
                    Log.i("FileViewerAdapter", "mCardView onClick failed: " + ex.getMessage());
                }
            }
        });
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

        public View mCardView;
        public TextView mTvName;
        public TextView mTvLength;

        public TextView mTvCreatedTime;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.cardView);
            mTvName = itemView.findViewById(R.id.tvName);
            mTvLength = itemView.findViewById(R.id.tvLength);
            mTvCreatedTime = itemView.findViewById(R.id.mTvCreatedTime);
            mCardView = itemView.findViewById(R.id.cardView);
        }
    }
}
