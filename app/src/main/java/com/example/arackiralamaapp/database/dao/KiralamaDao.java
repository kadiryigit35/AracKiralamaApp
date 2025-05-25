package com.example.arackiralamaapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.arackiralamaapp.database.entity.Kiralama;

import java.util.List;

@Dao
public interface KiralamaDao {

    @Insert
    void insert(Kiralama kiralama);

    @Query("SELECT * FROM kiralamalar")
    LiveData<List<Kiralama>> tumKiralamalar();

    @Query("SELECT * FROM kiralamalar WHERE bitisTarihi BETWEEN :baslangic AND :bitis")
    List<Kiralama> kiralamalarBitisAraliginda(long baslangic, long bitis);
}
