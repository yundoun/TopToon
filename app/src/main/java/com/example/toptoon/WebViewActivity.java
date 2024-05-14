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

    @SuppressLint("MissingInflatedId")
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
        webSettings.setJavaScriptEnabled(true);  // 자바스크립트 활성화

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
