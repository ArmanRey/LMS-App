package com.mobiquel.lms.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobiquel.lms.adapter.SettingListAdapter;
import com.mobiquel.lms.interfaces.RecyclerItemClickListener;

import com.mobiquel.lms.R;
import com.mobiquel.lms.utils.AppConstants;
import com.mobiquel.lms.utils.Preferences;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Settings extends Fragment {
    @BindView(R.id.dataList)
    RecyclerView dataList;
    Unbinder unbinder;
    private ArrayList<String> listData = new ArrayList<>();
    private ArrayList<Integer> drawableData = new ArrayList<>();
    private Context context;

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
        View view = inflater.inflate(R.layout.fragment_setting, null);
        unbinder = ButterKnife.bind(this, view);

        listData.add("My Profile");
        listData.add("About LMS");
        listData.add("Feedback");
        listData.add("Share");
        listData.add("Logout");


        SettingListAdapter mAdapter=new SettingListAdapter(listData, drawableData, new RecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClicked(int pos) {
                Intent i;
                if (pos == 0) {
                    /*i = new Intent(getActivity(), OfficialProfile.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.left_out, R.anim.right_in);
*/
                } else if (pos == 1) {
                  /*  i = new Intent(getActivity(), AboutUs.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.left_out, R.anim.right_in);
*/
                } else if (pos == 2) {
                  /*  i = new Intent(getActivity(), AppFeedback.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.left_out, R.anim.right_in);
*/
                } else if (pos == 3) {
                    i = new Intent();
                    i.setAction(Intent.ACTION_SEND);
                    String s1 = "Hi! Please Download Jan Nigrani app from this link:\n" + AppConstants.TEST_APP_DOWNLOAD_LINK;
                    i.putExtra(Intent.EXTRA_SUBJECT, "Jan Nigrani");
                    i.putExtra(Intent.EXTRA_TEXT, s1);
                    i.setType("text/plain");
                    startActivity(i);
                } else if (pos == 4) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            getActivity(), R.style.AppCompatAlertDialogStyle);
                        builder.setTitle("Logout from LMS!");
                        builder.setMessage("Are you sure you want to logout ?");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });

                    builder.show();
                }
            }
        });

        dataList.setAdapter(mAdapter);
        dataList.setLayoutManager(new LinearLayoutManager(context));
       

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            isViewShown = true;
        } else {
            isViewShown = false;
        }
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



}