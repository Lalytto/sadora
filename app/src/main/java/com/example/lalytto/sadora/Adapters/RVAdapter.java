package com.example.lalytto.sadora.Adapters;

import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lalytto.sadora.Models.Article;
import com.example.lalytto.sadora.R;
import com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by Lalytto on 10/7/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CategoryViewHolder> {

    List<Article> articles;

    public RVAdapter(List<Article> articles) {
        this.articles = articles;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView intro;
        SmartImageView img;

        CategoryViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            name = (TextView)itemView.findViewById(R.id.category_name);
            intro = (TextView)itemView.findViewById(R.id.category_intro);
            img = (SmartImageView)itemView.findViewById(R.id.category_img);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_category, viewGroup, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder categoryViewHolder, int i) {
        categoryViewHolder.intro.setText(articles.get(i).getIntro());
        categoryViewHolder.name.setText(articles.get(i).getName());

        Rect rect = new Rect(categoryViewHolder.img.getLeft(), categoryViewHolder.img.getTop(),
                categoryViewHolder.img.getRight(), categoryViewHolder.img.getBottom());
        categoryViewHolder.img.setImageUrl(articles.get(i).getUriImg());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
