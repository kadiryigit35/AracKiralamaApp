package com.example.arackiralamaapp.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.arackiralamaapp.database.entity.Arac;
import com.example.arackiralamaapp.database.repo.AracRepository;

import java.util.List;

public class AracViewModel extends AndroidViewModel {
    private final AracRepository repository;
    private final LiveData<List<Arac>> uygunAraclar;

    public AracViewModel(@NonNull Application application) {
        super(application);
        repository = new AracRepository(application);
        uygunAraclar = repository.getUygunAraclar();
    }

    public LiveData<List<Arac>> getUygunAraclar() {
        return uygunAraclar;
    }

    public void aracEkle(Arac arac) {
        repository.aracEkle(arac);
    }

    public void aracGuncelle(Arac arac) {
        repository.aracGuncelle(arac);
    }
}