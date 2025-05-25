package com.example.arackiralamaapp.database.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;
import java.util.List;
import com.example.arackiralamaapp.database.Veritabani;
import com.example.arackiralamaapp.database.dao.KiralamaDao;
import com.example.arackiralamaapp.database.entity.Kiralama;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KiralamaRepository {

    private final KiralamaDao kiralamaDao;
    private final LiveData<List<Kiralama>> tumKiralamalar;
    private final ExecutorService executorService;

    public KiralamaRepository(Application application) {
        Veritabani db = Veritabani.getVeritabani(application);
        kiralamaDao = db.kiralamaDao();
        tumKiralamalar = kiralamaDao.tumKiralamalar();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Kiralama>> getTumKiralamalar() {
        return tumKiralamalar;
    }

    public void kiralamaEkle(Kiralama kiralama) {
        executorService.execute(() -> kiralamaDao.insert(kiralama));
    }
}
