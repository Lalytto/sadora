package com.example.lalytto.sadora.Adapters;

import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lalytto.sadora.Models.Categorias;
import com.example.lalytto.sadora.Models.Sitios;
import com.example.lalytto.sadora.R;
import com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by Lalytto on 10/7/2016.
 */
public class RVAdapterSession extends RecyclerView.Adapter<RVAdapterSession.CategoryViewHolder> {

    List<Categorias> categories;

    public RVAdapterSession(List<Categorias> categories) {
        this.categories = categories;
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
        return categories.size();
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_category, viewGroup, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder categoryViewHolder, int i) {
        categoryViewHolder.intro.setText(categories.get(i).getCategoria_descripcion());
        categoryViewHolder.name.setText(categories.get(i).getCategoria_nombre());

        Rect rect = new Rect(categoryViewHolder.img.getLeft(), categoryViewHolder.img.getTop(),
                categoryViewHolder.img.getRight(), categoryViewHolder.img.getBottom());
        categoryViewHolder.img.setImageUrl(categories.get(i).getCategoria_imagen());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
