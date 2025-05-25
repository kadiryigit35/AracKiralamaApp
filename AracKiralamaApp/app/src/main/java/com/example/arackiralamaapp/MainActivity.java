package com.example.arackiralamaapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;
import androidx.fragment.app.FragmentTransaction;

import com.example.arackiralamaapp.ui.fragment.AracListesiFragment;
import com.example.arackiralamaapp.database.entity.Arac;
import com.example.arackiralamaapp.ui.viewmodel.AracViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private AracViewModel aracViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Edge-to-edge görünüm ayarı
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.activity_main);

        // Padding ayarı
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return windowInsets;
        });

        // ViewModel oluştur
        aracViewModel = new ViewModelProvider(this).get(AracViewModel.class);

        // Veritabanına örnek veri ekle (sadece ilk açılışta, tekrar tekrar eklememek için kontrol ekleyebilirsin)
        aracViewModel.getUygunAraclar().observe(this, araclar -> {
            if (araclar == null || araclar.size() == 0) {
                // Veritabanında araç yoksa ekle
                Arac yeniArac = new Arac();
                yeniArac.ad = "Toyota Corolla";
                yeniArac.gunlukUcret = 500.0;
                yeniArac.kiradaMi = false;
                yeniArac.resimUri = "https://example.com/images/toyota_corolla.jpg"; // gerçek bir URL olabilir

                aracViewModel.aracEkle(yeniArac);
            }
        });

        // AracListesiFragment'ı yükle
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new AracListesiFragment());
        ft.commit();
    }
}
