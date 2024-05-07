package com.example.toptoon;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.toptoon.Api.NetworkManager;
import com.example.toptoon.DataModel.ApiItems;
import com.example.toptoon.DataModel.MainMenuItem;
import com.example.toptoon.Fragment.AllAgeFragment;
import com.example.toptoon.Fragment.CompleteFragment;
import com.example.toptoon.Fragment.EventFragment;
import com.example.toptoon.Fragment.FreeRecommendFragment;
import com.example.toptoon.Fragment.HomeFragment;
import com.example.toptoon.Fragment.NewProductFragment;
import com.example.toptoon.Fragment.SerialFragment;
import com.example.toptoon.Fragment.ShortsFragment;
import com.example.toptoon.Fragment.Top100Fragment;
import com.example.toptoon.Ui.MainMenuRvAdapter;
import com.example.toptoon.Ui.OnMainMenuSelectedListener;
import com.example.toptoon.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMainMenuSelectedListener {

    private ActivityMainBinding binding;
    private MainMenuRvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setHeaderAd();
        setupMainMenu();
        displayFragment(new HomeFragment(), "HOME_FRAGMENT", false); // Initially display the Home Fragment
        setupLogoClickEvent();
    }


    private void setHeaderAd() {
        NetworkManager.fetchTopToonItems(new Callback<ApiItems>() {
            @Override
            public void onResponse(@NonNull Call<ApiItems> call, @NonNull Response<ApiItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String headerAdUrl = response.body()
                            .getHeaderAd();
                    Log.println(Log.INFO, "MainActivity", "headerAdUrl: " + headerAdUrl);
                    if (headerAdUrl != null && !headerAdUrl.isEmpty()) {
                        Glide.with(MainActivity.this)
                                .load(headerAdUrl)
                                .into(binding.ivHeaderAd);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiItems> call, @NonNull Throwable t) {
                Log.println(Log.ERROR, "MainActivity", "onFailure: " + t.getMessage());
            }
        });
    }

    private void setupMainMenu() {
        binding.rvMainMenu.setLayoutManager(new GridLayoutManager(this, 4)); // 2열 그리드 설정

        MainMenuRvAdapter adapter = new MainMenuRvAdapter();
        binding.rvMainMenu.setAdapter(adapter);
        adapter.setListener(this);
        adapter.submitList(createMenuItems()); // 데이터 설정
    }

    private List<MainMenuItem> createMenuItems() {
        List<MainMenuItem> menuList = new ArrayList<>();
        for (String item : getResources().getStringArray(R.array.main_menu_items)) {
            menuList.add(new MainMenuItem(item));
        }
        return menuList;
    }

    private void setupLogoClickEvent() {
        binding.ivLogo.setOnClickListener(v -> displayFragment(new HomeFragment(), "HOME_FRAGMENT", false));
    }

    @Override
    public void onMainMenuSelected(String menu) {
        Fragment newFragment;
        boolean shouldHideAd = true;

        switch (menu) {
            case "연재":
                newFragment = new SerialFragment();
                break;
            case "TOP100":
                newFragment = new Top100Fragment();
                break;
            case "신작":
                newFragment = new NewProductFragment();
                break;
            case "완결":
                newFragment = new CompleteFragment();
                break;
            case "추천무료":
                newFragment = new FreeRecommendFragment();
                break;
            case "전연령":
                newFragment = new AllAgeFragment();
                break;
            case "탑툰쇼츠":
                newFragment = new ShortsFragment();
                break;
            case "이벤트":
                newFragment = new EventFragment();
                break;
            default:
                return; // In case of an unrecognized menu item, do nothing.
        }
        displayFragment(newFragment, null, shouldHideAd);
    }

    private void displayFragment(Fragment fragment, String tag, boolean shouldHideAd) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        if (tag != null) {
            transaction.addToBackStack(null);
        }
        transaction.commit();

        if (shouldHideAd) {
            binding.ivHeaderAd.setVisibility(View.GONE);
        } else {
            binding.ivHeaderAd.setVisibility(View.VISIBLE);
        }
    }
}