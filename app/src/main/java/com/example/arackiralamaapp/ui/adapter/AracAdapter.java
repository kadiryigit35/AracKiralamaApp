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

import java.util.HashMap;
import java.util.Map;

public class AracAdapter extends ListAdapter<Arac, AracAdapter.AracViewHolder> {

    private OnItemClickListener listener;

    // true ise kalan süre modu aktif, false ise günlük ücret gösterilir
    private boolean kalanGunModu = false;

    // Araç ID'sine göre kalan süreyi (gün/saat metni) tutan Map
    private Map<Integer, String> kalanSureMap = new HashMap<>();

    public AracAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Arac> DIFF_CALLBACK = new DiffUtil.ItemCallback<Arac>() {
        @Override
        public boolean areItemsTheSame(@NonNull Arac oldItem, @NonNull Arac newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Arac oldItem, @NonNull Arac newItem) {
            return oldItem.equals(newItem);
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

        if (kalanGunModu) {
            // Kalan süre modu aktifse kalanSureMap'ten metni al ve göster
            String kalanSure = kalanSureMap.get(currentArac.getId());
            if (kalanSure != null) {
                holder.tvAracUcret.setText(kalanSure);
            } else {
                holder.tvAracUcret.setText("Süre bilgisi yok");
            }
        } else {
            // Normal mod: Günlük ücret göster
            holder.tvAracUcret.setText("Günlük Ücret: " + currentArac.getGunlukUcret() + "₺");
        }

        // Burada arac.getResimAdi() ile drawable'dan resmi alıyoruz
        String resimAdi = currentArac.getResimAdi();
        if (resimAdi != null && !resimAdi.isEmpty()) {
            int resId = holder.itemView.getContext().getResources()
                    .getIdentifier(resimAdi, "drawable", holder.itemView.getContext().getPackageName());

            if (resId != 0) {
                holder.imageViewArac.setImageResource(resId);
            } else {
                // Resim bulunamadıysa placeholder göster
                holder.imageViewArac.setImageResource(R.drawable.ic_placeholder);
            }
        } else {
            // Resim adı boşsa placeholder göster
            holder.imageViewArac.setImageResource(R.drawable.ic_placeholder);
        }
    }

    public Arac getAracAt(int position) {
        return getItem(position);
    }

    public void setKalanGunModu(boolean aktif) {
        this.kalanGunModu = aktif;
        notifyDataSetChanged();
    }

    // Kalan süre map'i dışarıdan set edilecek
    public void setKalanSureMap(Map<Integer, String> kalanSureMap) {
        this.kalanSureMap = kalanSureMap;
        notifyDataSetChanged();
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
