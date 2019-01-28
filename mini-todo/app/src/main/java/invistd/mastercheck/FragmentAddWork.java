package invistd.mastercheck;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Date;


public class FragmentAddWork extends Fragment {

    FloatingActionButton mFabConfirm;
    EditText mEdtWorkTitle;

    public FragmentAddWork() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_work, container, false);

        Intent intent = getActivity().getIntent();
        WorkItem item = (WorkItem) intent.getSerializableExtra("work_item");
        String workTitle = "";

        if (item != null) {
            workTitle = item.mTitle;
        }

        final int workIndex = intent.getIntExtra("work_index", -1);

        mEdtWorkTitle = view.findViewById(R.id.edt_work_title);
        mEdtWorkTitle.setText(workTitle);

        mFabConfirm = view.findViewById(R.id.fab_confirm);
        mFabConfirm.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View v) {
             String workTitle = mEdtWorkTitle.getText().toString();
             WorkItem item = new WorkItem(workTitle, "temp description", new Date());

             Intent intent = new Intent();

             intent.putExtra("work_item", item);
             intent.putExtra("work_index", workIndex);

             getActivity().setResult(Activity.RESULT_OK, intent);
             getActivity().finish();
         }
        });

        return view;
    }
}
