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

    private final AracDao dao;
    private final ExecutorService io = Executors.newSingleThreadExecutor();

    public AracRepository(Application app) {
        dao = Veritabani.getVeritabani(app).aracDao();
    }

    public LiveData<List<Arac>> getUygunAraclar()     { return dao.getUygunAraclar(); }
    public LiveData<List<Arac>> getKiralananAraclar() { return dao.getKiralananAraclar(); }

    public void aracEkle(Arac a) { io.execute(() -> dao.insert(a)); }

    public void aracGuncelle(Arac a) { io.execute(() -> dao.updateArac(a)); }

    /* KiradaMi alanını hızlıca değiştirebilmek için */
    public void setKirada(int aracId, boolean kirada) {
        io.execute(() -> {
            Arac a = dao.getAracById(aracId);
            if (a != null) {
                a.setKiradaMi(kirada);
                dao.updateArac(a);
            }
        });
    }
}
