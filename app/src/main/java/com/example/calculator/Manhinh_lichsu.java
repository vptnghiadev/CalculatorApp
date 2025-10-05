package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Manhinh_lichsu extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manhinh_lichsu);

        ListView listView = findViewById(R.id.history_list);

        ArrayList<String> historyList = getIntent().getStringArrayListExtra("historyList");
        if (historyList == null) historyList = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, historyList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.cam)); // 🌈 đổi màu chữ
                text.setTextSize(18); // có thể tăng kích thước
                return view;
            }
        };
        listView.setAdapter(adapter);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(Manhinh_lichsu.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
