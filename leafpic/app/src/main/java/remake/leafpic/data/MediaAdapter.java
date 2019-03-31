package remake.leafpic.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.io.File;

import remake.leafpic.R;
import remake.leafpic.schedule.BitmapLoaderTask;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {
    ImageView mPhoto;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.layout_media_item, viewGroup, false);

        mPhoto = view.findViewById(R.id.photo);

        ViewHolder holder = new ViewHolder(view);
        holder.init(mPhoto);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int index) {
        String path = MediaHelper.getInstance().getImagePathAt(index);
        if (path != null) {
            File file = new File(path);
            BitmapLoaderTask bitmapLoader = new BitmapLoaderTask(holder.mPhoto);
            bitmapLoader.execute(file);
        }
    }

    @Override
    public int getItemCount() {
        return MediaHelper.getInstance().getImageCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void init(ImageView photo) {
            mPhoto = photo;
        }
    }
}
