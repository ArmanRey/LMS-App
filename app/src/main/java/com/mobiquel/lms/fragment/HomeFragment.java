package com.mobiquel.lms.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.mobiquel.lms.adapter.CourseNameListAdapter_Home;
import com.mobiquel.lms.base.BaseFragment;
import com.mobiquel.lms.data.DataManager;
import com.mobiquel.lms.data.preferences.PrefKeys;
import com.mobiquel.lms.interfaces.RecyclerItemClickListener;
import com.mobiquel.lms.network.NetworkConstants;
import com.mobiquel.lms.pojo.CourseNameModel;
import com.mobiquel.lms.ui.CourseDetailActivity;
import com.mobiquel.lms.ui.LoginActivity;
import com.mobiquel.lms.ui.SineUpActivity;
import com.mobiquel.lms.ui.StudentHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.upcoLabel)
    TextView upcoLabel;
    @BindView(R.id.listBatch)
    RecyclerView listBatch;
    @BindView(R.id.listCourses)
    RecyclerView listCourses;
    private Context context;

    private List<String> courseList;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private boolean isViewShown = false;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCoursesAvailable();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getCoursesAvailable() {
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
                            Log.e("RESPO_OTP", response.toString());
                            try {
                                JSONArray data = response.getJSONArray("courses");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject jsonObject = data.getJSONObject(i);
                                    courseList.add(jsonObject.toString());
                                }
                                CourseNameListAdapter_Home mAdapter = new CourseNameListAdapter_Home(courseList, new RecyclerItemClickListener() {
                                    @Override
                                    public void onRecyclerItemClicked(int position) {
                                        startActivity(new Intent(getActivity(),CourseDetailActivity.class).putExtra("DATA", courseList.get(position)));
                                    }
                                });
                                listCourses.setLayoutManager(new GridLayoutManager(context,2 ));
                                listCourses.setAdapter(mAdapter);
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

    @Override
    public void initVariables() {
            courseList=new ArrayList<>();
    }

    @Override
    public void setListeners() {

    }
}