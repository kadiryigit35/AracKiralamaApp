package com.example.arackiralamaapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arackiralamaapp.R;
import com.example.arackiralamaapp.database.entity.Arac;

public class AracAdapter extends ListAdapter<Arac, AracAdapter.AracViewHolder> {

    private OnItemClickListener listener;

    public AracAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Arac> DIFF_CALLBACK = new DiffUtil.ItemCallback<Arac>() {
        @Override
        public boolean areItemsTheSame(@NonNull Arac oldItem, @NonNull Arac newItem) {
            return oldItem.getId() == newItem.getId(); // ID eşleşmesi
        }

        @Override
        public boolean areContentsTheSame(@NonNull Arac oldItem, @NonNull Arac newItem) {
            return oldItem.equals(newItem); // equals metoduna göre kontrol (Arac entity'sinde override edilmeli)
        }
    };

    @NonNull
    @Override
    public AracViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_arac, parent, false);
        return new AracViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AracViewHolder holder, int position) {
        Arac currentArac = getItem(position);
        holder.tvAracAdi.setText(currentArac.getAd());
        holder.tvAracUcret.setText("Günlük Ücret: " + currentArac.getGunlukUcret() + "₺");

        // Resim gösterme (örnek placeholder, Glide veya Picasso ile geliştirebilirsin)
        holder.imageViewArac.setImageResource(R.drawable.ic_placeholder);
    }

    class AracViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAracAdi, tvAracUcret;
        private ImageView imageViewArac;

        public AracViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAracAdi = itemView.findViewById(R.id.tvAracAdi);
            tvAracUcret = itemView.findViewById(R.id.tvAracUcret);
            imageViewArac = itemView.findViewById(R.id.imageViewArac);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Arac arac);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
