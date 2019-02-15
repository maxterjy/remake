package remake.leafpic.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import remake.leafpic.R;

public class NavigationDrawer extends ScrollView {

    public interface OnEntrySelectedListener {
        void onEntrySelected(int id);
    }

    OnEntrySelectedListener mOnEntrySelectedListener = null;

    public NavigationDrawer(Context context) {
        super(context);
    }

    public NavigationDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public NavigationDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NavigationDrawer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_navigation_drawer, this, true);

        LinearLayout body = v.findViewById(R.id.navigation_view_body);
        int len = body.getChildCount();

        for(int i = 0; i < len; i++) {
            View child = body.getChildAt(i);
            if (child instanceof NavigationEntry) {

                child.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         if (mOnEntrySelectedListener != null)
                             mOnEntrySelectedListener.onEntrySelected(v.getId());
                    }
                });
            }
        }
    }

    public void setOnEntrySelectedListener(OnEntrySelectedListener listener) {
        this.mOnEntrySelectedListener = listener;
    }
}
