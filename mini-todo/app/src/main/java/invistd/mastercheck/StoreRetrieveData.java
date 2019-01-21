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
import java.io.OutputStreamWriter;
import java.nio.Buffer;
import java.util.ArrayList;

public class StoreRetrieveData {
    Context mContext;

    public StoreRetrieveData(Context context) {
        mContext = context;
    }

    public void saveToFile(ArrayList<String> items) {
        try {
            FileWriter fileWriter = new FileWriter("/sdcard/Android/test.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            int size = items.size();
            for(int i = 0; i < size; i++) {
                bufferedWriter.write(items.get(i));
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileWriter.close();
        }
        catch (Exception ex) {
            Log.e("thachpham", "saveToFile failed: " + ex.toString());
        }
    }

    public ArrayList<String> loadFromFile() {
        ArrayList<String> workList = new ArrayList<>();

       try {
           FileReader fileReader = new FileReader("/sdcard/Android/test.txt");
           BufferedReader bufferedReader = new BufferedReader(fileReader);

           String str = null;
           while ((str = bufferedReader.readLine()) != null) {
               Log.i("thachpham", str);
               workList.add(str);
           }

           bufferedReader.close();
           fileReader.close();
       }
       catch (Exception ex) {
           Log.e("thachpham", "loadFromFile failed: " + ex.toString());
       }

       return workList;
    }
}
