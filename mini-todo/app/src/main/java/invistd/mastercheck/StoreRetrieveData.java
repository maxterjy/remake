package invistd.mastercheck;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.Buffer;
import java.util.ArrayList;

public class StoreRetrieveData {
    Context mContext;

    public StoreRetrieveData(Context context) {
        mContext = context;
    }

    public void saveToFile(ArrayList<WorkItem> items) {
        try {
            FileOutputStream fos = new FileOutputStream("/sdcard/Android/test.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            int size = items.size();
            for(int i = 0; i < size; i++) {
                oos.writeObject(items.get(i));
            }

            oos.close();
            fos.close();
        }
        catch (Exception ex) {
            Log.e("thachpham", "saveToFile failed: " + ex.toString());
        }
    }

    public ArrayList<WorkItem> loadFromFile() {
        ArrayList<WorkItem> workList = new ArrayList<>();

       try {
           FileInputStream fis = new FileInputStream("/sdcard/Android/test.txt");
           ObjectInputStream ois = new ObjectInputStream(fis);

           WorkItem item = null;
           while ((item = (WorkItem)ois.readObject()) != null) {
               workList.add(item);
           }

           ois.close();
           fis.close();
       }
       catch (Exception ex) {
           Log.e("thachpham", "loadFromFile failed: " + ex.toString());
       }

       return workList;
    }
}
