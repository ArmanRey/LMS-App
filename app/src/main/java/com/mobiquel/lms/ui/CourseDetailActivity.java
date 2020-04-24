package com.mobiquel.lms.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.mobiquel.lms.adapter.TestListAdapter;
import com.mobiquel.lms.base.BaseActivity;
import com.mobiquel.lms.interfaces.RecyclerItemClickListener;
import com.mobiquel.lms.network.NetworkConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class CourseDetailActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_toolbar)
    RelativeLayout rlToolbar;
    @BindView(R.id.testLabel)
    TextView testLabel;
    @BindView(R.id.listTests)
    RecyclerView listTests;

    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            jsonObject = new JSONObject(getIntent().getExtras().getString("DATA"));
            tvTitle.setText("Course Details " + jsonObject.getString("courseName"));
            getAvailableTests(jsonObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_course_detail;
    }

    @Override
    protected void initVariables() {
    }

    @Override
    protected void setListeners() {

    }


    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

        }
    }

    private void getAvailableTests(String id) {
        showProgressBar();

        String URL = NetworkConstants.BASE_URL + NetworkConstants.END_POINT_COURSE_TESTS + id;
        JSONObject jsonBody = new JSONObject();
        Log.e("JSON_OBJECT", "=== " + jsonBody);
        JsonObjectRequest request_json = null;

        request_json = new JsonObjectRequest(Request.Method.POST, URL, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideProgressBar();
                        Log.e("RESPO_OTP", response.toString());
                        try {
                            JSONArray testArray=response.getJSONArray("tests");
                            List<String> dataList=new ArrayList<>();
                            for(int i=0;i<testArray.length();i++){
                                dataList.add(testArray.getJSONObject(i).toString());
                            }
                            TestListAdapter mAdapter=new TestListAdapter(dataList, new RecyclerItemClickListener() {
                                @Override
                                public void onRecyclerItemClicked(int position) {

                                }
                            });
                            listTests.setAdapter(mAdapter);
                            listTests.setLayoutManager(new LinearLayoutManager(CourseDetailActivity.this));
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

    }

}
