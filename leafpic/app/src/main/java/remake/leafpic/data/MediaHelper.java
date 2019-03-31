package remake.leafpic.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.util.LruCache;

import java.util.ArrayList;

public class MediaHelper {
    private static final MediaHelper mInstance = new MediaHelper();

    private ArrayList<String> mImagePaths;
    LruCache<String, Bitmap> mBitmapCache;

    public static MediaHelper getInstance() {
        return mInstance;
    }

    private MediaHelper() {
    }

    public void init(Context context) {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String projections[] = {
                MediaStore.Images.Media.DATA
        };
        Cursor cursor = resolver.query(uri, projections, null, null, null);

        mImagePaths = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                mImagePaths.add(cursor.getString(0));
            }

            cursor.close();
        }

        int maxMemSize = (int) (Runtime.getRuntime().maxMemory()/1024);
        int cacheSize = maxMemSize / 10;

        mBitmapCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };
    }

    public Bitmap getBitmapFromCache(String path) {
        return mBitmapCache.get(path);
    }

    public void putBitmapToCache(String path, Bitmap bitmap){
        mBitmapCache.put(path, bitmap);
    }

    String getImagePathAt(int index) {
        if (index >= getImageCount())
            return null;

        return mImagePaths.get(index);
    }

    int getImageCount() {
        return mImagePaths.size();
    }
}
