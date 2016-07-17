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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lalytto on 10/7/2016.
 */
public class RVAdapterSession extends RecyclerView.Adapter<RVAdapterSession.CategoryViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Categorias item);
    }

    private final String path_img = "http://sadora.lalytto.com/app/src/img/categorias/";
    private final List<Categorias> categories;
    private final OnItemClickListener listener;

    public RVAdapterSession(List<Categorias> categories, OnItemClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_categories, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder categoryViewHolder, int i) {
        // Instancia de click
        categoryViewHolder.bind(categories.get(i), listener);
        // Cargar los valores de card
        categoryViewHolder.intro.setText(categories.get(i).getCategoria_descripcion());
        categoryViewHolder.name.setText(categories.get(i).getCategoria_nombre());
        // Graficar la imagen
        categoryViewHolder.img.setImageUrl(path_img+categories.get(i).getCategoria_imagen());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
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

        public void bind(final Categorias categorias, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(categorias);
                }
            });
        }
    }

}
