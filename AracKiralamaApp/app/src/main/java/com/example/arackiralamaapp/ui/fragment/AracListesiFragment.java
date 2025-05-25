package com.example.arackiralamaapp.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arackiralamaapp.R;
import com.example.arackiralamaapp.ui.adapter.AracAdapter;
import com.example.arackiralamaapp.ui.viewmodel.AracViewModel;

public class AracListesiFragment extends Fragment {

    private AracViewModel aracViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_arac_listesi, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvAraclar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AracAdapter adapter = new AracAdapter();
        recyclerView.setAdapter(adapter);

        aracViewModel = new ViewModelProvider(this).get(AracViewModel.class);
        aracViewModel.getUygunAraclar().observe(getViewLifecycleOwner(), adapter::submitList);

        return view;
    }
}