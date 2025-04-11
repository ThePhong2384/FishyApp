package com.example.fishy;

public class Product {
    private String sellerName;
    private String productName;
    private String price;
    private String imageRes;
    private int quantity; // Thêm thuộc tính số lượng

    public Product(String sellerName, String productName, String price, String imageRes) {
        this.sellerName = sellerName;
        this.productName = productName;
        this.price = price;
        this.imageRes = imageRes;
        this.quantity = 1; // Mặc định số lượng = 1 khi thêm vào giỏ hàng
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getProductName() {
        return productName;
    }

    public String getPrice() {
        return price;
    }

    public String getImageRes() {
        return imageRes;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
