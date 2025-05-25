package com.example.arackiralamaapp.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.arackiralamaapp.R;
import com.example.arackiralamaapp.database.entity.Arac;
import com.example.arackiralamaapp.database.entity.Kiralama;
import com.example.arackiralamaapp.ui.viewmodel.AracViewModel;
import com.example.arackiralamaapp.ui.viewmodel.KiralamaViewModel;

public class KiralamaFragment extends Fragment {

    private static final String ARG_ARAC = "arg_arac";

    private Arac arac;

    private TextView tvAracAdi;
    private EditText etAdSoyad, etTelefon, etKiralamaGunSayisi;
    private Button btnKiralamaKaydet;

    private KiralamaViewModel kiralamaViewModel;
    private AracViewModel aracViewModel;

    public static KiralamaFragment newInstance(Arac arac) {
        KiralamaFragment fragment = new KiralamaFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ARAC, arac);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kiralama, container, false);

        tvAracAdi = view.findViewById(R.id.tvKiralamaAracAdi);
        etAdSoyad = view.findViewById(R.id.etAdSoyad);
        etTelefon = view.findViewById(R.id.etTelefon);
        etKiralamaGunSayisi = view.findViewById(R.id.etKiralamaGunSayisi);
        btnKiralamaKaydet = view.findViewById(R.id.btnKiralamaKaydet);

        kiralamaViewModel = new ViewModelProvider(this).get(KiralamaViewModel.class);
        aracViewModel = new ViewModelProvider(this).get(AracViewModel.class);

        if (getArguments() != null) {
            arac = (Arac) getArguments().getSerializable(ARG_ARAC);
            if (arac != null) {
                tvAracAdi.setText(arac.getAd());
            }
        }

        btnKiralamaKaydet.setOnClickListener(v -> {
            String adSoyad = etAdSoyad.getText().toString().trim();
            String telefon = etTelefon.getText().toString().trim();
            String gunSayisiStr = etKiralamaGunSayisi.getText().toString().trim();

            if (TextUtils.isEmpty(adSoyad) || TextUtils.isEmpty(telefon) || TextUtils.isEmpty(gunSayisiStr)) {
                Toast.makeText(getContext(), "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                return;
            }

            int gunSayisi = Integer.parseInt(gunSayisiStr);
            long now = System.currentTimeMillis();

            Kiralama kiralama = new Kiralama();
            kiralama.aracId = arac.getId();
            kiralama.musteriAdi = adSoyad;
            kiralama.musteriTelefon = telefon;
            kiralama.gunSayisi = gunSayisi;
            kiralama.kiralamaTarihi = now;
            kiralama.bitisTarihi = now + gunSayisi * 24L * 60 * 60 * 1000;

            kiralamaViewModel.kirala(kiralama);

            arac.setKiradaMi(true);
            aracViewModel.aracGuncelle(arac);

            Toast.makeText(getContext(), "Kiralama başarıyla kaydedildi!", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}