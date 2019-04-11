package maxter.simrec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startRecording(View view) {
        Intent intent = new Intent(this, RecordingService.class);
        startService(intent);
    }

    public void stopRecording(View view) {
        Intent intent = new Intent(this, RecordingService.class);
        stopService(intent);
    }
}
