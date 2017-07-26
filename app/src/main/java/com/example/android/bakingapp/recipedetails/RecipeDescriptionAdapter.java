package com.example.android.bakingapp.recipedetails;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.beans.Steps;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeDescriptionAdapter extends RecyclerView.Adapter<RecipeDescriptionAdapter.ViewHolder> {


    private List<Steps> mStepsList;
    private ItemListener myListener;
    private int selectedPosition = 0;
    private boolean isTwopane = false;
    public RecipeDescriptionAdapter(List<Steps> items, ItemListener listener) {
        mStepsList = items;
        myListener = listener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        isTwopane = parent.getContext().getResources().getBoolean(R.bool.is_two_pane);

        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_steps_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return mStepsList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mStepsList.get(position),position);
        holder.mTvStepDescription.setText(mStepsList.get(position).getShortDescription());
        if (TextUtils.isEmpty( mStepsList.get(position).getVideoURL())) {
           holder.mIvPlay.setVisibility(View.GONE);
        }else {
            holder.mIvPlay.setVisibility(View.VISIBLE);
        }
        if (selectedPosition == position && isTwopane){
            myListener.onItemClick(mStepsList.get(0),position);
        }


    }

    public interface ItemListener {
        void onItemClick(Steps item,int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public Steps item;
        public int position;

        @BindView(R.id.iv_play)
        ImageView mIvPlay;
        @BindView(R.id.tv_step_title)
        TextView mTvStepDescription;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);

        }

        public void setData(Steps item, int position) {
            this.item = item;
            this.position = position;

        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item,position);
            }
        }
    }



}
                                