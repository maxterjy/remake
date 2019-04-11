package maxter.simrec;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.util.Log;

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
        stopRecording();
    }

    public void startRecording() {
        Log.i("RecordingService", "startRecording");
    }

    public void stopRecording() {
        Log.i("RecordingService", "stopRecording");
    }
}
