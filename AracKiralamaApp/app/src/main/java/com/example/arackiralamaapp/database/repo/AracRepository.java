package com.example.arackiralamaapp.database.repo;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.arackiralamaapp.database.Veritabani;
import com.example.arackiralamaapp.database.dao.AracDao;
import com.example.arackiralamaapp.database.entity.Arac;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AracRepository {
    private final AracDao aracDao;
    private final LiveData<List<Arac>> uygunAraclar;
    private final LiveData<List<Arac>> kiralananAraclar;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public AracRepository(Application application) {
        Veritabani db = Veritabani.getVeritabani(application);
        aracDao = db.aracDao();
        uygunAraclar = aracDao.uygunAraclar();
        kiralananAraclar = aracDao.kiralananAraclar();
    }

    public LiveData<List<Arac>> getUygunAraclar() { return uygunAraclar; }
    public LiveData<List<Arac>> getKiralananAraclar() { return kiralananAraclar; }

    public void aracEkle(Arac arac) { executor.execute(() -> aracDao.aracEkle(arac)); }
    public void aracGuncelle(Arac arac) { executor.execute(() -> aracDao.aracGuncelle(arac)); }
}