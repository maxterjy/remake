package remake.leafpic.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import remake.leafpic.R;
import remake.leafpic.data.MediaAdapter;


public class MediaFragment extends Fragment {

    RecyclerView mRvAllMedia;

    public MediaFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_media, container, false);

        mRvAllMedia = view.findViewById(R.id.rv_all_media);

        RecyclerView.LayoutManager layoutMgr = new LinearLayoutManager(this.getContext());
        mRvAllMedia.setLayoutManager(layoutMgr);

        MediaAdapter adapter = new MediaAdapter();
        mRvAllMedia.setAdapter(adapter);

        return view;
    }

}
