package maxter.simrec;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
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
                notifyItemInserted(getItemCount()-1);
                mLayoutManager.scrollToPosition(getItemCount()-1);
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
    public void onBindViewHolder(@NonNull final RecordViewHolder holder, final int index) {
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

        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final CharSequence[] items = {"Rename", "Delete"};
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Options");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0://rename
                                break;

                            case 1://delete
                                removeRecordAt(holder.getAdapterPosition());
                                break;
                        }
                    }
                });
                builder.setCancelable(true);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
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

    void removeRecordAt(int index) {
        mDBHelper.removeRecordAt(index);
        notifyItemRemoved(index);
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
