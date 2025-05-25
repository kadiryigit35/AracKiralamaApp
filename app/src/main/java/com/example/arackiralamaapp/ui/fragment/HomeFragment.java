package com.example.arackiralamaapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arackiralamaapp.R;
import com.example.arackiralamaapp.ui.adapter.AracAdapter;
import com.example.arackiralamaapp.ui.viewmodel.AracViewModel;

public class HomeFragment extends Fragment {

    private AracViewModel aracViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnKiralananlar = view.findViewById(R.id.btnKiralananlar);
        Button btnIlanEkle = view.findViewById(R.id.btnIlanEkle);

        RecyclerView recyclerView = view.findViewById(R.id.rvAraclar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AracAdapter adapter = new AracAdapter();
        recyclerView.setAdapter(adapter);

        aracViewModel = new ViewModelProvider(this).get(AracViewModel.class);
        aracViewModel.getUygunAraclar().observe(getViewLifecycleOwner(), adapter::submitList);

        btnKiralananlar.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new KiralananlarFragment())
                    .addToBackStack(null)
                    .commit();
        });


        // İlan Ekle butonu tıklama - şimdilik toast veya log ekleyebilirsin
        btnIlanEkle.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new IlanEkleFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Araç listesine tıklandığında AracDetayFragment'e geçiş için
        adapter.setOnItemClickListener(arac -> {
            AracDetayFragment detayFragment = AracDetayFragment.newInstance(arac);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detayFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
