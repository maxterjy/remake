package remake.leafpic.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import remake.leafpic.util.AsyncDrawable;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {
    ImageView mPhoto;
    Bitmap placeHolderBitmap;

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
            Bitmap bitmap = MediaHelper.getInstance().getBitmapFromCache(path);
            if (bitmap != null) {
                holder.mPhoto.setImageBitmap(bitmap);
            }
            else {
                File requireFile = new File(path);

                if (shouldStartNewBitmapLoaderTask(requireFile, holder.mPhoto)) {
                    BitmapLoaderTask task = new BitmapLoaderTask(holder.mPhoto);
                    AsyncDrawable drawable = new AsyncDrawable(holder.mPhoto.getResources(), placeHolderBitmap, task);
                    holder.mPhoto.setImageDrawable(drawable);
                    task.execute(requireFile);
                }
                BitmapLoaderTask bitmapLoader = new BitmapLoaderTask(holder.mPhoto);
                bitmapLoader.execute(requireFile);
            }
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

    //if the file is current loading into imageview
    //and the file it need to display are different
    //cancel current task in image view, and the start a new task to load new file later
    //otherwise, if they are the same, keep going
    boolean shouldStartNewBitmapLoaderTask(File requireFile, ImageView imageView) {
        BitmapLoaderTask task = getBitmapLoaderTaskFrom(imageView);

        if (task != null) {
            File loadingFile = task.getImageFile();
            if (loadingFile != null) {

                if (loadingFile != requireFile) {
                    task.cancel(true);
                }
                else {
                    //loadingFile and requestToLoadFile are the same
                    //keep current loading work
                    return false;
                }
            }
        }

        return true;
    }

    BitmapLoaderTask getBitmapLoaderTaskFrom(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof AsyncDrawable) {
            return ((AsyncDrawable) drawable).getBitmapLoaderTask();
        }

        return null;
    }
}
