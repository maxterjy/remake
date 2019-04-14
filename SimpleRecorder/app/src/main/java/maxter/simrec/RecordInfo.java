package maxter.simrec;

public class RecordInfo {
    public int mId;
    public String mName;
    public String mPath;
    public int mLength;
    public long mCreatedTime;

    public RecordInfo(String name, String path, int length, long createdTime) {
        mName = name;
        mPath = path;
        mLength = length;
        mCreatedTime = createdTime;
    }

    public RecordInfo() {

    }
}
