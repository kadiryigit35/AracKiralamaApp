package com.example.arackiralamaapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arackiralamaapp.R;
import com.example.arackiralamaapp.database.entity.Arac;

public class AracAdapter extends ListAdapter<Arac, AracAdapter.AracViewHolder> {

    public AracAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Arac> DIFF_CALLBACK = new DiffUtil.ItemCallback<Arac>() {
        @Override
        public boolean areItemsTheSame(@NonNull Arac oldItem, @NonNull Arac newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Arac oldItem, @NonNull Arac newItem) {
            return oldItem.ad.equals(newItem.ad) &&
                    oldItem.gunlukUcret == newItem.gunlukUcret &&
                    oldItem.kiradaMi == newItem.kiradaMi;
        }
    };

    static class AracViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewAd;
        private final TextView textViewUcret;
        private final ImageView imageViewArac;

        public AracViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAd = itemView.findViewById(R.id.tvAracAdi);
            textViewUcret = itemView.findViewById(R.id.tvAracUcret);
            imageViewArac = itemView.findViewById(R.id.imageViewArac);
        }
    }

    @NonNull
    @Override
    public AracViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_arac, parent, false);
        return new AracViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AracViewHolder holder, int position) {
        Arac arac = getItem(position);
        holder.textViewAd.setText(arac.ad);
        holder.textViewUcret.setText(String.format("Günlük Ücret: %.2f₺", arac.gunlukUcret));

        if (arac.resimUri != null && !arac.resimUri.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(arac.resimUri)
                    .placeholder(R.drawable.ic_placeholder) // placeholder resmi
                    .into(holder.imageViewArac);
        } else {
            holder.imageViewArac.setImageResource(R.drawable.ic_placeholder);
        }
    }
}
