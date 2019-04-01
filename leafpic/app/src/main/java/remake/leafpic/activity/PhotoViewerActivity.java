package remake.leafpic.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
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

public class PhotoViewerActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ImageView mImgViewPhoto;

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

        mImgViewPhoto = findViewById(R.id.image_view_photo);

        int image_index = getIntent().getIntExtra("image_index", -1);

        String path = MediaManager.getInstance().getImagePathAt(image_index);
        File file = new File(path);
        Bitmap bitmap = decodeBitmapFromFile(file);

        mImgViewPhoto.setImageBitmap(bitmap);
    }

    Bitmap decodeBitmapFromFile(File file) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        int imageWidth = options.outWidth;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;

        int ratio = imageWidth / screenWidth; //hardcode: 180 is size of imageview

        options.inJustDecodeBounds = false;
        options.inSampleSize = ratio;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

        return  bitmap;
    }
}
