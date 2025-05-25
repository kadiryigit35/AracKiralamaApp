package com.example.arackiralamaapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arackiralamaapp.database.entity.Arac;

import java.util.List;

@Dao
public interface AracDao {
    @Query("SELECT * FROM araclar WHERE kiradaMi = 0")
    LiveData<List<Arac>> uygunAraclar();

    @Query("SELECT * FROM araclar WHERE kiradaMi = 1")
    LiveData<List<Arac>> kiralananAraclar();

    @Insert
    void aracEkle(Arac arac);

    @Update
    void aracGuncelle(Arac arac);

    @Delete
    void aracSil(Arac arac);
}