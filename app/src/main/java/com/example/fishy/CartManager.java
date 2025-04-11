package com.example.fishy;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<Product> cartList;

    private CartManager() {
        cartList = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    // Thêm sản phẩm vào giỏ hàng (nếu đã có thì tăng số lượng)
    public void addToCart(Product product) {
        boolean exists = false;
        for (Product p : cartList) {
            if (p.getProductName().equals(product.getProductName())) {
                p.setQuantity(p.getQuantity() + 1);
                exists = true;
                break;
            }
        }
        if (!exists) {
            cartList.add(product);
        }
    }

    // Lấy danh sách sản phẩm trong giỏ hàng
    public List<Product> getCart() {
        if (cartList == null) {
            cartList = new ArrayList<>();
        }
        return cartList;
    }

    // **Xóa toàn bộ sản phẩm khỏi giỏ hàng**
    public void clearCart() {
        cartList.clear();
    }
}

