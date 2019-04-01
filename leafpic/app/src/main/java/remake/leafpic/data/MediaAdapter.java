package remake.leafpic.data;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.io.File;

import remake.leafpic.R;
import remake.leafpic.activity.PhotoViewerActivity;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int index) {
        String path = MediaManager.getInstance().getImagePathAt(index);
        if (path != null) {
            Bitmap bitmap = MediaManager.getInstance().getBitmapFromCache(path);
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
            }
        }

        holder.mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PhotoViewerActivity.class);
                intent.putExtra("image_index", index);

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return MediaManager.getInstance().getImageCount();
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
