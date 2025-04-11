package com.example.fishy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final Context context;
    private final List<Post> postList;
    private DatabaseHelper db;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
        this.db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    // tạo view cho một bài đăng
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        // Gán dữ liệu
        holder.usernameTextView.setText(post.getUsername());
        holder.timeTextView.setText(post.getTime());
        holder.contentTextView.setText(post.getContent());
        holder.breedTextView.setText("Giống cá: " + post.getBreed()); // Hiển thị giống cá
        holder.likeCountTextView.setText(String.valueOf(post.getLikeCount()));
        holder.priceButton.setText(post.getPrice());

        int avatarResId = context.getResources().getIdentifier(
                post.getAvatarURL().replace("@drawable/", ""),
                "drawable",
                context.getPackageName()
        );
        holder.avatarImageView.setImageResource(avatarResId);

        int postImageResId = context.getResources().getIdentifier(
                post.getImageURL().replace("@drawable/", ""),
                "drawable",
                context.getPackageName()
        );
        holder.postImageView.setImageResource(postImageResId);

        // Bắt sự kiện khi ấn vào bài viết
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("username", post.getUsername());
            intent.putExtra("time", post.getTime());
            intent.putExtra("content", post.getContent());
            intent.putExtra("imageRes", post.getImageURL());
            intent.putExtra("avatarRes", post.getAvatarURL());
            intent.putExtra("likeCount", post.getLikeCount());
            intent.putExtra("price", post.getPrice());
            intent.putExtra("breed", post.getBreed());
            context.startActivity(intent);
        });

        // Đảm bảo nút xóa luôn hiển thị
        holder.deleteButton.setVisibility(View.VISIBLE);

        // Sự kiện nhấn nút xóa
        holder.deleteButton.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(context)// tạo hộp thoại alertDiaLog
                    .setTitle("Xác nhận xóa")// đặt tiêu đề
                    .setMessage("Bạn có chắc chắn muốn xóa bài đăng này?")// đặt nội dụng thông báo
                    .setPositiveButton("Có", (dialog, which) -> {
                        int postId = post.getPostId();
                        if (postId != -1) {
                            db.deletePost(postId);
                        }
                        postList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, postList.size());
                        Toast.makeText(context, "Đã xóa bài đăng!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView, postImageView, deleteButton;
        TextView usernameTextView, timeTextView, contentTextView, likeCountTextView, breedTextView; // Thêm breedTextView
        Button commentButton, priceButton;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            postImageView = itemView.findViewById(R.id.postImageView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            breedTextView = itemView.findViewById(R.id.breedTextView); // Ánh xạ breedTextView
            likeCountTextView = itemView.findViewById(R.id.likeCountTextView);
            commentButton = itemView.findViewById(R.id.commentButton);
            priceButton = itemView.findViewById(R.id.priceButton);
        }
    }
}