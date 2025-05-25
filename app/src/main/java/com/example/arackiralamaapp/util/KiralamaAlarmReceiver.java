package com.example.arackiralamaapp.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.arackiralamaapp.database.Veritabani;
import com.example.arackiralamaapp.database.dao.KiralamaDao;
import com.example.arackiralamaapp.database.entity.Kiralama;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

public class KiralamaAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        KiralamaDao kiralamaDao = Veritabani.getVeritabani(context).kiralamaDao();

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Kiralama> kiralamalar = kiralamaDao.tumKiralamalar().getValue();
            if (kiralamalar == null) return;

            long now = System.currentTimeMillis();
            long next24h = now + 24 * 60 * 60 * 1000;

            for (Kiralama k : kiralamalar) {
                if (k.bitisTarihi >= now && k.bitisTarihi <= next24h) {
                    NotificationHelper.showNotification(context,
                            "Kiralama Süresi Bitiyor",
                            k.musteriAdi + " için araç " + k.aracId + " yakında iade edilmeli.");
                }
            }
        });
    }
}
