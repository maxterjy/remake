package remake.leafpic.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.WeakReference;

import remake.leafpic.schedule.BitmapLoaderTask;

public class AsyncDrawable extends BitmapDrawable {

    WeakReference<BitmapLoaderTask> mBitmapLoaderTask;

    public AsyncDrawable(Resources res, Bitmap bitmap, BitmapLoaderTask task) {
        super(res, bitmap);

        mBitmapLoaderTask = new WeakReference<>(task);
    }

    public BitmapLoaderTask getBitmapLoaderTask() {
        return mBitmapLoaderTask.get();
    }
}
