package com.mobiquel.lms.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.mobiquel.lms.BaseApplication;
import com.mobiquel.lms.R;
import com.mobiquel.lms.base.BaseActivity;
import com.mobiquel.lms.data.DataManager;
import com.mobiquel.lms.data.preferences.PrefKeys;
import com.mobiquel.lms.network.NetworkConstants;
import com.mobiquel.lms.utils.Utils;
import com.mobiquel.lms.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.et_user_email)
    EditText etUserEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.showPwd)
    ImageView showPwd;
    @BindView(R.id.forgotPasswordLabel)
    TextView forgotPasswordLabel;
    @BindView(R.id.rememberMe)
    CheckBox rememberMe;
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
       // ButterKnife.bind(this);

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new DataManager(LoginActivity.this).saveStringInPreference(PrefKeys.MOBILE_NUMBER, etUserEmail.getText().toString());
                    new DataManager(LoginActivity.this).saveStringInPreference(PrefKeys.USER_PASSWORD, etPassword.getText().toString());
                    new DataManager(LoginActivity.this).saveStringInPreference(PrefKeys.REMEMBER_CRED, "1");
                } else {
                    new DataManager(LoginActivity.this).saveStringInPreference(PrefKeys.MOBILE_NUMBER, "");
                    new DataManager(LoginActivity.this).saveStringInPreference(PrefKeys.USER_PASSWORD, "");
                    new DataManager(LoginActivity.this).saveStringInPreference(PrefKeys.REMEMBER_CRED, "0");
                }
            }
        });
        if (new DataManager(LoginActivity.this).getStringFromPreference(PrefKeys.REMEMBER_CRED).equals("1")) {
            etUserEmail.setText(new DataManager(LoginActivity.this).getStringFromPreference(PrefKeys.MOBILE_NUMBER));
            etPassword.setText(new DataManager(LoginActivity.this).getStringFromPreference(PrefKeys.USER_PASSWORD));
            rememberMe.setChecked(true);
        }
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void setListeners() {

    }

    @OnClick({R.id.forgotPasswordLabel,R.id.btn_sign_up, R.id.btn_sign_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forgotPasswordLabel:
                break;
            case R.id.btn_sign_up:
                startActivity(new Intent(LoginActivity.this,SineUpActivity.class));
                break;
            case R.id.btn_sign_in:
                if(etUserEmail.getText().toString().equals(""))
                       showSnackBar("Please enter emailId");
                /*else if(!Utils.isValidEmail(etUserEmail.getText().toString()))
                    showSnackBar("Please enter correct emailId");
               */ else if(etPassword.getText().toString().equals(""))
                    showSnackBar("Please enter password");
                else {
                loginMethod();
                }
                break;
        }
    }


    private void loginMethod() {
        showProgressBar();
        try {
            String URL = NetworkConstants.BASE_URL + NetworkConstants.END_POINT_LOGIN;
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", etUserEmail.getText().toString());
            jsonBody.put("password", etPassword.getText().toString());
            Log.e("JSON_OBJECT", "=== " + jsonBody);
            JsonObjectRequest request_json = null;

            request_json = new JsonObjectRequest(Request.Method.POST, URL, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            hideProgressBar();
                            Log.e("RESPO_OTP", response.toString());
                            try {
                                if(response.getString("errorCode").equals("1")){
                                        showSnackBar(response.getString("errorMessage"));
                                }
                                else {
                                    new DataManager(LoginActivity.this).saveBooleanInPreference(PrefKeys.IS_LOGGED_IN,true);
                                    startActivity(new Intent(LoginActivity.this,StudentHome.class));
                                    new DataManager(LoginActivity.this).saveStringInPreference(PrefKeys.MY_PROFILE,response.getJSONObject("data").toString() );
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideProgressBar();
                    VolleyLog.e("Error: ", error.getMessage());
                    Log.e("Error", "=== " + error.toString());

                }

            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();

                    return headers;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

            };

            request_json.setRetryPolicy(new DefaultRetryPolicy(120000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            BaseApplication.getInstance().addToRequestQueue(request_json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
