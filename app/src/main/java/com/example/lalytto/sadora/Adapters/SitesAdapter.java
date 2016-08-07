package com.example.lalytto.sadora.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lalytto.sadora.Models.Sitios;
import com.example.lalytto.sadora.R;
import com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by Lalytto on 6/8/2016.
 */
public class SitesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Sitios> data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    private final String path_img = "http://sadora.lalytto.com/app/src/img/sitios/";
    private static final int TYPE_COLOR = 0;
    private static final int TYPE_FOOTER = 1;

    public SitesAdapter(List<Sitios> data, RecyclerViewOnItemClickListener clickListener) {
        this.data = data;
        this.recyclerViewOnItemClickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof Sitios) {
            return TYPE_COLOR;
        } else {
            return TYPE_FOOTER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_COLOR) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_classes, parent, false);
            return new SitesViewHolder(view, recyclerViewOnItemClickListener);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_classes, parent, false);
            return new SitesViewHolder(view, recyclerViewOnItemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SitesViewHolder) {
            Sitios sitio = (Sitios) data.get(position);
            SitesViewHolder sitesViewHolder = (SitesViewHolder) holder;
            // Set de atributos
            sitesViewHolder.intro.setText(sitio.getSitio_intro());
            sitesViewHolder.name.setText(sitio.getSitio_nombre());
            // Intancia de SmartImagen
            sitesViewHolder.img.setImageUrl(path_img+sitio.getSitio_imagen());
        }
        //FOOTER: nothing to do

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class SitesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView name;
        TextView intro;
        SmartImageView img;


        private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;


        public SitesViewHolder(View itemView, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            name = (TextView)itemView.findViewById(R.id.category_name);
            intro = (TextView)itemView.findViewById(R.id.category_intro);
            img = (SmartImageView)itemView.findViewById(R.id.category_img);

            this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }

}