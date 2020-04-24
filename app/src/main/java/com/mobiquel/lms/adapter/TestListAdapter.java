package com.mobiquel.lms.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobiquel.lms.R;
import com.mobiquel.lms.interfaces.RecyclerItemClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.OptionsViewHolder> {


    private Context context;
    private List<String> optionsList;
    private RecyclerItemClickListener clickListener;

    public TestListAdapter(List<String> optionsList, RecyclerItemClickListener clickListener) {
        this.optionsList = optionsList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public OptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new OptionsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_test, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OptionsViewHolder holder, final int position) {
        try {
            JSONObject jsonObject = new JSONObject(optionsList.get(position));
            holder.testName.setText(jsonObject.getString("courseName"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return optionsList != null ? optionsList.size() : 0;
    }


    class OptionsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.takeTest)
        Button takeTest;
        @BindView(R.id.testName)
        TextView testName;
        @BindView(R.id.testTime)
        TextView testTime;
        @BindView(R.id.viewDetail)
        TextView viewDetail;
        OptionsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.takeTest)
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.takeTest:
                    if (clickListener != null)
                        clickListener.onRecyclerItemClicked(getAdapterPosition());
                    break;
            }
        }

    }

}
