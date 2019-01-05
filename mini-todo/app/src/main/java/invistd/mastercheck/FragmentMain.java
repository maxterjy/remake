package invistd.mastercheck;


import android.os.Bundle;
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
        listWork.add("abc");
        listWork.add("def");
        listWork.add("ghi");
        listWork.add("klm");
        Log.i("thachpham", "size " + listWork.size());


        mAdapterWork = new AdapterWork(listWork);


        mRcvWork.setAdapter(mAdapterWork);

        return view;
    }

}
