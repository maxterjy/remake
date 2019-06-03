package minimalism.pomodoro;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import minimalism.pomodoro.fragment.MainFragment;
import minimalism.pomodoro.fragment.SlideIntroFragment;

public class MainActivity extends AppCompatActivity {

    SharedPreferences mPref;
    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check is_first_launch
        mPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean isFirstLaunch = mPref.getBoolean("is_first_launch", true);
        mFragmentManager = getSupportFragmentManager();

        if (isFirstLaunch) {
            Log.i("bt", "launch");
            FragmentTransaction transaction = mFragmentManager.beginTransaction();

            SlideIntroFragment fragment = new SlideIntroFragment();
            fragment.setOnFinishCallback(new SlideIntroFragment.OnSlideIntroFinishCallback() {
                @Override
                public void onFinished() {
                    //update is_first_launch
                    SharedPreferences.Editor edt = mPref.edit();
                    edt.putBoolean("is_first_launch", false);
                    edt.commit();
                    
                    launchMainFragment();
                }
            });

            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();
        }
        else {
            launchMainFragment();
        }
    }

    void launchMainFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        MainFragment fragment = new MainFragment();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}
