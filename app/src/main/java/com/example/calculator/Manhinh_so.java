package com.example.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class Manhinh_so extends Fragment {
    private Button[] buttons = new Button[20];

    private CalculatorViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.manhinh_so,container,false);
        viewModel = new ViewModelProvider(requireActivity()).get(CalculatorViewModel.class);
        int[] buttonIds = {
                R.id.button, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8,
                R.id.button9, R.id.button10, R.id.button11, R.id.button12,
                R.id.button13, R.id.button14, R.id.button15, R.id.button16,
                R.id.button17, R.id.button18, R.id.button19, R.id.button20
        };
        for (int i = 0; i < buttonIds.length; i++) {
            buttons[i] = view.findViewById(buttonIds[i]);
        }
        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            String value = b.getText().toString();

            switch (value) {
                case "C":
                    viewModel.clearText(); // Xóa hết biểu thức
                    break;

                case "=":
                    viewModel.calculateResult(); // Tính toán kết quả
                    break;

                case "D": // Xóa 1 ký tự
                    viewModel.deleteLastChar();
                    break;

                case "$": // Ví dụ bạn muốn thêm chức năng đặc biệt (vd: đổi sang phần trăm)
                    // Gọi 1 hàm riêng
                    //viewModel.convertCurrency();
                    break;

                default:
                    // Nút số hoặc phép tính (+ - × ÷ % …)
                    viewModel.appendText(value);
                    break;
            }

            // 👇 Thêm các hiệu ứng phụ (nếu bạn muốn)
            v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(50).withEndAction(() ->
                    v.animate().scaleX(1f).scaleY(1f).setDuration(50)
            ).start();
        };

        // Gắn sự kiện click cho từng nút
        for (Button b : buttons) {
            b.setOnClickListener(listener);
        }
        return view;
    }
}
