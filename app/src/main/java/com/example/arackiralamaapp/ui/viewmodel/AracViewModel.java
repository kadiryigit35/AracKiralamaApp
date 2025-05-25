package com.example.arackiralamaapp.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.arackiralamaapp.database.entity.Arac;
import com.example.arackiralamaapp.database.repo.AracRepository;

import java.util.List;

public class AracViewModel extends AndroidViewModel {

    private final AracRepository repo;
    private final LiveData<List<Arac>> uygunAraclar;
    private final LiveData<List<Arac>> kiralananAraclar;

    public AracViewModel(@NonNull Application app) {
        super(app);
        repo = new AracRepository(app);
        uygunAraclar     = repo.getUygunAraclar();
        kiralananAraclar = repo.getKiralananAraclar();
    }

    public LiveData<List<Arac>> getUygunAraclar()     { return uygunAraclar; }
    public LiveData<List<Arac>> getKiralananAraclar() { return kiralananAraclar; }

    public void aracEkle(Arac a)       { repo.aracEkle(a); }
    public void aracGuncelle(Arac a)   { repo.aracGuncelle(a); }
}
