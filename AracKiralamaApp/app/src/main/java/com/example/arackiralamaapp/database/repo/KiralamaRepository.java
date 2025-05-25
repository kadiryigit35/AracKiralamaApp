package com.example.arackiralamaapp.database.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.arackiralamaapp.database.Veritabani;
import com.example.arackiralamaapp.database.dao.KiralamaDao;
import com.example.arackiralamaapp.database.entity.Kiralama;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KiralamaRepository {
    private final KiralamaDao kiralamaDao;
    private final LiveData<List<Kiralama>> tumKiralamalar;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public KiralamaRepository(Application application) {
        Veritabani db = Veritabani.getVeritabani(application);
        kiralamaDao = db.kiralamaDao();
        tumKiralamalar = kiralamaDao.tumKiralamalar();
    }

    public LiveData<List<Kiralama>> getTumKiralamalar() { return tumKiralamalar; }
    public void kiralamaEkle(Kiralama kiralama) { executor.execute(() -> kiralamaDao.kiralamaEkle(kiralama)); }
    public void getKiralamaBildirimleri(long baslangic, long bitis, OnBildirimHazirlayici callback) {
        executor.execute(() -> {
            List<Kiralama> yaklasanlar = kiralamaDao.kiralamalarBitisAraliginda(baslangic, bitis);
            callback.bildirimHazirla(yaklasanlar);
        });
    }

    public interface OnBildirimHazirlayici {
        void bildirimHazirla(List<Kiralama> yaklasanKiralamalar);
    }
}