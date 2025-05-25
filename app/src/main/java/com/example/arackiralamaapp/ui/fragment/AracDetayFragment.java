package com.example.arackiralamaapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.arackiralamaapp.R;
import com.example.arackiralamaapp.database.entity.Arac;

public class AracDetayFragment extends Fragment {

    private static final String ARG_ARAC = "arg_arac";
    private Arac arac;

    public static AracDetayFragment newInstance(Arac arac) {
        AracDetayFragment fragment = new AracDetayFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ARAC, arac);  // Arac sınıfı Serializable veya Parcelable olmalı
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            arac = (Arac) getArguments().getSerializable(ARG_ARAC);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_arac_detay, container, false);

        ImageView ivAracResim = view.findViewById(R.id.ivAracResim);
        TextView tvAracAdi = view.findViewById(R.id.tvAracAdiDetay);
        TextView tvAracAciklama = view.findViewById(R.id.tvAracAciklama);
        TextView tvAracFiyat = view.findViewById(R.id.tvAracFiyat);
        Button btnKirala = view.findViewById(R.id.btnKirala);

        if (arac != null) {
            tvAracAdi.setText(arac.getAd());
            tvAracAciklama.setText(arac.getAciklama());
            tvAracFiyat.setText("Günlük Ücret: " + arac.getGunlukUcret() + "₺");

            // Glide ile resmi yükle
            Glide.with(this)
                    .load(arac.getResimUri())
                    .placeholder(R.drawable.ic_placeholder)
                    .into(ivAracResim);
        }

        btnKirala.setOnClickListener(v -> {
            KiralamaFragment kiralamaFragment = KiralamaFragment.newInstance(arac);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, kiralamaFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
