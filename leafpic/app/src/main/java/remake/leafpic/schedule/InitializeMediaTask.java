package remake.leafpic.schedule;

import android.content.Context;
import android.os.AsyncTask;

import remake.leafpic.data.MediaManager;

public class InitializeMediaTask extends AsyncTask<Context, Void, Void> {
    OnPostExecuteCallback mPostCallback;

    public InitializeMediaTask(OnPostExecuteCallback postCallback) {
        mPostCallback = postCallback;
    }

    @Override
    protected Void doInBackground(Context... params) {
        Context context = params[0];
        MediaManager.getInstance().init(context);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        mPostCallback.onPostExecute();
    }

    public interface OnPostExecuteCallback {
        void onPostExecute();
    }
}
