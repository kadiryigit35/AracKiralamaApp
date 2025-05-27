package com.example.arackiralamaapp.ui.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arackiralamaapp.R;
import com.example.arackiralamaapp.database.entity.Arac;
import com.example.arackiralamaapp.database.entity.Kiralama;
import com.example.arackiralamaapp.ui.adapter.AracAdapter;
import com.example.arackiralamaapp.ui.fragment.HaritaFragment;
import com.example.arackiralamaapp.ui.viewmodel.AracViewModel;
import com.example.arackiralamaapp.ui.viewmodel.KiralamaViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KiralananlarFragment extends Fragment {

    private AracViewModel aracViewModel;
    private KiralamaViewModel kiralamaViewModel;
    private AracAdapter adapter;

    // Burada kalan süreyi hem gün hem saat olarak tutacak Map
    private Map<Integer, String> kalanSureMap = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_arac_listesi, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvAraclar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AracAdapter();
        recyclerView.setAdapter(adapter);

        aracViewModel = new ViewModelProvider(this).get(AracViewModel.class);
        kiralamaViewModel = new ViewModelProvider(this).get(KiralamaViewModel.class);

        // Kiralanan araçları gözlemle
        aracViewModel.getKiralananAraclar().observe(getViewLifecycleOwner(), araclar -> {
            adapter.submitList(araclar);
            adapter.setKalanGunModu(true);
            updateKalanSureMap(araclar);
        });

        adapter.setOnItemClickListener(arac -> openMapFragment(arac));

        return view;
    }

    private void updateKalanSureMap(List<Arac> araclar) {
        kiralamaViewModel.getTumKiralamalar().observe(getViewLifecycleOwner(), kiralamalar -> {
            kalanSureMap.clear();

            long now = System.currentTimeMillis();

            for (Arac arac : araclar) {
                Kiralama ilgiliKiralama = null;

                for (Kiralama k : kiralamalar) {
                    if (k.aracId == arac.getId()) {
                        ilgiliKiralama = k;
                        break;
                    }
                }

                if (ilgiliKiralama != null) {
                    long kalanMs = ilgiliKiralama.bitisTarihi - now;
                    if (kalanMs > 0) {
                        long kalanGun = kalanMs / (1000 * 60 * 60 * 24);
                        if (kalanGun >= 1) {
                            kalanSureMap.put(arac.getId(), kalanGun + " gün kaldı");
                        } else {
                            long kalanSaat = kalanMs / (1000 * 60 * 60);
                            kalanSaat = Math.max(kalanSaat, 0);
                            kalanSureMap.put(arac.getId(), kalanSaat + " saat kaldı");
                        }
                    } else {
                        kalanSureMap.put(arac.getId(), "Süre doldu");
                    }
                }
            }

            adapter.setKalanSureMap(kalanSureMap);
        });
    }

    private void openMapFragment(Arac arac) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("arac", arac);

        HaritaFragment haritaFragment = new HaritaFragment();
        haritaFragment.setArguments(bundle);

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, haritaFragment)
                .addToBackStack(null)
                .commit();
    }
}
