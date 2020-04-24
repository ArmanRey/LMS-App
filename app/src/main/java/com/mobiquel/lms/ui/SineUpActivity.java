package com.mobiquel.lms.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mobiquel.lms.BaseApplication;
import com.mobiquel.lms.R;
import com.mobiquel.lms.adapter.CourseNameListAdapter;
import com.mobiquel.lms.adapter.SurveyWeeknNameListAdapter;
import com.mobiquel.lms.base.BaseActivity;
import com.mobiquel.lms.network.NetworkConstants;
import com.mobiquel.lms.pojo.CourseNameModel;
import com.mobiquel.lms.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SineUpActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.l1)
    LinearLayout l1;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_toolbar)
    RelativeLayout rlToolbar;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.workTypeRadioGroup)
    RadioGroup workTypeRadioGroup;
    @BindView(R.id.et_role)
    EditText etRole;
    @BindView(R.id.t1)
    TextView t1;
    @BindView(R.id.t2)
    TextView t2;
    @BindView(R.id.et_organization)
    EditText etOrganization;
    @BindView(R.id.sessionTimeRadioGroup)
    RadioGroup sessionTimeRadioGroup;
    @BindView(R.id.courseList)
    RecyclerView courseList;
    @BindView(R.id.list)
    RecyclerView list;
    private CourseNameListAdapter adapter;
    private SurveyWeeknNameListAdapter mAdapter;
    private List<CourseNameModel> allCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getAllCourse();
        tvTitle.setText("Enrollment Form ");

        workTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                l1.setVisibility(View.VISIBLE);
                RadioButton rBtn = findViewById(i);
                if (rBtn.getText().toString().contains("Work")) {
                    t1.setText("Role");
                    t2.setText("Organization");
                    etRole.setHint("Role");
                    etOrganization.setHint("Organization");
                } else {
                    t1.setText("Course");
                    t2.setText("College");
                    etRole.setHint("Course");
                    etOrganization.setHint("College");
                }

            }
        });

        String[] aboutArray = getResources().getStringArray(R.array.about);
        List<CourseNameModel> abouArray2 = new ArrayList<>();
        for (int i = 0; i < aboutArray.length; i++) {
            CourseNameModel model = new CourseNameModel();
            model.setTitle(aboutArray[i]);
            abouArray2.add(model);
        }

        mAdapter = new SurveyWeeknNameListAdapter(abouArray2);
        list.setAdapter(mAdapter);
        list.setLayoutManager(new LinearLayoutManager(SineUpActivity.this));

    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_sineup;
    }

    @Override
    protected void initVariables() {
        allCourses = new ArrayList<>();
    }

    @Override
    protected void setListeners() {

    }


    @OnClick({R.id.iv_back, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.submit:
                if (etName.getText().toString().equals(""))
                    showSnackBar("Please enter name!");
                else if (etEmail.getText().toString().equals(""))
                    showSnackBar("Please enter emailid!");
                else if (!Utils.isValidEmail(etEmail.getText().toString()))
                    showSnackBar("Please enter valid emailid!");
                else if (!Utils.validatePhoneNumber(etMobile.getText().toString()))
                    showSnackBar("Please enter valid mobile number!");
                else if (workTypeRadioGroup.getCheckedRadioButtonId() == -1)
                    showSnackBar("Please select anyone type!");
                else if (etRole.getText().toString().equals(""))
                    showSnackBar("Please enter course/role!");
                else if (etOrganization.getText().toString().equals(""))
                    showSnackBar("Please enter college/organization!");
                else if (sessionTimeRadioGroup.getCheckedRadioButtonId() == -1)
                    showSnackBar("Please select preferred timing of sessions!");
                else if (etPassword.getText().toString().equals(""))
                    showSnackBar("Please enter password!");
                else {
                    List<CourseNameModel> list = adapter.getOptionsList();
                    List<CourseNameModel> list2 = mAdapter.getOptionsList();
                    String courseIds = "";
                    for (CourseNameModel model : list) {
                        if (model.getStats().equals("1"))
                            courseIds = courseIds + model.getId() + ",";
                    }


                    String courseIds2 = "";
                    for (CourseNameModel model : list2) {
                        if (model.getStats().equals("1"))
                            courseIds2 = courseIds2 + model.getTitle() + ",";
                    }

                    sineUpStudents(courseIds, courseIds2);
                }

                break;
        }
    }

    private void getAllCourse() {
        showProgressBar();

        String URL = NetworkConstants.BASE_URL + NetworkConstants.END_POINT_ALL_COURSE;
        JSONObject jsonBody = new JSONObject();
        Log.e("JSON_OBJECT", "=== " + jsonBody);
        JsonObjectRequest request_json = null;

        request_json = new JsonObjectRequest(Request.Method.POST, URL, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideProgressBar();
                        try {
                            JSONArray data = response.getJSONArray("courses");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jsonObject = data.getJSONObject(i);
                                CourseNameModel model = new CourseNameModel();
                                model.setTitle(jsonObject.getString("courseName"));
                                model.setId(jsonObject.getString("id"));
                                allCourses.add(model);
                            }
                            adapter = new CourseNameListAdapter(allCourses);
                            courseList.setAdapter(adapter);
                            courseList.setLayoutManager(new LinearLayoutManager(SineUpActivity.this));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("RESPO_OTP", response.toString());

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

    }

    private void sineUpStudents(String ids,String ids2) {
        showProgressBar();
        try {
            String URL = NetworkConstants.BASE_URL + NetworkConstants.END_POINT_SINE_UP;
            RadioButton rBtn = findViewById(workTypeRadioGroup.getCheckedRadioButtonId());
            RadioButton rBtn2 = findViewById(sessionTimeRadioGroup.getCheckedRadioButtonId());
            JSONObject dataObject = new JSONObject();
            dataObject.put("name",etName.getText().toString());
            dataObject.put("mobile",etMobile.getText().toString());
            dataObject.put("email",etEmail.getText().toString());
            dataObject.put("studentType",rBtn.getText().toString());
            dataObject.put("interestedCourseIds",ids);
            dataObject.put("details",etRole.getText().toString()+"\n"+etOrganization.getText().toString());
            dataObject.put("sessionType",rBtn2.getText().toString());
            dataObject.put("source",ids2);
            dataObject.put("password",etPassword.getText().toString());


            Log.e("JSON_OBJECT", "=== " + dataObject);
            JsonObjectRequest request_json = null;

            request_json = new JsonObjectRequest(Request.Method.POST, URL, dataObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            hideProgressBar();

                            Log.e("RESPO_OTP", response.toString());
                            try {
                                Utils.showToast(SineUpActivity.this,response.getString("errorMessage") );
                                finish();
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
