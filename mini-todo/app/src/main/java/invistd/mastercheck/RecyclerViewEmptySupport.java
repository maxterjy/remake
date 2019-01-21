package invistd.mastercheck;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RecyclerViewEmptySupport extends RecyclerView {
    View emptyView;

    public RecyclerViewEmptySupport(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewEmptySupport(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
            observer.onChanged();
        }
    }

    AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            updateview();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            updateview();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            updateview();
        }
    };

    private void updateview() {
        Adapter<?> adapter = getAdapter();

        if (adapter.getItemCount() == 0 && emptyView != null) {
            emptyView.setVisibility(VISIBLE);
            RecyclerViewEmptySupport.this.setVisibility(GONE);
        }
        else {
            emptyView.setVisibility(GONE);
            RecyclerViewEmptySupport.this.setVisibility(VISIBLE);
        }
    }

    public void setEmptyView(View view) {
        this.emptyView = view;
    }
}
