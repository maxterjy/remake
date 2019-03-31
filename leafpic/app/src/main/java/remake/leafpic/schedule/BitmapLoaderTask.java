package remake.leafpic.schedule;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.lang.ref.WeakReference;

public class BitmapLoaderTask extends AsyncTask<File, Void, Bitmap> {

    WeakReference<ImageView> imgViewRef;

    public BitmapLoaderTask(ImageView imgView) {
        imgViewRef = new WeakReference<>(imgView);
    }

    @Override
    protected Bitmap doInBackground(File... files) {
        File file = files[0];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        int imageWidth = options.outWidth;
        int ratio = imageWidth / 180; //hardcode: 180 is size of imageview

        options.inJustDecodeBounds = false;
        options.inSampleSize = ratio;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

        return  bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null && imgViewRef != null) {
            ImageView view = imgViewRef.get();
            view.setImageBitmap(bitmap);
        }
    }
}
