package remake.leafpic.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import remake.leafpic.R;
import remake.leafpic.data.MediaManager;
import remake.leafpic.data.SlideShowPagerAdapter;

public class PhotoViewerActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ImageView mImgViewPhoto;
    ViewPager vpSlideShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        vpSlideShow = findViewById(R.id.viewpager_slideshow);
        PagerAdapter adapter = new SlideShowPagerAdapter(this);
        vpSlideShow.setAdapter(adapter);

//        mImgViewPhoto = findViewById(R.id.image_view_photo);
//
//        int image_index = getIntent().getIntExtra("image_index", -1);
//
//        String path = MediaManager.getInstance().getImagePathAt(image_index);
//        File file = new File(path);
//        Bitmap bitmap = decodeBitmapFromFile(file);
//
//        mImgViewPhoto.setImageBitmap(bitmap);
    }
}
