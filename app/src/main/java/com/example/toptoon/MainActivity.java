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
        displayHomeFragment();
        clickMainLogo();
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // HomeFragment를 찾거나 없으면 새로 생성
        HomeFragment homeFragment = (HomeFragment) fragmentManager.findFragmentByTag("HOME_FRAGMENT");
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            fragmentTransaction.add(R.id.fragmentContainer, homeFragment, "HOME_FRAGMENT");
        } else {
            // 이미 HomeFragment가 추가된 경우에는 replace하지 않고 그대로 두거나 필요에 따라 refresh할 수 있음
            fragmentTransaction.replace(R.id.fragmentContainer, homeFragment, "HOME_FRAGMENT");
        }

        // 백스택에서 현재 프래그먼트 제거 (HomeFragment가 기본 화면이므로 백스택 필요 없음)
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fragmentTransaction.commit();
    }


    public void clickMainLogo() {
        binding.ivLogo.setOnClickListener(v -> {
            // 현재 활성화된 프래그먼트가 HomeFragment가 아니라면 교체
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (!(currentFragment instanceof HomeFragment)) {
                displayHomeFragment();
                binding.ivHeaderAd.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onMainMenuSelected(String menu) {
        // 여기서 메뉴 항목 선택 처리
        // 예: 프래그먼트 전환
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (menu.equals("연재")) {
            transaction.replace(R.id.fragmentContainer, new SerialFragment());
            transaction.addToBackStack(null);
            binding.ivHeaderAd.setVisibility(View.GONE);
        } else if (menu.equals("TOP100")) {
            transaction.replace(R.id.fragmentContainer, new Top100Fragment());
            transaction.addToBackStack(null);
            binding.ivHeaderAd.setVisibility(View.GONE);
        } else if (menu.equals("신작")) {
            transaction.replace(R.id.fragmentContainer, new NewProductFragment());
            transaction.addToBackStack(null);
            binding.ivHeaderAd.setVisibility(View.GONE);
        } else if (menu.equals("완결")) {
            transaction.replace(R.id.fragmentContainer, new CompleteFragment());
            transaction.addToBackStack(null);
            binding.ivHeaderAd.setVisibility(View.GONE);
        } else if (menu.equals("추천무료")) {
            transaction.replace(R.id.fragmentContainer, new FreeRecommendFragment());
            transaction.addToBackStack(null);
            binding.ivHeaderAd.setVisibility(View.GONE);
        } else if (menu.equals("전연령")) {
            transaction.replace(R.id.fragmentContainer, new AllAgeFragment());
            transaction.addToBackStack(null);
            binding.ivHeaderAd.setVisibility(View.GONE);
        } else if (menu.equals("탑툰쇼츠")) {
            transaction.replace(R.id.fragmentContainer, new ShortsFragment());
            transaction.addToBackStack(null);
            binding.ivHeaderAd.setVisibility(View.GONE);
        } else if (menu.equals("이벤트")) {
            transaction.replace(R.id.fragmentContainer, new EventFragment());
            transaction.addToBackStack(null);
            binding.ivHeaderAd.setVisibility(View.GONE);

        }
        // 다른 메뉴 항목에 대한 추가 체크 및 해당 프래그먼트로 교체
        transaction.commit();
    }
}