package invistd.mastercheck;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityAddWork extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);

//        String workTitle = savedInstanceState.getString("work_title", "");
//        int workIndex = savedInstanceState.getInt("work_index", -1);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragmentAddWork = new FragmentAddWork();
        transaction.add(R.id.activity_add_work, fragmentAddWork);
        transaction.commit();
    }
}
