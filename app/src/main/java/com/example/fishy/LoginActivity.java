package com.example.fishy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);
        ImageView btnGoogle = findViewById(R.id.btnGoogle);
        ImageView btnFacebook = findViewById(R.id.btnFacebook);

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.checkUser(email, password)) {
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });

        tvForgotPassword.setOnClickListener(v ->
                Toast.makeText(LoginActivity.this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show());

        btnGoogle.setOnClickListener(v ->
                Toast.makeText(LoginActivity.this, "Đăng nhập Google", Toast.LENGTH_SHORT).show());

        btnFacebook.setOnClickListener(v ->
                Toast.makeText(LoginActivity.this, "Đăng nhập Facebook", Toast.LENGTH_SHORT).show());
    }

    public void openRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
