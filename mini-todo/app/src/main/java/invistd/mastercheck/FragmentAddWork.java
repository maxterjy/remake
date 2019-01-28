package invistd.mastercheck;


import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Date;


public class FragmentAddWork extends Fragment {

    FloatingActionButton mFabConfirm;
    EditText mEdtWorkTitle;
    EditText mEdtWorkDescription;
    SwitchCompat mSwitchReminder;
    LinearLayout mLayoutDateInfo;

    public FragmentAddWork() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_work, container, false);

        Intent intent = getActivity().getIntent();
        WorkItem item = (WorkItem) intent.getSerializableExtra("work_item");

        String workTitle = "";
        String workDescription = "";

        if (item != null) {
            workTitle = item.mTitle;
            workDescription = item.mDescription;
        }

        final int workIndex = intent.getIntExtra("work_index", -1);

        mEdtWorkTitle = view.findViewById(R.id.edt_work_title);
        mEdtWorkTitle.setText(workTitle);
        mEdtWorkTitle.requestFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        mEdtWorkDescription = view.findViewById(R.id.edt_work_description);
        mEdtWorkDescription.setText(workDescription);

        mFabConfirm = view.findViewById(R.id.fab_confirm);
        mFabConfirm.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View v) {
             String title = mEdtWorkTitle.getText().toString();
             String description = mEdtWorkDescription.getText().toString();

             WorkItem item = new WorkItem(title, description, new Date());

             Intent intent = new Intent();

             intent.putExtra("work_item", item);
             intent.putExtra("work_index", workIndex);

             getActivity().setResult(Activity.RESULT_OK, intent);
             getActivity().finish();
         }
        });

        mLayoutDateInfo = view.findViewById(R.id.layout_date_info);

        mSwitchReminder = view.findViewById(R.id.switch_reminder);
        mSwitchReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSwitchReminder.isChecked()) {
                    mLayoutDateInfo.animate().alpha(1.0f).setDuration(500).setListener(
                            new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    mLayoutDateInfo.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {

                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });
                }
                else {
                    mLayoutDateInfo.animate().alpha(0.0f).setDuration(500).setListener(
                            new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    mLayoutDateInfo.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            }
                    );
                }
            }
        });

        return view;
    }
}
