package maxter.simrec;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class RecordFragment extends Fragment {

    final int REQUEST_PERMISSION_TO_RECORD = 1;
    final String REQUIRE_PERMISSIONS[] = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    Chronometer mChronometer = null;
    FloatingActionButton mFabRecord = null;
    boolean mIsRecording = false;
    CoordinatorLayout mCoordinatorLayout = null;

    public RecordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View outputView = inflater.inflate(R.layout.fragment_record, container, false);

        mChronometer = outputView.findViewById(R.id.recordChronometer);
        mCoordinatorLayout = outputView.findViewById(R.id.coordinatorLayout);
        mFabRecord = outputView.findViewById(R.id.fabRecord);
        mFabRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onFabRecordClick();
            }
        });

        return outputView;
    }

    private void onFabRecordClick() {
        mIsRecording = !mIsRecording;

        if (mIsRecording) {
            startRecording();
        }
        else {
            stopRecording();
        }
    }

    void startRecordingService(){
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();

        Intent intent = new Intent(getActivity(), RecordingService.class);
        getActivity().startService(intent);
    }

    public void startRecording() {
        if (isPermissionGranted()) {
            mFabRecord.setImageResource(R.drawable.ic_media_stop);
            startRecordingService();
        }
        else {
            requestNeededPermissions();
        }
    }

    public void stopRecording() {
        mChronometer.stop();
        mFabRecord.setImageResource(R.drawable.ic_mic_white_36dp);
        Snackbar.make(mCoordinatorLayout, "Record Saved", Snackbar.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), RecordingService.class);
        getActivity().stopService(intent);
    }

    boolean isPermissionGranted() {
        boolean isPermissionGranted = true;
        for(String permission: REQUIRE_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                isPermissionGranted = false;
                break;
            }
        }

        return isPermissionGranted;
    }

    //ref: https://android--code.blogspot.com/2017/08/android-request-multiple-permissions.html
    void requestNeededPermissions() {
        if (!isPermissionGranted()) { //do something, when permissions not granted
            boolean shouldShowPermissionRational = false;
            for(String permission: REQUIRE_PERMISSIONS) {
                if (shouldShowRequestPermissionRationale(permission)) {
                    shouldShowPermissionRational = true;
                    break;
                }
            }

            if (shouldShowPermissionRational) { //show explanation of request permissions
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Please grant those permissions");
                builder.setMessage("Microphone and Storage are required to do the tasks");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(REQUIRE_PERMISSIONS, REQUEST_PERMISSION_TO_RECORD);
                    }
                });
                builder.setNeutralButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else { //directly request for permission without explanation
                requestPermissions(REQUIRE_PERMISSIONS, REQUEST_PERMISSION_TO_RECORD);
            }
        }
        else { //do something when permission are granted
            Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("RecordFragment", "requestCode: " + requestCode);
        boolean isPermissionGranted = true;
        if (requestCode == REQUEST_PERMISSION_TO_RECORD) {
            for(int i = 0; i < permissions.length; i++) {
                Log.i("RecordFragment", "permission: " + permissions[i] + " : " + grantResults[i]);
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isPermissionGranted = false;
                    break;
                }
            }

            if (isPermissionGranted) {
                startRecordingService();
            }
            else {
                Toast.makeText(getActivity(), "Please enable permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
