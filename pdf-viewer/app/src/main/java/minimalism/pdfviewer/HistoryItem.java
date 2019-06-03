package minimalism.pdfviewer;

import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class HistoryItem implements BaseColumns {
    public String mUriStr;
    public long mLastOpenTime = 0;

    public HistoryItem(String uriStr) {
        mUriStr = uriStr;
    }

    public HistoryItem(String uriStr, long lastOpenTime) {
        mUriStr = uriStr;
        mLastOpenTime = lastOpenTime;
    }

    public String getFileName() {
        String decodeStr = Uri.decode(mUriStr);

        int lo = decodeStr.lastIndexOf('/');
        int hi = decodeStr.lastIndexOf('.');

        String name = decodeStr.substring(lo+1, hi);

        return name;
    }

    public String getUriStr() {
        return mUriStr;
    }

    //TODO
    public long getLastOpenTime() {
        return mLastOpenTime;
    }

    @Override
    public String toString() {
        return String.format("uri: %s last: %d", mUriStr, mLastOpenTime);
    }

    public boolean hasSameUri(HistoryItem item){
        return (getUriStr().compareTo(item.getUriStr()) == 0);
    }
}
