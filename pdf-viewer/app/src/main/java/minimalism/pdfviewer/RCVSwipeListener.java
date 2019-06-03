package minimalism.pdfviewer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class RCVSwipeListener extends ItemTouchHelper.SimpleCallback {

    interface RCVItemSwipeCallback {
        void onSwipe(int i);
    }

    RCVItemSwipeCallback mRCVItemSwipeCallback = null;

    public RCVSwipeListener() {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    public void setRCVItemSwipeCallback(RCVItemSwipeCallback callback) {
        mRCVItemSwipeCallback = callback;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (mRCVItemSwipeCallback != null) {
            mRCVItemSwipeCallback.onSwipe(viewHolder.getAdapterPosition());
        }
    }
}
