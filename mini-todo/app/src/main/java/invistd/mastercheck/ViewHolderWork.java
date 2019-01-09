package invistd.mastercheck;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class ViewHolderWork extends  RecyclerView.ViewHolder {
    final View mView;
    TextView mTxtName;

    public ViewHolderWork(View view) {
        super(view);
        mView = view;
        mTxtName = view.findViewById(R.id.textview_work_name);
    }
}

