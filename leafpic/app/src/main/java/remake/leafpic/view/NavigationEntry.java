package remake.leafpic.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import remake.leafpic.R;

public class NavigationEntry extends LinearLayout {
    public NavigationEntry(Context context) {
        super(context);
    }

    public NavigationEntry(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NavigationEntry(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NavigationEntry(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_navigation_entry, this, true);
    }
}
