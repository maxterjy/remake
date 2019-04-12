package maxter.simrec;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FileViewerAdapter extends RecyclerView.Adapter<FileViewerAdapter.RecordViewHolder> {


    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_record_view, viewGroup, false);

        RecordViewHolder holder = new RecordViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder recordViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


//------------------------------------ViewHolder-------------------------------//
    public static class RecordViewHolder extends RecyclerView.ViewHolder {

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
