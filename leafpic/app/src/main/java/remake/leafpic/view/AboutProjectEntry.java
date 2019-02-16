package remake.leafpic.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import remake.leafpic.R;

public class AboutProjectEntry extends FrameLayout {
    ImageView mIcon;
    TextView mTitleTv;
    TextView mDescriptionTv;
    String mLink;

    public AboutProjectEntry(Context context) {
        super(context);
    }

    public AboutProjectEntry(Context context,AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public AboutProjectEntry(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs) {
        View v = LayoutInflater.from(context).inflate(R.layout.about_projet_entry, this, true);

        mIcon = v.findViewById(R.id.about_project_icon);
        mTitleTv = v.findViewById(R.id.about_project_text);
        mDescriptionTv = v.findViewById(R.id.about_project_description);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.leaf);
        Drawable iconDrawable = attributes.getDrawable(R.styleable.leaf_entry_icon);
        String title = attributes.getString(R.styleable.leaf_entry_text);
        String description = attributes.getString(R.styleable.leaf_entry_description);
        mLink = attributes.getString(R.styleable.leaf_entry_link);

        mIcon.setImageDrawable(iconDrawable);
        mTitleTv.setText(title);
        mDescriptionTv.setText(description);

        //todo
        //onSelectListener
        //direct to mLink
    }
}
