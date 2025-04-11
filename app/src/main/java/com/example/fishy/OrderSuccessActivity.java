package com.example.fishy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class OrderSuccessActivity extends AppCompatActivity {
    private Button btnBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        btnBackToHome = findViewById(R.id.btnBackToHome);

        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(OrderSuccessActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}

