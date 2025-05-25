package com.example.arackiralamaapp.database.repo;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.arackiralamaapp.database.Veritabani;
import com.example.arackiralamaapp.database.dao.AracDao;
import com.example.arackiralamaapp.database.entity.Arac;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AracRepository {

    private final AracDao aracDao;
    private final ExecutorService executorService;
    private final MutableLiveData<List<Arac>> uygunAraclar = new MutableLiveData<>();
    private final MutableLiveData<List<Arac>> kiralananAraclar = new MutableLiveData<>(); // 👈 BU SATIRI EKLE

    public AracRepository(Application application) {
        Veritabani db = Veritabani.getVeritabani(application);
        aracDao = db.aracDao();
        executorService = Executors.newSingleThreadExecutor();
        loadUygunAraclar();
    }

    private void loadUygunAraclar() {
        executorService.execute(() -> {
            List<Arac> liste = aracDao.getKiralanmamisAraclar();
            uygunAraclar.postValue(liste);
        });
    }

    public LiveData<List<Arac>> getUygunAraclar() {
        return uygunAraclar;
    }

    public void loadKiralananAraclar() {
        executorService.execute(() -> {
            List<Arac> liste = aracDao.getKiralananAraclar();
            kiralananAraclar.postValue(liste);
        });
    }

    public LiveData<List<Arac>> getKiralananAraclar() {
        return kiralananAraclar;
    }

    public void aracEkle(Arac arac) {
        executorService.execute(() -> {
            aracDao.insert(arac);
            loadUygunAraclar();
        });
    }

    public void aracGuncelle(Arac arac) {
        executorService.execute(() -> {
            aracDao.updateArac(arac);
            loadUygunAraclar();
        });
    }
}
