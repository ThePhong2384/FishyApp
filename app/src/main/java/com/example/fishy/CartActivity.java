package com.example.fishy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View; // Sửa lỗi "Cannot resolve symbol 'View'"
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerViewCart;
    private TextView tvTotalPrice, tvEmptyCart;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerViewCart = findViewById(R.id.rvCartItems);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvEmptyCart = findViewById(R.id.tvEmptyCart);
        Button btnCheckout = findViewById(R.id.btnCheckout);
        ImageView btnBack = findViewById(R.id.btnBack);

        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));

        List<Product> cartList = CartManager.getInstance().getCart();
        cartAdapter = new CartAdapter(cartList, this::updateTotalPrice);
        recyclerViewCart.setAdapter(cartAdapter);

        updateTotalPrice();

        btnBack.setOnClickListener(v -> finish());

        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });
    }

    // Cập nhật tổng giá tiền
    private void updateTotalPrice() {
        int total = 0;
        for (Product product : CartManager.getInstance().getCart()) {
            try {
                String priceStr = product.getPrice().replaceAll("[^0-9]", ""); // Chỉ giữ số
                int price = Integer.parseInt(priceStr);
                total += price * product.getQuantity();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        tvTotalPrice.setText("Tổng: " + total + "Đ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Product> cartItems = CartManager.getInstance().getCart();
        if (cartItems.isEmpty()) {
            recyclerViewCart.setVisibility(View.GONE);
            tvEmptyCart.setVisibility(View.VISIBLE);
        } else {
            recyclerViewCart.setVisibility(View.VISIBLE);
            tvEmptyCart.setVisibility(View.GONE);
            cartAdapter.notifyDataSetChanged();
        }
    }
}

