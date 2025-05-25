package com.example.arackiralamaapp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "araclar")
public class Arac implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String ad;

    private String aciklama;
    private double gunlukUcret;
    private String resimUri;
    private boolean kiradaMi;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @NonNull
    public String getAd() { return ad; }
    public void setAd(@NonNull String ad) { this.ad = ad; }

    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }

    public double getGunlukUcret() { return gunlukUcret; }
    public void setGunlukUcret(double gunlukUcret) { this.gunlukUcret = gunlukUcret; }

    public String getResimUri() { return resimUri; }
    public void setResimUri(String resimUri) { this.resimUri = resimUri; }

    public boolean isKiradaMi() { return kiradaMi; }
    public void setKiradaMi(boolean kiradaMi) { this.kiradaMi = kiradaMi; }
}
