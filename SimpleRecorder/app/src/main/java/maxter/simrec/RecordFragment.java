package maxter.simrec;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class RecordFragment extends Fragment {

    final int REQUEST_PERMISSION_TO_RECORD = 1;
    final String REQUIRE_PERMISSIONS[] = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    Button btnStart, btnStop;

    public RecordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View outputView = inflater.inflate(R.layout.fragment_record, container, false);

        btnStart = outputView.findViewById(R.id.btnStart);
        btnStop = outputView.findViewById(R.id.btnStop);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });

        return outputView;
    }

    void startRecordingService(){
        Toast.makeText(getActivity(), "Start Recording", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), RecordingService.class);
        getActivity().startService(intent);
    }

    public void startRecording() {
        if (isPermissionGranted()) {
            startRecordingService();
        }
        else {
            requestNeededPermissions();
        }

    }

    public void stopRecording() {
        Toast.makeText(getActivity(), "Stop Recording", Toast.LENGTH_SHORT).show();
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
