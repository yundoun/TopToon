package com.example.toptoon;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
    private int currentSelectedMenuIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (adapter == null) { // 어댑터가 아직 생성되지 않았다면 생성
            setupMainMenu();
        }

        setupMainMenu();
        setHeaderAd();
        displayFragment(new HomeFragment(), false);
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

        adapter = new MainMenuRvAdapter();
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
        binding.ivLogo.setOnClickListener(v -> {
            adapter.setSelectedItemPosition(-1); // 선택 리셋
            displayFragment(new HomeFragment(), false);
        });
    }


    @Override
    public void onMainMenuSelected(String menu) {
        Fragment newFragment = null;
        boolean shouldHideAd = true;

        switch (menu) {
            case "연재":
                newFragment = new SerialFragment();
                currentSelectedMenuIndex = 0;
                break;
            case "TOP100":
                newFragment = new Top100Fragment();
                currentSelectedMenuIndex = 1;
                break;
            case "신작":
                newFragment = new NewProductFragment();
                currentSelectedMenuIndex = 2;
                break;
            case "완결":
                newFragment = new CompleteFragment();
                currentSelectedMenuIndex = 3;
                break;
            case "추천무료":
                newFragment = new FreeRecommendFragment();
                currentSelectedMenuIndex = 4;
                break;
            case "전연령":
                newFragment = new AllAgeFragment();
                currentSelectedMenuIndex = 5;
                break;
            case "탑툰쇼츠":
                newFragment = new ShortsFragment();
                currentSelectedMenuIndex = 6;
                break;
            case "이벤트":
                newFragment = new EventFragment();
                currentSelectedMenuIndex = 7;
                break;
            default:
                return;
        }
        displayFragment(newFragment, shouldHideAd);
    }

    private void displayFragment(Fragment fragment, boolean shouldHideAd) {

        // 메뉴 특정 프래그먼트에서 벗어날 때 선택된 항목을 리셋
        if (currentSelectedMenuIndex != -1) {
            adapter.setSelectedItemPosition(-1);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        if (true) {
            transaction.addToBackStack(null);
        }
        transaction.commit();

        binding.ivHeaderAd.setVisibility(shouldHideAd ? View.GONE : View.VISIBLE);

    }

    public void onBackPressed() {
        // 뒤로 가기 내비게이션 처리
        if (currentSelectedMenuIndex != -1) {
            adapter.setSelectedItemPosition(-1);
        }
        super.onBackPressed();
    }


}