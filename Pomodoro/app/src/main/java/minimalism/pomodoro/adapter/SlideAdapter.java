package minimalism.pomodoro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import minimalism.pomodoro.R;

public class SlideAdapter extends PagerAdapter {

    Context mContext;

    ArrayList<IntroInfo> mIntro;

    public SlideAdapter(Context context) {
        mContext = context;

        mIntro = new ArrayList<IntroInfo>();
        mIntro.add(new IntroInfo(R.string.intro_lyrics_01));
        mIntro.add(new IntroInfo(R.string.intro_lyrics_02));
        mIntro.add(new IntroInfo(R.string.intro_lyrics_03));
    }

    @Override
    public int getCount() {
        return mIntro.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View outputView = inflater.inflate(R.layout.slide, container, false);

        IntroInfo intro = mIntro.get(position);
        ((TextView)outputView.findViewById(R.id.introLyrics)).setText(intro.mLyrics);

        container.addView(outputView);
        return outputView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

//---------Intro Lyrics---------//
    class IntroInfo {
        public int mLyrics;

        public IntroInfo(int lyrics) {
            mLyrics = lyrics;
        }
    }
}
