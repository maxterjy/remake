package maxter.simrec;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class RecordFragment extends Fragment {


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

    public void startRecording() {
        Intent intent = new Intent(getActivity(), RecordingService.class);
        getActivity().startService(intent);
    }

    public void stopRecording() {
        Intent intent = new Intent(getActivity(), RecordingService.class);
        getActivity().stopService(intent);
    }
}
