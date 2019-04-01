package remake.leafpic.data;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.transition.Slide;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;

import remake.leafpic.R;
import remake.leafpic.schedule.BitmapLoaderTask;

public class SlideShowPagerAdapter extends PagerAdapter {
    LayoutInflater mLayoutInflater;
    Context mContext;
    int mScreenWidth;

    public SlideShowPagerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;

        calculateScreenSize();
    }

    void calculateScreenSize() {
        Display display = ((Activity)mContext).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mScreenWidth = size.x;
    }

    @Override
    public int getCount() {
        return MediaManager.getInstance().getImageCount();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mLayoutInflater.inflate(R.layout.slideshow_pager_layout, container, false);

        ImageView imgView = view.findViewById(R.id.imageview_photo);

        String path = MediaManager.getInstance().getImagePathAt(position);

        BitmapLoaderTask bitmapLoader = new BitmapLoaderTask(imgView);
        bitmapLoader.setTargetViewWidth(mScreenWidth);
        bitmapLoader.execute(new File(path));

        container.addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view == (LinearLayout)obj;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
