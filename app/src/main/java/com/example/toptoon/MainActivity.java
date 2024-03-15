package com.example.toptoon;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.toptoon.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.RvMainMenu.setLayoutManager(new GridLayoutManager(this, 4)); // 2열 그리드 설정

        MainMenuAdapter adapter = new MainMenuAdapter();
        binding.RvMainMenu.setAdapter(adapter);

        List<MainMenuItem> menuList = new ArrayList<>();
        menuList.add(new MainMenuItem("연재"));
        menuList.add(new MainMenuItem("TOP100"));
        menuList.add(new MainMenuItem("신작"));
        menuList.add(new MainMenuItem("완결"));
        menuList.add(new MainMenuItem("추천무료"));
        menuList.add(new MainMenuItem("전연령"));
        menuList.add(new MainMenuItem("탑툰쇼츠"));
        menuList.add(new MainMenuItem("이벤트"));

        adapter.submitList(menuList); // 데이터 설정

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