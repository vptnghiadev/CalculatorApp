package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CalculatorViewModel viewModel;
    ImageButton btnHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CalculatorViewModel.class);
        setContentView(R.layout.activity_main);
        btnHistory = findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Manhinh_lichsu.class);
                List<String> history = viewModel.getHistoryList().getValue();
                if (history != null) {
                    intent.putStringArrayListExtra("historyList", new ArrayList<>(history));
                }

                startActivity(intent);
            }
        });
    }
}