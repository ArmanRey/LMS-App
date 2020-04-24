package com.mobiquel.lms.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobiquel.lms.R;
import com.mobiquel.lms.interfaces.RecyclerItemClickListener;
import com.mobiquel.lms.pojo.CourseNameModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseNameListAdapter_Home extends RecyclerView.Adapter<CourseNameListAdapter_Home.OptionsViewHolder> {

    private Context context;
    private List<String> optionsList;
    private RecyclerItemClickListener clickListener;

    public CourseNameListAdapter_Home(List<String> optionsList,RecyclerItemClickListener clickListener) {
        this.optionsList = optionsList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public OptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new OptionsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_home_options, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OptionsViewHolder holder, final int position) {
        try {
            JSONObject jsonObject=new JSONObject(optionsList.get(position));
            holder.courseName.setText(jsonObject.getString("courseName"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return optionsList != null ? optionsList.size() : 0;
    }


    class OptionsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.courseName)
        TextView courseName;

        @BindView(R.id.rl_main)
        RelativeLayout rlMain;


        @BindView(R.id.image)
        ImageView image;

        OptionsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @OnClick(R.id.rl_main)
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.rl_main:
                    if (clickListener != null)
                        clickListener.onRecyclerItemClicked(getAdapterPosition());
                    break;
            }
        }

    }

}
