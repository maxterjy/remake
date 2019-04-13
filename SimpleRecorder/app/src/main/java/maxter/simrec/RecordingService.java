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
    DBHelper mDBHelper = null;

    String mOutputPath = null;
    String mOutputName = null;

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
        mDBHelper = DBHelper.getInstance();
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

    void calculateOutputPath() {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SimpleRecorder/";

        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdir();

        mOutputName =  "record.mp4";

        try {
            int n = mDBHelper.getRecordCount()+1;
            String index = String.format("%02d", n);
            mOutputName = "Record " + index + ".mp4";
        }
        catch (Exception ex) {
            Log.i("RecordingService", "calculateOutputPath failed: " + ex.getMessage());
        }

        mOutputPath =  dirPath + mOutputName;
    }

    public void startRecording() {
        Log.i("RecordingService", "startRecording");
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioChannels(1);

        calculateOutputPath();
        mRecorder.setOutputFile(mOutputPath);

        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException ex){
            Log.e("RecordingService", "prepare failed: " + ex.getMessage());
        }
    }

    public void stopRecording() {
        Log.i("RecordingService", "stopRecording output: " + mOutputPath);

        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        try {
            mDBHelper.addRecording(mOutputName, mOutputPath, 1000);
        }
        catch (Exception ex) {
            Log.i("RecordingService", "stopRecording failed: " + ex.getMessage());
        }
    }
}
