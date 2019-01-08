package invistd.mastercheck;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class ViewHolderWork extends  RecyclerView.ViewHolder {
    final View mView;
    TextView mTxtName;
    Activity mActivity;

    public ViewHolderWork(View view, Activity activity) {
        super(view);
        mView = view;
        mTxtName = view.findViewById(R.id.textview_work_name);
        mActivity = activity;

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = getAdapterPosition();
                String title = mTxtName.getText().toString();

                Intent intent = new Intent(mActivity, ActivityAddWork.class);
                intent.putExtra("work_index", index);
                intent.putExtra("work_title", title);

                mActivity.startActivityForResult(intent, FragmentMain.REQUEST_EDIT_WORK);
            }
        });
    }
}

