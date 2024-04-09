package com.example.toptoon;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.toptoon.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    TopToonApi service = RetrofitClient.getClient().create(TopToonApi.class);
    Call<TopToonItems> call = service.getTopToonItems();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setHeaderAd();
        setupMainMenu();
        displayHomeFragment();

    }

    private void setHeaderAd(){
        NetworkManager.fetchTopToonItems(new Callback<TopToonItems>() {
            @Override
            public void onResponse(@NonNull Call<TopToonItems> call, @NonNull Response<TopToonItems> response) {
                if(response.isSuccessful() && response.body() != null){
                    String headerAdUrl = response.body()
                            .getHeaderAd();
                    Log.println(Log.INFO, "MainActivity", "headerAdUrl: " + headerAdUrl);
                    if(headerAdUrl != null && !headerAdUrl.isEmpty()){
                        Glide.with(MainActivity.this)
                                .load(headerAdUrl)
                                .into(binding.ivHeaderAd);
                    }
                }
            }
            @Override
            public void onFailure(Call<TopToonItems> call, Throwable t) {
                Log.println(Log.ERROR, "MainActivity", "onFailure: " + t.getMessage());
            }
        });
    }

    private void setupMainMenu() {
        binding.rvMainMenu.setLayoutManager(new GridLayoutManager(this, 4)); // 2열 그리드 설정

        MainMenuRvAdapter adapter = new MainMenuRvAdapter();
        binding.rvMainMenu.setAdapter(adapter);
        List<MainMenuItem> menuList = createMenuItems(); // 메뉴 아이템 데이터 리스트 생성
        adapter.submitList(menuList); // 데이터 설정
    }

    private List<MainMenuItem> createMenuItems() {
        List<MainMenuItem> menuList = new ArrayList<>();
        for (String item : getResources().getStringArray(R.array.main_menu_items)) {
            menuList.add(new MainMenuItem(item));
        }
        return menuList;
    }

    private void displayHomeFragment() {
        // HomeFragment 인스턴스 생성
        HomeFragment homeFragment = new HomeFragment();

        // FragmentManager를 사용하여 FragmentTransaction을 시작
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // FragmentTransaction을 사용하여 FragmentContainerView에 HomeFragment 추가
        fragmentTransaction.add(R.id.fragmentContainer, homeFragment);
        fragmentTransaction.commit(); // 변경사항을 커밋하여 적용
    }

}