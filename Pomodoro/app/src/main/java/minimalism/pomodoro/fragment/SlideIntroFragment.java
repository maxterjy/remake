package minimalism.pomodoro.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import minimalism.pomodoro.R;
import minimalism.pomodoro.adapter.SlideAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SlideIntroFragment extends Fragment {

    public interface OnSlideIntroFinishCallback {
        void onFinished();
    }

    ViewPager mViewPager;
    SlideAdapter mSlideAdapter;
    TabLayout mTabLayout;
    TextView mBtnFinish;
    OnSlideIntroFinishCallback mOnFinishCallback = null;

    public SlideIntroFragment() {

    }

    public void setOnFinishCallback(OnSlideIntroFinishCallback cb) {
        mOnFinishCallback = cb;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View outView = inflater.inflate(R.layout.fragment_slide_intro, container, false);

        mViewPager = outView.findViewById(R.id.slideViewPager);
        mSlideAdapter = new SlideAdapter(getContext());
        mViewPager.setAdapter(mSlideAdapter);

        mTabLayout = outView.findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        mBtnFinish = outView.findViewById(R.id.btnFinish);
        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnFinishCallback != null) {
                    mOnFinishCallback.onFinished();
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (i == mSlideAdapter.getCount() - 1) {
                    mBtnFinish.setVisibility(View.VISIBLE);
                }
                else {
                    mBtnFinish.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        return outView;
    }
}
