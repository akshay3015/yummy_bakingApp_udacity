package com.example.android.bakingapp.recepielist;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.beans.Recipe;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {


    private List<Recipe> myItems;
    private ItemListener myListener;

    public RecipeAdapter(List<Recipe> items, ItemListener listener) {
        myItems = items;
        myListener = listener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return myItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(myItems.get(position));
        holder.mTvRecipeName.setText(myItems.get(position).getName());
        holder.mTvServings.setText("Servings : "+ myItems.get(position).getServings());

//        try {
//            List<Steps> mStepList = myItems.get(position).getStepsList();
////            Bitmap bitmap = retriveVideoFrameFromVideo(myItems.get(position).getStepsList().get(mStepList.size()-1).getVideoURL().toString());
//
//            Log.d("videoUrl", "onBindViewHolder: " + myItems.get(position).getStepsList().get(mStepList.size()-1).getVideoURL());
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }

    }

    public interface ItemListener {
        void onItemClick(Recipe item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public Recipe item;

        @BindView(R.id.iv_recipe)
        ImageView mIvRecipe;
        @BindView(R.id.tv_recipe_name)
        TextView mTvRecipeName;
        @BindView(R.id.tv_servings)
        TextView mTvServings;
        @BindView(R.id.cv_recipe)
        CardView mCvRecipe;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);

        }

        public void setData(Recipe item) {
            this.item = item;

        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item);
            }
        }
    }


    // TODO : CREATE IMAGE FROM VIDEO URL FIND A EFFICIENT WAY LOAD BITMAP
    public static Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }


        return bitmap;
    }

}
                                