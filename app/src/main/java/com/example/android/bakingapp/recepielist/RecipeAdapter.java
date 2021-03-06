package com.example.android.bakingapp.recepielist;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.beans.Recipe;
import com.squareup.picasso.Picasso;

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
        if (TextUtils.isEmpty(myItems.get(position).getImage())){
            Picasso.with(holder.mIvRecipe.getContext())
                    .load(R.drawable.error)
            .into(holder.mIvRecipe);
        }else {
            Picasso.with(holder.mIvRecipe.getContext())
                    .load(myItems.get(position).getImage())
            .error(R.drawable.ic_error_outline_black_24dp)
            .placeholder(R.drawable.error)
            .into(holder.mIvRecipe);
        }



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
                                