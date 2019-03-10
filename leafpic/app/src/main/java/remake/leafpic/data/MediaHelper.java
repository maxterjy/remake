package remake.leafpic.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class MediaHelper {
    private static final MediaHelper mInstance = new MediaHelper();

    private ArrayList<String> mImagePaths;

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
