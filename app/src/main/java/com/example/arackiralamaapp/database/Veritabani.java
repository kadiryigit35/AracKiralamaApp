package com.example.arackiralamaapp.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.arackiralamaapp.database.dao.AracDao;
import com.example.arackiralamaapp.database.dao.KiralamaDao;
import com.example.arackiralamaapp.database.entity.Arac;
import com.example.arackiralamaapp.database.entity.Kiralama;

@Database(entities = {Arac.class, Kiralama.class}, version = 8, exportSchema = false)
public abstract class Veritabani extends RoomDatabase {

    public abstract AracDao aracDao();
    public abstract KiralamaDao kiralamaDao();

    private static volatile Veritabani INSTANCE;

    public static Veritabani getVeritabani(final Context context) {
        if (INSTANCE == null) {
            synchronized (Veritabani.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    Veritabani.class, "arac_kiralama_db")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)  // callback ekledik
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Veritabanı ilk oluşturulduğunda örnek verileri ekle
            new InsertInitialDataAsyncTask(INSTANCE).execute();
        }
    };

    private static class InsertInitialDataAsyncTask extends AsyncTask<Void, Void, Void> {

        private AracDao aracDao;

        InsertInitialDataAsyncTask(Veritabani db) {
            aracDao = db.aracDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Örnek araçları ekle (örnek resim adları drawable dosya isimleri olmalı)
            Arac arac1 = new Arac();
            arac1.setAd("NİSSAN GTR35");
            arac1.setAciklama("Yüksek performanslı spor araç.");
            arac1.setGunlukUcret(750);
            arac1.setResimAdi("nissan_gtr35");  // drawable/bmw_m3.png olmalı
            arac1.setKiradaMi(false);
            arac1.setLatitude(41.0082);
            arac1.setLongitude(28.9784);
            aracDao.insert(arac1);

            Arac arac2 = new Arac();
            arac2.setAd("Audi A4");
            arac2.setAciklama("Konforlu ve şık sedan.");
            arac2.setGunlukUcret(600);
            arac2.setResimAdi("audi_a4");  // drawable/audi_a4.png olmalı
            arac2.setKiradaMi(false);
            arac2.setLatitude(40.7128);
            arac2.setLongitude(-74.0060);
            aracDao.insert(arac2);

            Arac arac3 = new Arac();
            arac3.setAd("Tesla Model 3");
            arac3.setAciklama("Elektrikli ve çevreci araç.");
            arac3.setGunlukUcret(900);
            arac3.setResimAdi("tesla_model_3");  // drawable/tesla_model_3.png olmalı
            arac3.setKiradaMi(false);
            arac3.setLatitude(37.7749);
            arac3.setLongitude(-122.4194);
            aracDao.insert(arac3);

            return null;
        }
    }
}
