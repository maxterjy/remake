package maxter.simrec;


import android.app.AlertDialog;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.IOException;

public class PlaybackFragment extends DialogFragment {

    ImageView mBtnClose = null;
    FloatingActionButton mFabPlay = null;
    MediaPlayer mMediaPlayer = null;
    boolean mIsPlaying = false;

    //For handling SeekBar
    SeekBar mSeekBar = null;
    Handler mHandler = new Handler();
    Runnable mSeekBarUpdater = new Runnable() {
        @Override
        public void run() {
            if (mMediaPlayer != null) {
                int progress = mMediaPlayer.getCurrentPosition();
                mSeekBar.setProgress(progress);

                postUpdateSeekBar();
            }
        }
    };

    void postUpdateSeekBar() {
        mHandler.postDelayed(mSeekBarUpdater, 1000);
    }
    //For handling SeekBar end

    public PlaybackFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.PlayBackDialogTheme;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_playback, null);
        builder.setView(view);

        mFabPlay = view.findViewById(R.id.fabPlay);
        mFabPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabPlayClicked();
            }
        });

        mBtnClose = view.findViewById(R.id.btnClose);
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mSeekBar = view.findViewById(R.id.seekbar);

        Dialog dialog = builder.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    private void onFabPlayClicked() {
        if (mIsPlaying) {
            pauseAudio();
        }
        else {
            playAudio();
        }
    }

    void playAudio() {
        mIsPlaying = true;
        mFabPlay.setImageResource(R.drawable.ic_media_pause);

        mMediaPlayer = new MediaPlayer();

        try{
            String path = "/storage/emulated/0/SimpleRecorder/Record 01.mp4";
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();

            mSeekBar.setMax(mMediaPlayer.getDuration());

            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopAudio();
                }
            });

            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mSeekBar.setProgress(0);
                    mMediaPlayer.start();
                    postUpdateSeekBar();
                }
            });
        }
        catch (IOException ex) {
            Log.i("PlaybackFragment", "playAudio failed: " + ex.getMessage());
        }
    }

    void pauseAudio() {
        mIsPlaying = false;
        mFabPlay.setImageResource(R.drawable.ic_media_play);

        mMediaPlayer.pause();

        mHandler.removeCallbacks(mSeekBarUpdater);
    }

    void stopAudio() {
        mIsPlaying = false;
        mFabPlay.setImageResource(R.drawable.ic_media_play);

        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mMediaPlayer = null;

        mSeekBar.setProgress(mSeekBar.getMax());
        mHandler.removeCallbacks(mSeekBarUpdater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_playback, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mMediaPlayer != null) {
            stopAudio();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mMediaPlayer != null) {
            stopAudio();
        }
    }
}
