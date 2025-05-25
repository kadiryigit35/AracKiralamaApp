package com.example.arackiralamaapp.database.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.arackiralamaapp.database.Veritabani;
import com.example.arackiralamaapp.database.dao.AracDao;
import com.example.arackiralamaapp.database.dao.KiralamaDao;
import com.example.arackiralamaapp.database.entity.Arac;
import com.example.arackiralamaapp.database.entity.Kiralama;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KiralamaRepository {

    private final KiralamaDao kDao;
    private final AracDao aDao;
    private final ExecutorService io = Executors.newSingleThreadExecutor();
    private final LiveData<List<Kiralama>> tumKiralamalar;

    public KiralamaRepository(Application app) {
        Veritabani db = Veritabani.getVeritabani(app);
        kDao = db.kiralamaDao();
        aDao = db.aracDao();
        tumKiralamalar = kDao.tumKiralamalar();
    }

    public LiveData<List<Kiralama>> getTumKiralamalar() {
        return tumKiralamalar;
    }

    // Aracı kirala
    public void kirala(Kiralama k) {
        io.execute(() -> {
            kDao.insert(k); // 1) Kiralama kaydı

            // 2) Aracı kirada olarak işaretle
            Arac arac = aDao.getAracById(k.aracId);
            if (arac != null) {
                arac.setKiradaMi(true);
                aDao.updateArac(arac);
            }
        });
    }
}
