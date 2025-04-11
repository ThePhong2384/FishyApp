package com.example.fishy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Product> cartList;
    private Runnable updateTotalPrice;

    public CartAdapter(List<Product> cartList, Runnable updateTotalPrice) {
        this.cartList = cartList;
        this.updateTotalPrice = updateTotalPrice;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartList.get(position);

        holder.tvProductName.setText(product.getProductName());
        holder.tvSeller.setText("Người bán: " + product.getSellerName());
        holder.tvPrice.setText(product.getPrice());
        holder.tvQuantity.setText(String.valueOf(product.getQuantity()));

        // Load ảnh từ drawable
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(
                product.getImageRes().replace("@drawable/", ""),
                "drawable",
                holder.itemView.getContext().getPackageName()
        );
        if (imageResId != 0) {
            holder.imgProduct.setImageResource(imageResId);
        }

        // Xử lý tăng số lượng
        holder.btnIncrease.setOnClickListener(v -> {
            product.setQuantity(product.getQuantity() + 1);
            holder.tvQuantity.setText(String.valueOf(product.getQuantity()));
            updateTotalPrice.run();
        });

        // Xử lý giảm số lượng
        holder.btnDecrease.setOnClickListener(v -> {
            if (product.getQuantity() > 1) {
                product.setQuantity(product.getQuantity() - 1);
                holder.tvQuantity.setText(String.valueOf(product.getQuantity()));
                updateTotalPrice.run();
            }
        });

        // Xóa sản phẩm khỏi giỏ hàng
        holder.btnRemove.setOnClickListener(v -> {
            cartList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartList.size());
            updateTotalPrice.run();
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, btnRemove;
        TextView tvProductName, tvSeller, tvPrice, tvQuantity;
        Button btnIncrease, btnDecrease;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvSeller = itemView.findViewById(R.id.tvSeller);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
