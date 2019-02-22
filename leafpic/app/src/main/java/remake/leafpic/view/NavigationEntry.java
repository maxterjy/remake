package remake.leafpic.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import remake.leafpic.R;

public class NavigationEntry extends FrameLayout {
    ImageView mEntryIcon;
    TextView mEntryTv;

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
        View view = LayoutInflater.from(context).inflate(R.layout.view_navigation_entry, this, true);
        mEntryIcon = view.findViewById(R.id.navigation_item_icon);
        mEntryTv = view.findViewById(R.id.navigation_item_text);

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.leaf);
        String entryText = arr.getString(R.styleable.leaf_entry_text);
        mEntryTv.setText(entryText);

        Drawable drawable = arr.getDrawable(R.styleable.leaf_entry_icon);
        mEntryIcon.setImageDrawable(drawable);
    }
}
