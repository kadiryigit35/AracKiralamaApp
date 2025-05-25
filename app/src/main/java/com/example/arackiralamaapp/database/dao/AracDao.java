package com.example.arackiralamaapp.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arackiralamaapp.database.entity.Arac;

import java.util.List;

@Dao
public interface AracDao {

    @Query("SELECT * FROM araclar WHERE kiradaMi = 0")
    List<Arac> getKiralanmamisAraclar();

    @Insert
    void insert(Arac arac);

    @Update
    void updateArac(Arac arac);

    @Query("SELECT * FROM araclar WHERE id = :id")
    Arac getAracById(int id);
    @Query("SELECT * FROM araclar WHERE kiradaMi = 1")
    List<Arac> getKiralananAraclar();
}
