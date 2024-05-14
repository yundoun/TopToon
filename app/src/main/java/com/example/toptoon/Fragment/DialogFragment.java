package com.example.toptoon.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.toptoon.R;

public class DialogFragment extends androidx.fragment.app.DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a new dialog using the AlertDialog builder.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // Request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                // 크기를 디스플레이 크기의 일정 비율로 설정
                DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int width = (int) (metrics.widthPixels * 0.9); // 화면 너비의 90%
                int height = (int) (metrics.widthPixels * 0.9);
                window.setLayout(width, height);
                window.setGravity(Gravity.CENTER); // 중앙 정렬
            }
        }
    }


}
