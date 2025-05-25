package com.example.arackiralamaapp.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.arackiralamaapp.database.entity.Arac;
import com.example.arackiralamaapp.database.dao.AracDao;
import com.example.arackiralamaapp.database.dao.KiralamaDao;
import com.example.arackiralamaapp.database.entity.Kiralama;

@Database(entities = {Arac.class, Kiralama.class}, version = 1, exportSchema = false)
public abstract class Veritabani extends RoomDatabase {

    private static volatile Veritabani INSTANCE;

    public abstract AracDao aracDao();
    public abstract KiralamaDao kiralamaDao();

    public static Veritabani getVeritabani(final Context context) {
        if (INSTANCE == null) {
            synchronized (Veritabani.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    Veritabani.class, "arac_kiralama_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
