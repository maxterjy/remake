package minimalism.pdfviewer;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import minimalism.imagehelper.TouchImageView;

public class PdfPageAdapter extends PagerAdapter {

    Context mContext = null;
    ParcelFileDescriptor mFileDescriptor;
    PdfRenderer mPdfRenderer;
    PdfRenderer.Page mPdfPage = null;

    public PdfPageAdapter(Context context, Uri uri) {
        mContext = context;

        try {
//            //debug
//            File file = new File("/sdcard/debug", "test.pdf");
//            mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);

            mFileDescriptor = mContext.getContentResolver().openFileDescriptor(uri, "r");

            mPdfRenderer = new PdfRenderer(mFileDescriptor);
        } catch (Exception ex) {
            Toast.makeText(mContext, "File not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getCount() {
        return mPdfRenderer.getPageCount();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view == obj;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        TouchImageView imageView = new TouchImageView(container.getContext());

        try {
            if (mPdfPage != null)
                mPdfPage.close();

            mPdfPage = mPdfRenderer.openPage(position);

            int orientation = container.getResources().getConfiguration().orientation;

            int w = mPdfPage.getWidth();
            int h = mPdfPage.getHeight();

            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                int tmp = w;
                w = h;
                h = tmp;
            }

            Bitmap bitmap = Bitmap.createBitmap(
                    container.getContext().getResources().getDisplayMetrics().densityDpi * mPdfPage.getWidth() /  144,
                    container.getContext().getResources().getDisplayMetrics().densityDpi * mPdfPage.getHeight() / 144,
                    Bitmap.Config.ARGB_8888);

            mPdfPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            imageView.setImageBitmap(bitmap);

        } catch (Exception ex) {
            Log.e("thachpham", "PdfPageAdapter instantiateItem ex: ", ex);
        }

        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
