package com.example.arackiralamaapp.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.arackiralamaapp.database.entity.Arac;
import com.example.arackiralamaapp.database.dao.AracDao;
import com.example.arackiralamaapp.database.dao.KiralamaDao;
import com.example.arackiralamaapp.database.entity.Kiralama;

import java.util.concurrent.Executors;

@Database(entities = {Arac.class, Kiralama.class}, version = 3, exportSchema = false)
public abstract class Veritabani extends RoomDatabase {

    private static volatile Veritabani INSTANCE;

    public abstract AracDao aracDao();
    public abstract KiralamaDao kiralamaDao();

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                Veritabani database = INSTANCE;
                if (database != null) {
                    AracDao aracDao = database.aracDao();

                    // Yeni araçlar ekle
                    Arac arac1 = new Arac();
                    arac1.setAd("Toyota Corolla");
                    arac1.setAciklama("Konforlu ve ekonomik sedan");
                    arac1.setGunlukUcret(250);
                    arac1.setResimUri("https://example.com/toyota_corolla.jpg");
                    arac1.setKiradaMi(false);

                    Arac arac2 = new Arac();
                    arac2.setAd("Ford Focus");
                    arac2.setAciklama("Sportif hatchback");
                    arac2.setGunlukUcret(300);
                    arac2.setResimUri("https://example.com/ford_focus.jpg");
                    arac2.setKiradaMi(false);

                    Arac arac3 = new Arac();
                    arac3.setAd("Volkswagen Passat");
                    arac3.setAciklama("Geniş aile aracı");
                    arac3.setGunlukUcret(350);
                    arac3.setResimUri("https://example.com/vw_passat.jpg");
                    arac3.setKiradaMi(false);

                    aracDao.insert(arac1);
                    aracDao.insert(arac2);
                    aracDao.insert(arac3);
                }
            });
        }
    };

    public static Veritabani getVeritabani(final Context context) {
        if (INSTANCE == null) {
            synchronized (Veritabani.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    Veritabani.class, "arac_kiralama_db")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)  // Burada callback ekleniyor
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
