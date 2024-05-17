package com.example.toptoon;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.toptoon.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {

    private ActivityWebViewBinding binding;

    @SuppressLint({"MissingInflatedId", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // WebViewClient 설정
        binding.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true; // 웹 페이지 내에서의 모든 리다이렉션을 웹뷰에서 처리
            }
        });

        // 웹 설정 가져오기
        WebSettings webSettings = binding.webview.getSettings();

        // JavaScript를 활성화하여 웹 페이지에서 JavaScript를 사용할 수 있게 합니다.
        webSettings.setJavaScriptEnabled(true);
        // 파일 접근을 허용하여 웹 페이지에서 로컬 파일에 접근할 수 있게 합니다.
        webSettings.setAllowFileAccess(true);
        // 콘텐츠 접근을 허용하여 웹 페이지에서 콘텐츠에 접근할 수 있게 합니다.
        webSettings.setAllowContentAccess(true);
        // DOM 스토리지를 활성화하여 웹 페이지에서 LocalStorage 및 SessionStorage를 사용할 수 있게 합니다.
        webSettings.setDomStorageEnabled(true);
        // 데이터베이스 스토리지를 활성화하여 웹 페이지에서 웹 SQL 데이터베이스 API를 사용할 수 있게 합니다.
        webSettings.setDatabaseEnabled(true);
        // 캐시 모드를 설정하여 웹 페이지의 리소스를 캐시에서 불러오도록 합니다.
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 혼합된 콘텐츠 모드를 설정하여 HTTPS 페이지에서 HTTP 리소스를 불러올 수 있게 합니다.
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        // 인텐트에서 URL 가져와서 웹뷰에 로드
        String url = getIntent().getStringExtra("URL");
        binding.webview.loadUrl(url);

    }

    @Override
    public void onBackPressed() {
        if (binding.webview.canGoBack()) {
            binding.webview.goBack();  // 웹뷰 내에서 뒤로 갈 수 있으면 뒤로 감
        } else {
            super.onBackPressed();  // 그 외의 경우 기본 뒤로 가기 동작
        }
    }

}
