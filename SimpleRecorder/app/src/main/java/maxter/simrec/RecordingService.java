package maxter.simrec;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class RecordingService extends Service {

    MediaRecorder mRecorder = null;

    public RecordingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("RecordingService", "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("RecordingService", "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i("RecordingService", "onStartCommand");
        startRecording();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("RecordingService", "onDestroy");
        super.onDestroy();
        if (mRecorder != null) {
            stopRecording();
        }
    }

    String getOutputPath() {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SimpleRecorder/";

        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdir();

        String path =  dirPath + "record.mp4";
        return path;
    }

    public void startRecording() {
        Log.i("RecordingService", "startRecording");
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioChannels(1);

        String path = getOutputPath();
        mRecorder.setOutputFile(path);

        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException ex){
            Log.e("RecordingService", "prepare failed: " + ex.getMessage());
        }
    }

    public void stopRecording() {
        Log.i("RecordingService", "stopRecording");

        mRecorder.stop();
        mRecorder = null;
    }
}
