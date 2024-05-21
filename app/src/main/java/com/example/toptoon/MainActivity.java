package com.example.toptoon;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import com.example.toptoon.Fragment.CompleteFragment;
import com.example.toptoon.Fragment.DialogFragment;
import com.example.toptoon.Fragment.HomeFragment;
import com.example.toptoon.Fragment.NewProductFragment;
import com.example.toptoon.Fragment.SerialFragment;
import com.example.toptoon.Fragment.Top100Fragment;
import com.example.toptoon.Ui.DrawerRvAdapter;
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


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupMainMenu();
        setHeaderAd();

        setupDrawerNavigation();

        setupLogoClickEvent();
        setupButtonClickListeners();

        // HomeFragment를 기본으로 설정
        if (savedInstanceState == null) {
            displayFragment(new HomeFragment(), false);
        }

        // 백스택 변경 리스너 추가
        getSupportFragmentManager().addOnBackStackChangedListener(this::onBackStackChanged);

//        if (adapter == null) { // 어댑터가 아직 생성되지 않았다면 생성
//            setupMainMenu();
//        }

    }

    private void setupDrawerNavigation() {

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

        String[] menu = getResources().getStringArray(R.array.drawer);
        // 문자열 배열을 List<DrawerItem>으로 변환
        List<DrawerItem> drawerItems = new ArrayList<>();
        for (String title : menu) {
            drawerItems.add(new DrawerItem(title));
        }
        drawerRvAdapter.submitList(drawerItems);

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
        binding.adultSwitch.setOnClickListener(v -> {
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
        int selectedIndex = getMenuIndex(menu);
        if (selectedIndex >= 4 && selectedIndex <= 7) {
            String url = getWebViewUrlForMenu(selectedIndex);
            if (url != null) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("URL", url);
                startActivity(intent);
                return;
            }
        } else {
            Fragment newFragment = getFragmentForMenu(menu);
            if (newFragment != null) {
                displayFragment(newFragment, true);
            }
        }
    }


    // 메뉴 인덱스에 따라 URL을 반환하는 메서드
    private String getWebViewUrlForMenu(int index) {
        switch (index) {
            case 4:
                return "https://toptoon.com/event/freetoon/daily#event1";
            case 5:
                return "https://toptoon.com/cartoon#cartoon1";
            case 6:
                return "https://toptoon.com/shorts";
            case 7:
                return "https://toptoon.com/event";
            default:
                return null;
        }
    }

    // 메뉴 이름에 따라 인덱스를 반환하는 메서드
    private int getMenuIndex(String menu) {
        switch (menu) {
            case "연재":
                return 0;
            case "TOP100":
                return 1;
            case "신작":
                return 2;
            case "완결":
                return 3;
            case "추천무료":
                return 4;
            case "전연령":
                return 5;
            case "탑툰쇼츠":
                return 6;
            case "이벤트":
                return 7;
            default:
                return -1;
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
            default:
                return null;
        }
    }


    private void displayFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();

        binding.ivHeaderAd.setVisibility(fragment instanceof HomeFragment ? View.VISIBLE : View.GONE);
    }


    // 백스택이 변경될 때 현재 표시된 프래그먼트에 따라 선택된 메뉴 항목의 인덱스를 업데이트
    // => 메인 메뉴 테두리 변경
    public void onBackStackChanged() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (currentFragment != null) {
            int index = getMenuIndexForFragment(currentFragment);
            adapter.setSelectedItemPosition(index);
            binding.ivHeaderAd.setVisibility(currentFragment instanceof HomeFragment ? View.VISIBLE : View.GONE);
        }
    }


    // 현재 프래그먼트에 해당하는 메뉴 인덱스를 반환하는 메서드
    private int getMenuIndexForFragment(Fragment fragment) {
        if (fragment instanceof SerialFragment) {
            return 0;
        } else if (fragment instanceof Top100Fragment) {
            return 1;
        } else if (fragment instanceof NewProductFragment) {
            return 2;
        } else if (fragment instanceof CompleteFragment) {
            return 3;
        } else if (fragment instanceof HomeFragment) {
            return -1;
        } else {
            return -1;
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            binding.drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (currentFragment instanceof HomeFragment) {
                showExitDialog();
            } else {
                super.onBackPressed();
            }
        }
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setMessage("종료하시겠습니까?")
                .setPositiveButton("예", (dialog, which) -> finish())
                .setNegativeButton("아니오", null)
                .show();
    }


}