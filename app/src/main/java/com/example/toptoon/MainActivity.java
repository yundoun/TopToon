package com.example.toptoon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.RvMainMenu);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4)); // 2열 그리드 설정

        MainMenuAdapter adapter = new MainMenuAdapter();
        recyclerView.setAdapter(adapter);

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

    }
}