package minimalism.pdfviewer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class PageChangeDialog extends DialogFragment {

    interface PageChangeListener {
        void onPageChangeCompleted(int pageNumber);
    }


    EditText mEdtPageNumber;
    PageChangeListener mPageChangeListener = null;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.page_change_layout, null);
        builder.setView(view);
        builder.setTitle("Go To Page");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int num = 1;

                try {
                    num = Integer.valueOf(mEdtPageNumber.getText().toString());
                }
                catch (Exception ex) {
                    num = 1;
                }

                if (mPageChangeListener != null)
                    mPageChangeListener.onPageChangeCompleted(num);
            }
        });
        builder.setNegativeButton("Cancel", null);

        mEdtPageNumber = view.findViewById(R.id.edt_page_number);

        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return dialog;
    }

    public void setPageChangeListener(PageChangeListener listener) {
        mPageChangeListener = listener;
    }
}
