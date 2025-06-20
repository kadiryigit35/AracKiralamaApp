package com.example.arackiralamaapp.database.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "kiralamalar",
        foreignKeys = @ForeignKey(entity = Arac.class,
                parentColumns = "id",
                childColumns = "aracId",
                onDelete = CASCADE),
        indices = @Index("aracId"))
public class Kiralama {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int    aracId;
    public String musteriAdi;
    public String musteriTelefon;
    public int    gunSayisi;
    public long   kiralamaTarihi;
    public long   bitisTarihi;
}
