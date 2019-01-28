package invistd.mastercheck;

import java.io.Serializable;
import java.util.Date;

public class WorkItem implements Serializable {
    String mTitle;
    String mDescription;
    Date mDate;

    public WorkItem(String title, String description, Date date) {
        this.mTitle = title;
        this.mDescription = description;
        this.mDate = date;
    }

    @Override
    public String toString() {
        String out = String.format("%s %s %d", mTitle, mDescription, mDate.getTime());
        return out;
    }
}
