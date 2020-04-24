package com.mobiquel.lms.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mobiquel.lms.R;
import com.mobiquel.lms.interfaces.RecyclerItemClickListener;
import com.mobiquel.lms.pojo.CourseNameModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingListAdapter extends RecyclerView.Adapter<SettingListAdapter.OptionsViewHolder> {

    private Context context;
    private List<String> optionsList;
    private List<Integer> drawbleList;
    private RecyclerItemClickListener clickListener;

    public SettingListAdapter(List<String> optionsList,List<Integer> drawbleList,RecyclerItemClickListener clickListener) {
        this.optionsList = optionsList;
        this.drawbleList = drawbleList;
        this.clickListener=clickListener;
    }

    @NonNull
    @Override
    public OptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new OptionsViewHolder(LayoutInflater.from(context).inflate(R.layout.settin_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OptionsViewHolder holder, final int position) {
        holder.text.setText(optionsList.get(position));
       /* Drawable left = context.getResources().getDrawable(drawbleList.get(position));
        holder.text.setCompoundDrawables(left,null ,null ,null );
        holder.text.setCompoundDrawablePadding(10);
*/
    }

    @Override
    public int getItemCount() {
        return optionsList != null ? optionsList.size() : 0;
    }


    class OptionsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text1)
        TextView text;

        OptionsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.text1)
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.text1:
                    if (clickListener != null)
                        clickListener.onRecyclerItemClicked(getAdapterPosition());
                    break;
            }
        }

    }


}
