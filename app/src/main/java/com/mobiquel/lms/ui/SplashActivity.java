package com.mobiquel.lms.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mobiquel.lms.R;
import com.mobiquel.lms.data.DataManager;
import com.mobiquel.lms.data.preferences.PrefKeys;
import com.mobiquel.lms.utils.Preferences;

public class SplashActivity extends AppCompatActivity {


    private static final int SPLASH_HOLD_TIME = 2500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Preferences.getInstance().loadPreferences(SplashActivity.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(new DataManager(SplashActivity.this).getBooleanFromPreference(PrefKeys.IS_LOGGED_IN) ){
                    if (!isFinishing()) {
                        Intent intent = new Intent(SplashActivity.this, StudentHome.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.left_out, R.anim.right_in);
                        finish();
                    }
                }
                else if(new DataManager(SplashActivity.this).getBooleanFromPreference(PrefKeys.IS_LOGGED_IN) && new DataManager(SplashActivity.this).getStringFromPreference(PrefKeys.IS_LOGGED_IN).equals("Student")){
                    if (!isFinishing()) {
                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.left_out, R.anim.right_in);
                        finish();
                    }
                }
                else {
                    if (!isFinishing()) {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.left_out, R.anim.right_in);
                        finish();
                    }
                }

            }
        }, SPLASH_HOLD_TIME);
    }

}
