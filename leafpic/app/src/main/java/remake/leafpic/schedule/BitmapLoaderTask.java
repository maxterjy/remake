package remake.leafpic.schedule;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.lang.ref.WeakReference;

import remake.leafpic.data.MediaManager;

public class BitmapLoaderTask extends AsyncTask<File, Void, Bitmap> {

    WeakReference<ImageView> imgViewRef;
    File mImageFile = null;
    int mTargetViewWidth = 180;

    public BitmapLoaderTask(ImageView imgView) {
        imgViewRef = new WeakReference<>(imgView);
    }

    public BitmapLoaderTask() {

    }

    public void setTargetViewWidth(int w) {
        mTargetViewWidth = w;
    }

    @Override
    protected Bitmap doInBackground(File... files) {
        File file = files[0];
        mImageFile = file;

        String path = file.getAbsolutePath();

        Bitmap bitmap = MediaManager.getInstance().getBitmapFromCache(path);

        if (bitmap == null) {
            bitmap = MediaManager.getInstance().decodeBitmapFromFile(file, mTargetViewWidth);
            MediaManager.getInstance().putBitmapToCache(path, bitmap);
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null && imgViewRef != null) {
            ImageView view = imgViewRef.get();
            view.setImageBitmap(bitmap);
        }
    }

    public File getImageFile() {
        return mImageFile;
    }
}
