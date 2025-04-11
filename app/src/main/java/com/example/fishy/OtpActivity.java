package com.example.fishy;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class OtpActivity extends AppCompatActivity {

    EditText otp1, otp2, otp3, otp4, otp5, otp6;
    Button btnVerifyOTP;
    TextView tvResendCode, tvTimer;
    CheckBox chkTerms;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);
        tvResendCode = findViewById(R.id.tvResendCode);
        tvTimer = findViewById(R.id.tvTimer);
        chkTerms = findViewById(R.id.chkTerms);

        phoneNumber = getIntent().getStringExtra("phone");

        // Bắt đầu đếm ngược
        new CountDownTimer(154000, 1000) {
            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                tvTimer.setText(String.format("%02d:%02d", minutes, seconds));
            }
            public void onFinish() {
                tvTimer.setText("00:00");
            }
        }.start();

        btnVerifyOTP.setOnClickListener(v -> {
            if (!chkTerms.isChecked()) {
                Toast.makeText(OtpActivity.this, "Bạn phải đồng ý với điều khoản!", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(OtpActivity.this, "Xác nhận thành công!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(OtpActivity.this, HomeActivity.class));
            finish();
        });

        tvResendCode.setOnClickListener(v ->
                Toast.makeText(OtpActivity.this, "Mã OTP đã được gửi lại!", Toast.LENGTH_SHORT).show()
        );
    }
}
