package minimalism.pdfviewer;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int REQUEST_STORAGE_PERMISSION = 1;
    final String REQUIRE_PERMISSIONS[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    FragmentManager mFragmentMgr;
    Handler mHandler = new Handler();

    ImageView mImgLogo;
    TextView mTvPageNumber;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImgLogo = findViewById(R.id.image_view_logo);
        mFragmentMgr = getSupportFragmentManager();

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mTvPageNumber = findViewById(R.id.tv_page_number);

        if (!isPermissionGranted()){
            requestNeededPermissions();
        }
        else {
            enterFileListFragment();
        }
    }

    boolean isPermissionGranted() {
        for(String permission: REQUIRE_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }


    void requestNeededPermissions() {
        if (!isPermissionGranted()) { //do something, when permissions not granted
            boolean shouldShowPermissionRational = false;
            for(String permission: REQUIRE_PERMISSIONS) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    shouldShowPermissionRational = true;
                    break;
                }
            }

            if (shouldShowPermissionRational) { //show explanation of request permissions
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Permission Require");

                if (Build.VERSION.SDK_INT >= 24) {
                    builder.setMessage(Html.fromHtml("<b>Storage</b> permission is required to do the tasks", 0));
                }
                else {
                    builder.setMessage(Html.fromHtml("<b>Storage</b> permission is required to do the tasks"));
                }

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, REQUIRE_PERMISSIONS, REQUEST_STORAGE_PERMISSION);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else { //directly request for permission without explanation
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRE_PERMISSIONS, REQUEST_STORAGE_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (!isPermissionGranted()) {
                Toast.makeText(this, "Please enable permission to continue", Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            }
            else {
                enterFileListFragment();
            }
        }
    }

    void enterReadFragment() {
        mImgLogo.setVisibility(View.INVISIBLE);

        FragmentTransaction transaction = mFragmentMgr.beginTransaction();
        Fragment fragment = new ReadFragment();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    void enterFileListFragment() {
        mImgLogo.setVisibility(View.INVISIBLE);

        FragmentTransaction transaction = mFragmentMgr.beginTransaction();
        Fragment fragment = new FileListFragment();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    public void setPageNumberText(String str){
        mTvPageNumber.setText(str);
    }

    public TextView getPageNumberTextView() {
        return mTvPageNumber;
    }

    public Toolbar getToolbar(){
        return mToolbar;
    }
}
