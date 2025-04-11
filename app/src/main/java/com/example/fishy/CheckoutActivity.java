package com.example.fishy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CheckoutActivity extends AppCompatActivity {
    private EditText etFullName, etPhone, etAddress;
    private RadioGroup radioPaymentMethod;
    private Button btnConfirmOrder, btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ImageView btnBack = findViewById(R.id.btnBack);

        etFullName = findViewById(R.id.etFullName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        radioPaymentMethod = findViewById(R.id.radioPaymentMethod);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);

        btnBack.setOnClickListener(v -> finish());

        btnConfirmOrder.setOnClickListener(v -> {
            String fullName = etFullName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            int selectedPaymentId = radioPaymentMethod.getCheckedRadioButtonId();

            Intent intent = null;
            if (fullName.isEmpty() || phone.isEmpty() || address.isEmpty() || selectedPaymentId == -1) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }
                intent = new Intent(CheckoutActivity.this, OrderSuccessActivity.class);
                startActivity(intent);
                finish();

            RadioButton selectedPayment = findViewById(selectedPaymentId);
            String paymentMethod = selectedPayment.getText().toString();
            CartManager.getInstance().clearCart();
        });

    }
}
