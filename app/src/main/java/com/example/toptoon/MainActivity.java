package com.example.toptoon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.toptoon.Api.NetworkManager;
import com.example.toptoon.DataModel.ApiItems;
import com.example.toptoon.DataModel.DrawerItem;
import com.example.toptoon.DataModel.MainMenuItem;
import com.example.toptoon.Fragment.AllAgeFragment;
import com.example.toptoon.Fragment.CompleteFragment;
import com.example.toptoon.Fragment.DialogFragment;
import com.example.toptoon.Fragment.EventFragment;
import com.example.toptoon.Fragment.FreeRecommendFragment;
import com.example.toptoon.Fragment.HomeFragment;
import com.example.toptoon.Fragment.NewProductFragment;
import com.example.toptoon.Fragment.SerialFragment;
import com.example.toptoon.Fragment.ShortsFragment;
import com.example.toptoon.Fragment.Top100Fragment;
import com.example.toptoon.Ui.DrawerRvAdapter;
import com.example.toptoon.Ui.MainMenuRvAdapter;
import com.example.toptoon.Ui.OnMainMenuSelectedListener;
import com.example.toptoon.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMainMenuSelectedListener {

    private ActivityMainBinding binding;
    private MainMenuRvAdapter adapter;
    private int currentSelectedMenuIndex = -1;

    private final Map<Class<? extends Fragment>, Integer> fragmentIndexMap = new HashMap<Class<? extends Fragment>, Integer>() {{
        put(HomeFragment.class, -1);
        put(SerialFragment.class, 0);
        put(Top100Fragment.class, 1);
        put(NewProductFragment.class, 2);
        put(CompleteFragment.class, 3);
        put(FreeRecommendFragment.class, 4);
        put(AllAgeFragment.class, 5);
        put(ShortsFragment.class, 6);
        put(EventFragment.class, 7);
    }};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (adapter == null) { // 어댑터가 아직 생성되지 않았다면 생성
            setupMainMenu();
        }


        binding.btnHamburger.setOnClickListener(v -> {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                binding.drawerLayout.closeDrawer(GravityCompat.END);
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.END);
            }
        });


        binding.rvDrawer.setLayoutManager(new LinearLayoutManager(this));

        DrawerRvAdapter drawerRvAdapter = new DrawerRvAdapter();
        binding.rvDrawer.setAdapter(drawerRvAdapter);

        // 문자열 배열을 가져옵니다.
        String[] menu = getResources().getStringArray(R.array.drawer);
        // 문자열 배열을 List<DrawerItem>으로 변환합니다.
        List<DrawerItem> drawerItems = new ArrayList<>();
        for (String title : menu) {
            drawerItems.add(new DrawerItem(title));
        }
        // 변환한 List<DrawerItem>을 DrawerRvAdapter에 제출합니다.
        drawerRvAdapter.submitList(drawerItems);


        setupMainMenu();
        setHeaderAd();
        displayFragment(new HomeFragment(), false);
        setupLogoClickEvent();
        setupButtonClickListeners();

        // 백스택 변경 리스너 추가
        getSupportFragmentManager().addOnBackStackChangedListener(this::onBackStackChanged);
    }

    private void setupButtonClickListeners() {
        binding.btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("URL", "https://toptoon.com/hashtag");
            startActivity(intent);
        });

        View.OnClickListener dialogClickListener = v -> {
            DialogFragment dialog = new DialogFragment();
            dialog.show(getSupportFragmentManager(), "LoginDialogFragment");
        };

        binding.btnCoin.setOnClickListener(dialogClickListener);
        binding.btnMyLibrary.setOnClickListener(dialogClickListener);
        binding.btnGiftBox.setOnClickListener(dialogClickListener);
        binding.ivHeaderAd.setOnClickListener(dialogClickListener);
        binding.adultSwitch.setOnClickListener(v->{
            DialogFragment dialog = new DialogFragment();
            dialog.show(getSupportFragmentManager(), "LoginDialogFragment");
            binding.adultSwitch.setChecked(!binding.adultSwitch.isChecked());
        });
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
        Fragment newFragment = getFragmentForMenu(menu);
        if (newFragment != null) {
            adapter.setSelectedItemPosition(fragmentIndexMap.get(newFragment.getClass()));
            displayFragment(newFragment, true);
        }
    }

    // Fragment 생성을 위한 함수
    private Fragment getFragmentForMenu(String menu) {
        switch (menu) {
            case "연재":
                return new SerialFragment();
            case "TOP100":
                return new Top100Fragment();
            case "신작":
                return new NewProductFragment();
            case "완결":
                return new CompleteFragment();
            case "추천무료":
                return new FreeRecommendFragment();
            case "전연령":
                return new AllAgeFragment();
            case "탑툰쇼츠":
                return new ShortsFragment();
            case "이벤트":
                return new EventFragment();
            default:
                return null;
        }
    }



    private void displayFragment(Fragment fragment, boolean shouldHideAd) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        binding.ivHeaderAd.setVisibility(shouldHideAd ? View.GONE : View.VISIBLE);
    }


    // 백스택이 변경될 때 현재 표시된 프래그먼트에 따라 선택된 메뉴 항목의 인덱스를 업데이트
    // => 메인 메뉴 테두리 변경
    public void onBackStackChanged() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        assert currentFragment != null;
        Integer index = fragmentIndexMap.get(currentFragment.getClass());
        if (index != null) {
            adapter.setSelectedItemPosition(index);
            binding.ivHeaderAd.setVisibility(index == -1 ? View.VISIBLE : View.GONE);
        }
    }

    public void onBackPressed() {
        // 뒤로 가기 내비게이션 처리
        if (currentSelectedMenuIndex != -1) {
            adapter.setSelectedItemPosition(-1);
        }
        super.onBackPressed();
    }


}