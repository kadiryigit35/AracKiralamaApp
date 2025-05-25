package com.example.arackiralamaapp.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.arackiralamaapp.database.entity.Kiralama;
import com.example.arackiralamaapp.database.repo.KiralamaRepository;

import java.util.List;

public class KiralamaViewModel extends AndroidViewModel {

    private final KiralamaRepository repo;
    private final LiveData<List<Kiralama>> tumKiralamalar;

    public KiralamaViewModel(@NonNull Application app) {
        super(app);
        repo = new KiralamaRepository(app);
        tumKiralamalar = repo.getTumKiralamalar();
    }

    public LiveData<List<Kiralama>> getTumKiralamalar() {
        return tumKiralamalar;
    }

    public void kirala(Kiralama k) {
        repo.kirala(k);
    }
}
