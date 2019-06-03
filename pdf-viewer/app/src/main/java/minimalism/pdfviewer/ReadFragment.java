package minimalism.pdfviewer;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.net.URI;


public class ReadFragment extends Fragment implements PageChangeDialog.PageChangeListener {

    ViewPager mViewPager;
    PdfPageAdapter mPdfPageAdapter;

    Handler mHanlder = new Handler();
    MainActivity mActivity;

    int mCurrentPageIndex = 0;
    int mPageCount = 0;
    TextView mTvPageNumber;
    Toolbar mToolbar;

    public static ReadFragment newInstance(String uriStr) {
        Bundle args = new Bundle();
        args.putString("file_uri", uriStr);

        ReadFragment fragment = new ReadFragment();
        fragment.setArguments(args);

        return fragment;
    }

    Uri getFileUri() {
        String str  = getArguments().getString("file_uri");
        Uri uri = Uri.parse(str);

        return uri;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View outView = inflater.inflate(R.layout.fragment_read, container, false);

        mViewPager = outView.findViewById(R.id.view_pager);

        mPdfPageAdapter = new PdfPageAdapter(getContext(), getFileUri());


        mViewPager.setAdapter(mPdfPageAdapter);
        mPageCount = mViewPager.getAdapter().getCount();

        mTvPageNumber = mActivity.getPageNumberTextView();
        mTvPageNumber.setText(String.format("%d/%d", mCurrentPageIndex+1, mPageCount));

        mToolbar = mActivity.getToolbar();
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPreviousFragment();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int i) {
                mCurrentPageIndex = i;
                String str = String.format("%d/%d", mCurrentPageIndex+1, mPageCount);
                mTvPageNumber.setText(str);
            }

        });

        setHasOptionsMenu(true);

        return outView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.reader_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.go_to_page:{
                PageChangeDialog dialog = new PageChangeDialog();
                dialog.setPageChangeListener(this);
                dialog.show(getFragmentManager(), "pageChangeDialog");
                break;
            }
        }

        return true;
    }

    @Override
    public void onPageChangeCompleted(int pageNumber) {
        mViewPager.setCurrentItem(pageNumber-1);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    void goPreviousFragment() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mToolbar.setNavigationIcon(null);
        mTvPageNumber.setText("");
    }
}
