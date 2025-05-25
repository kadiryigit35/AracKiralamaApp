package com.example.arackiralamaapp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "araclar")
public class Arac {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String ad;

    public String aciklama;

    // Günlük kira ücreti (₺)
    public double gunlukUcret;

    // Araç resmi (URI veya URL)
    public String resimUri;

    // Kiralanma durumu
    public boolean kiradaMi;
}