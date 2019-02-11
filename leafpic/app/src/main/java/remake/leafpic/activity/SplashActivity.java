package remake.leafpic.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import remake.leafpic.R;
import remake.leafpic.util.PermissionUtil;

public class SplashActivity extends AppCompatActivity {

    private final int STORAGE_PERMISSION_REQUEST_CODE = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        if (!PermissionUtil.isPermissionGranted(this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PermissionUtil.requestPermission(this, STORAGE_PERMISSION_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSION_REQUEST_CODE: {
                boolean gotPermission = grantResults.length > 0;
                for(int result: grantResults) {
                    gotPermission &= (result == PackageManager.PERMISSION_GRANTED);
                }

                if (!gotPermission) {
                    Toast.makeText(this, "Permisison to access storage was denied!\n We're not able to get your images", Toast.LENGTH_LONG).show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 3000);
                }
                
                break;
            }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
