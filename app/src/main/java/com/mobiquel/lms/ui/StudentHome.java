package com.mobiquel.lms.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import com.mobiquel.lms.base.BaseActivity;
import com.mobiquel.lms.fragment.HomeFragment;
import com.mobiquel.lms.utils.AppConstants;
import com.mobiquel.lms.utils.Preferences;
import com.mobiquel.lms.utils.Utils;
import com.mobiquel.lms.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;
import com.mobiquel.lms.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentHome extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Double lat, lon;
    private Runnable runnable;
    private GoogleApiClient mGoogleApiClient;
    private String regid, version;
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String TAG = "GCM";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String PROPERTY_REG_ID = "registration_id";
    private GoogleCloudMessaging gcm;
    private PackageInfo pInfo = null;
    private List<Fragment> mFragmentList;
    private Fragment selectedFragment;
    private BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         //registerForGCM();

        setListeners();
        setUpBottomNavigationView();
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_student_home;
    }

    @Override
    protected void initVariables() {
        mFragmentList=new ArrayList<>();
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    public void onBackPressed() {

            super.onBackPressed();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        return true;
    }





    public void setListeners() {
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public void setUpBottomNavigationView() {
        Preferences.getInstance().loadPreferences(StudentHome.this);
        mFragmentList.clear();
        selectedFragment = new HomeFragment();
        mFragmentList.add(selectedFragment);
        mFragmentList.add(new com.mobiquel.lms.fragment.Settings());

        for (Fragment fragment : mFragmentList) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fl_container_home, fragment, fragment.getClass().getName());
            fragmentTransaction.hide(fragment);
            fragmentTransaction.commit();
        }
        bottomNavigation.setSelectedItemId(R.id.nav_home);


    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            FragmentTransaction fragmentTransaction;

            switch (menuItem.getItemId()) {


                case R.id.nav_home:
                    fragment = mFragmentList.get(0);
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.hide(selectedFragment);
                    fragmentTransaction.show(fragment);
                    fragmentTransaction.commit();
                    selectedFragment = fragment;
                   return true;
                  case R.id.nav_settings:
                    fragment = mFragmentList.get(1);
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.hide(selectedFragment);
                    fragmentTransaction.show(fragment);
                    fragmentTransaction.commit();
                    selectedFragment = fragment;


                    return true;
                default:
                    return false;
            }
        }
    };

    private void registerForGCM() {
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(StudentHome.this);
            registerInBackground();
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.trim().equals("")) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {

                        gcm = GoogleCloudMessaging.getInstance(StudentHome.this);
                    }
                    regid = gcm.register(AppConstants.GCM_SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    registerForNotificationId(regid);
                    storeRegistrationId(StudentHome.this, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                // mDisplay.append(msg + "\n");
            }
        }.execute(null, null, null);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private SharedPreferences getGcmPreferences(Context context) {
        return getSharedPreferences(StudentHome.class.getSimpleName(), Context.MODE_PRIVATE);
    }

    private void registerForNotificationId(final String notificationId) {
        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();
        String url = AppConstants.BASE_URL + "registerOfficialPushNotificationId";

        StringRequest requestObject = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    Log.e("NOTI_RESPO", "===> " + response.toString());
                    if (responseObject.has("errorCode") && responseObject.getInt("errorCode") == 0) {

                        String version = "";
                        try {

                            pInfo = StudentHome.this.getPackageManager().getPackageInfo(StudentHome.this.getPackageName(), 0);
                            version=pInfo.versionName;
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }

                        if (responseObject.getString("responseObject").equals(version)) {

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(StudentHome.this, R.style.AppCompatAlertDialogStyle);
                            builder.setTitle("Jan Nigraani update available!");
                            builder.setMessage("We strongly recommend you to update the app!");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.TEST_APP_DOWNLOAD_LINK));
                                    startActivity(browserIntent);
                                }
                            });
                            builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog,int id)
                                {
                                    dialog.cancel();

                                }
                            });
                            builder.show();
                        }

                    } else if (responseObject.has("errorCode") && responseObject.getInt("errorCode") == 2) {
                       // Utils.logoutWithOutAPI(StudentHome.this);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StudentHome.this, "Could not connect to server", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Preferences.getInstance().loadPreferences(StudentHome.this);
                String android_id = Settings.Secure.getString(StudentHome.this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                params.put("officialId", Preferences.getInstance().officialId);
                params.put("notificationId", notificationId);
                params.put("deviceId", android_id);
                params.put("deviceOS", "Android");
                params.put("make", "" + Build.MANUFACTURER);
                params.put("model", "" + Build.MODEL);
                params.put("tokenUserId", Preferences.getInstance().officialId);
                params.put("tokenUserType", "official");
                params.put("token", Preferences.getInstance().token);
                Log.e("PARMS",params.toString());
                return params;
            }
        };
        requestObject.setRetryPolicy(new DefaultRetryPolicy(25000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        if (Utils.isNetworkAvailable(StudentHome.this)) {
            queue.add(requestObject);
        } else {
            Utils.showToast(StudentHome.this, AppConstants.MESSAGES.ENABLE_INTERNET_SETTING_MESSAGE);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public static View getToolbarNavigationIcon(Toolbar toolbar){
        //check if contentDescription previously was set
        boolean hadContentDescription = TextUtils.isEmpty(toolbar.getNavigationContentDescription());
        String contentDescription = !hadContentDescription ? (String) toolbar.getNavigationContentDescription() : "navigationIcon";
        toolbar.setNavigationContentDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<View>();
        //find the view based on it's content description, set programatically or with android:contentDescription
        toolbar.findViewsWithText(potentialViews,contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        //Nav icon is always instantiated at this point because calling setNavigationContentDescription ensures its existence
        View navIcon = null;
        if(potentialViews.size() > 0){
            navIcon = potentialViews.get(0); //navigation icon is ImageButton
        }
        //Clear content description if not previously present
        if(hadContentDescription)
            toolbar.setNavigationContentDescription(null);
        return navIcon;
    }
}