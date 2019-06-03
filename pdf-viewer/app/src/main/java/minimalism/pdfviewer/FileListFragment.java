package minimalism.pdfviewer;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FileListFragment extends Fragment implements HistoryRCVAdapter.HistoryItemCallback, RCVSwipeListener.RCVItemSwipeCallback {
    static final int REQUEST_FILE_CHOOSER = 100;


    AppCompatActivity mActivity;
    FloatingActionButton mFabOpen;

    HistoryDB mHistoryDB = null;
    List<HistoryItem> mHistoryItems;

    RecyclerView mRcvHistory;
    HistoryRCVAdapter mHistoryAdapter;

    public FileListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AppCompatActivity)getActivity();

        if (mHistoryDB == null)
            mHistoryDB = new HistoryDB(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View outView = inflater.inflate(R.layout.fragment_file_list, container, false);

//        setHasOptionsMenu(true);
        ((MainActivity)getActivity()).getPageNumberTextView().setText("Open");

        mFabOpen = outView.findViewById(R.id.fab_open);
        mFabOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        LinearLayoutManager layoutMgr = new LinearLayoutManager(getActivity());
        layoutMgr.setOrientation(LinearLayoutManager.VERTICAL);

        mRcvHistory = outView.findViewById(R.id.recycler_view_history);
        mRcvHistory.setLayoutManager(layoutMgr);

        mHistoryItems = mHistoryDB.getAllItem();
        mHistoryAdapter = new HistoryRCVAdapter(mHistoryItems);
        mRcvHistory.setAdapter(mHistoryAdapter);
        mHistoryAdapter.setHistoryItemCallback(this);

        RCVSwipeListener swipeListener = new RCVSwipeListener();
        swipeListener.setRCVItemSwipeCallback(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeListener);
        touchHelper.attachToRecyclerView(mRcvHistory);

        return outView;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//        inflater.inflate(R.menu.file_list_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        switch (id) {
//            case R.id.open: {
//                openFileChooser();
//                break;
//            }
//
//            case R.id.about:{
//                break;
//            }
//        }
//
//        return true;
//    }

    void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent, "Select pdf"), REQUEST_FILE_CHOOSER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FILE_CHOOSER && resultCode == Activity.RESULT_OK) {
            String fileUri = data.getDataString();

            Uri uri = data.getData();
            getActivity().getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            enterReadFragment(fileUri);
        }
    }

    void enterReadFragment(String uriStr) {
        saveUriToHistory(uriStr);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = ReadFragment.newInstance(uriStr);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void saveUriToHistory(String uriStr) {
        if (isUriExists(uriStr)) return;

        HistoryItem item = new HistoryItem(uriStr);
        mHistoryDB.addItem(item);
    }

    boolean isUriExists(String uriStr) {
        for(HistoryItem item: mHistoryItems) {
            if (item.getUriStr().compareTo(uriStr) == 0)
                return true;
        }

        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHistoryDB.close();
    }

    @Override
    public void onClick(int pos) {
        HistoryItem item = mHistoryItems.get(pos);
        String uriStr = item.getUriStr();

        enterReadFragment(uriStr);
    }

    @Override
    public void onSwipe(int i) {
        HistoryItem item = mHistoryAdapter.getItem(i);

        mHistoryDB.removeItem(item);
        mHistoryAdapter.removeItem(i);
    }
}
