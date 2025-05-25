package com.example.arackiralamaapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.arackiralamaapp.R;
import com.example.arackiralamaapp.database.entity.Arac;
import com.example.arackiralamaapp.ui.viewmodel.AracViewModel;

public class IlanEkleFragment extends Fragment {

    private EditText etAracAd, etAciklama, etUcret, etResimUri;
    private Button btnKaydet;
    private AracViewModel aracViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ilan_ekle, container, false);

        etAracAd = view.findViewById(R.id.etAracAd);
        etAciklama = view.findViewById(R.id.etAciklama);
        etUcret = view.findViewById(R.id.etUcret);
        etResimUri = view.findViewById(R.id.etResimUri);
        btnKaydet = view.findViewById(R.id.btnAracKaydet);

        aracViewModel = new ViewModelProvider(this).get(AracViewModel.class);

        btnKaydet.setOnClickListener(v -> {
            String ad = etAracAd.getText().toString().trim();
            String aciklama = etAciklama.getText().toString().trim();
            String ucretStr = etUcret.getText().toString().trim();
            String resimUri = etResimUri.getText().toString().trim();

            if (ad.isEmpty() || ucretStr.isEmpty()) {
                Toast.makeText(getContext(), "Lütfen en azından ad ve ücret girin", Toast.LENGTH_SHORT).show();
                return;
            }

            double ucret = Double.parseDouble(ucretStr);

            Arac yeniArac = new Arac();
            yeniArac.setAd(ad);
            yeniArac.setAciklama(aciklama);
            yeniArac.setGunlukUcret(ucret);
            yeniArac.setResimUri(resimUri);
            yeniArac.setKiradaMi(false);  // ilan, boşta araç olarak eklenecek

            aracViewModel.aracEkle(yeniArac);
            Toast.makeText(getContext(), "Araç başarıyla eklendi", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}
