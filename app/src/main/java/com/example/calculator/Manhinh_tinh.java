package com.example.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class Manhinh_tinh extends Fragment {
    private CalculatorViewModel viewModel;
    private TextView idNumber, idKetqua;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.manhinh_tinh,container,false);
        idNumber = view.findViewById(R.id.id_number);
        idKetqua = view.findViewById(R.id.id_ketqua);
        // Dùng ViewModel chung
        viewModel = new ViewModelProvider(requireActivity()).get(CalculatorViewModel.class);

        // Quan sát thay đổi
        viewModel.getCurrentText().observe(getViewLifecycleOwner(), value -> {
            idNumber.setText(value);
        });
        viewModel.getResultText().observe(getViewLifecycleOwner(), result -> {
            idKetqua.setText(result);
        });

        return view;
    }
}
