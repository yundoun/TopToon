package com.example.toptoon.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.toptoon.DataModel.MainListItem;
import com.example.toptoon.R;
import com.example.toptoon.Ui.MainListRvAdapter;
import com.example.toptoon.databinding.FragmentNewProductBinding;

import java.util.ArrayList;
import java.util.List;

public class NewProductFragment extends Fragment {

    FragmentNewProductBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewProductBinding.inflate(inflater, container, false);
        initializeComponents();
        return binding.getRoot();
    }

    public void initializeComponents() {
        binding.rvComplete.setLayoutManager(new GridLayoutManager(getContext(), 3));
        MainListRvAdapter adapter = new MainListRvAdapter();
        binding.rvComplete.setAdapter(adapter);


    }

}
