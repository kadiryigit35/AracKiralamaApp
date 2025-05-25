package com.example.arackiralamaapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arackiralamaapp.database.entity.Arac;

import java.util.List;

@Dao
public interface AracDao {

    @Insert
    void insert(Arac arac);

    @Update
    void updateArac(Arac arac);

    /* DAİMA LİVEDATA DÖN → UI otomatik yenilensin */
    @Query("SELECT * FROM araclar WHERE kiradaMi = 0 ORDER BY id DESC")
    LiveData<List<Arac>> getUygunAraclar();

    @Query("SELECT * FROM araclar WHERE kiradaMi = 1 ORDER BY id DESC")
    LiveData<List<Arac>> getKiralananAraclar();

    @Query("SELECT * FROM araclar WHERE id = :aracId LIMIT 1")
    Arac getAracById(int aracId);            // senkron, repository içinde kullanacağız
}
