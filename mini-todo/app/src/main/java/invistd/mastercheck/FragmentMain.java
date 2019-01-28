package invistd.mastercheck;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FragmentMain extends Fragment {

    public static final int REQUEST_ADD_WORK = 100;
    public static final int REQUEST_EDIT_WORK = 101;

    List<WorkItem> listWork;
    RecyclerViewEmptySupport mRcvWork;
    AdapterWork mAdapterWork;
    RecyclerView.LayoutManager mLayoutManager;

    FloatingActionButton mfabAddWork;
    CoordinatorLayout mlayoutCoordinator;

    StoreRetrieveData mStoreRetrieveData;

    public FragmentMain() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRcvWork = view.findViewById(R.id.recycle_view_work);
        mRcvWork.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this.getActivity());

        mRcvWork.setLayoutManager(mLayoutManager);

        mStoreRetrieveData = new StoreRetrieveData(getContext());
        listWork = mStoreRetrieveData.loadFromFile();

        mAdapterWork = new AdapterWork(listWork, this);

        View emptyView = view.findViewById(R.id.todoEmpty);
        mRcvWork.setEmptyView(emptyView);

        mRcvWork.setAdapter(mAdapterWork);
        mRcvWork.setItemAnimator(new DefaultItemAnimator());

        mlayoutCoordinator = view.findViewById(R.id.layout_coordinator);
        mfabAddWork = view.findViewById(R.id.fab_add_work);
        mfabAddWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityAddWork.class);
                startActivityForResult(intent, REQUEST_ADD_WORK);
            }
        });

        WorkTouchHandler workTouchHandler = new WorkTouchHandler(mAdapterWork);
        ItemTouchHelper touchHelper = new ItemTouchHelper(workTouchHandler);
        touchHelper.attachToRecyclerView(mRcvWork);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        WorkItem item = null;

        if (data != null) {
            item = (WorkItem)data.getSerializableExtra("work_item");
        }
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADD_WORK:
                    mAdapterWork.addItem(item);
                    break;

                case REQUEST_EDIT_WORK:
                    int index = data.getIntExtra("work_index", -1);
                    mAdapterWork.editItem(index, item);
                    break;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mStoreRetrieveData.saveToFile((ArrayList<WorkItem>)listWork);
    }
}
