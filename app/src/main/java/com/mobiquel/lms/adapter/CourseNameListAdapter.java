package com.mobiquel.lms.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.mobiquel.lms.R;
import com.mobiquel.lms.pojo.CourseNameModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseNameListAdapter extends RecyclerView.Adapter<CourseNameListAdapter.OptionsViewHolder> {

    private Context context;
    private List<CourseNameModel> optionsList;

    public CourseNameListAdapter(List<CourseNameModel> optionsList) {
        this.optionsList = optionsList;
    }

    @NonNull
    @Override
    public OptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new OptionsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_courses, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OptionsViewHolder holder, final int position) {
        holder.text.setText(optionsList.get(position).getTitle());
        holder.text.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    optionsList.get(position).setStats("1");
                else
                    optionsList.get(position).setStats("0");
            }
        });
    }

    @Override
    public int getItemCount() {
        return optionsList != null ? optionsList.size() : 0;
    }


    class OptionsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        CheckBox text;

        OptionsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

    public List<CourseNameModel> getOptionsList(){
        return optionsList;
    }
}
