package remake.leafpic.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class PermissionUtil {

    public static boolean isPermissionGranted(Context context, String... permissions) {
        for(String permission: permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    public static void requestPermission(Activity activity, int requestCode, String... permissions) {
        ActivityCompat.requestPermissions((AppCompatActivity)activity, permissions, requestCode);
    }
}
