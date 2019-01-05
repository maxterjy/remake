package invistd.mastercheck;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentMain extends Fragment {

    RecyclerView mRcvWork;
    RecyclerView.Adapter mAdapterWork;
    RecyclerView.LayoutManager mLayoutManager;

    FloatingActionButton mfabAddWork;
    CoordinatorLayout mlayoutCoordinator;

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

        List<String> listWork = new ArrayList<String>();
        listWork.add("Get Up");
        listWork.add("Run");
        listWork.add("English Speaking");
        listWork.add("Breakfast");
        listWork.add("Algorithm Practice");
        listWork.add("Project Coding");


        mAdapterWork = new AdapterWork(listWork);
        mRcvWork.setAdapter(mAdapterWork);

        mlayoutCoordinator = view.findViewById(R.id.layout_coordinator);
        mfabAddWork = view.findViewById(R.id.fab_add_work);
        mfabAddWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(mlayoutCoordinator, "add", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        return view;
    }

}
