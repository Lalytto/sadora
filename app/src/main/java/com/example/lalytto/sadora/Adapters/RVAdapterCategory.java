package com.example.lalytto.sadora.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lalytto.sadora.Models.Sitios;
import com.example.lalytto.sadora.R;
import com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by Lalytto on 10/7/2016.
 */
public class RVAdapterCategory extends RecyclerView.Adapter<RVAdapterCategory.CategoryViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Sitios item);
    }

    private final List<Sitios> sitios;
    private final OnItemClickListener listener;

    public RVAdapterCategory(List<Sitios> sitios, OnItemClickListener listener) {
        this.sitios = sitios;
        this.listener = listener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_classes, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder categoryViewHolder, int i) {
        // Instancia de click
        categoryViewHolder.bind(sitios.get(i), listener);
        // Set de atributos
        categoryViewHolder.intro.setText(sitios.get(i).getSitio_intro());
        categoryViewHolder.name.setText(sitios.get(i).getSitio_nombre());
        // Intancia de SmartImagen
        categoryViewHolder.img.setImageUrl(sitios.get(i).getSitio_imagen());
    }

    @Override
    public int getItemCount() {
        return sitios.size();
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

        public void bind(final Sitios sitios, final OnItemClickListener listener){
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override public void onClick(View v) {
                   listener.onItemClick(sitios);
               }
           });
        }

    }

}
