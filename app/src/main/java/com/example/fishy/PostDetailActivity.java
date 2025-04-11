package com.example.fishy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {

    private ImageView imgPost, imgAvatar, btnBack, btnSendComment;
    private TextView tvUsername, tvTime, tvContent, tvLikeCount;
    private Button btnAddToCart;
    private RecyclerView rvComments;
    private EditText etComment;
    private CommentAdapter commentAdapter;
    private int authorAvatarRes;
    private DatabaseHelper dbHelper;
    private List<Comment> commentList;

    private String username, content, price, imageRes;
    private int postId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        imgPost = findViewById(R.id.imgPost);
        imgAvatar = findViewById(R.id.imgAvatar);
        btnBack = findViewById(R.id.btnBack);
        tvUsername = findViewById(R.id.tvUsername);
        tvTime = findViewById(R.id.tvTime);
        tvContent = findViewById(R.id.tvContent);
        tvLikeCount = findViewById(R.id.tvLikeCount);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        rvComments = findViewById(R.id.rvComments);
        etComment = findViewById(R.id.etComment);
        btnSendComment = findViewById(R.id.btnSendComment);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null) {
            postId = intent.getIntExtra("postId", 1);
            username = intent.getStringExtra("username");
            String time = intent.getStringExtra("time");
            content = intent.getStringExtra("content");
            imageRes = intent.getStringExtra("imageRes");
            String avatarRes = intent.getStringExtra("avatarRes");
            int likeCount = intent.getIntExtra("likeCount", 0);
            price = intent.getStringExtra("price");

            tvUsername.setText(username);
            tvTime.setText(time);
            tvContent.setText(content);
            tvLikeCount.setText(String.valueOf(likeCount));
            btnAddToCart.setText("Thêm vào giỏ hàng - " + price);

            if (imageRes != null) {
                int postImageResId = getResources().getIdentifier(
                        imageRes.replace("@drawable/", ""),
                        "drawable",
                        getPackageName()
                );
                if (postImageResId != 0) imgPost.setImageResource(postImageResId);
            }

            if (avatarRes != null) {
                authorAvatarRes = getResources().getIdentifier(
                        avatarRes.replace("@drawable/", ""),
                        "drawable",
                        getPackageName()
                );
                if (authorAvatarRes != 0) imgAvatar.setImageResource(authorAvatarRes);
            }
        }

        btnBack.setOnClickListener(v -> finish());

        rvComments.setLayoutManager(new LinearLayoutManager(this));
        commentList = loadCommentsFromDatabase(postId);
        commentAdapter = new CommentAdapter(this, commentList, authorAvatarRes, username);
        rvComments.setAdapter(commentAdapter);

        btnAddToCart.setOnClickListener(v -> {
            Product product = new Product(username, content, price, imageRes);
            CartManager.getInstance().addToCart(product);
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });

        btnSendComment.setOnClickListener(v -> sendComment());
    }

    private List<Comment> loadCommentsFromDatabase(int postId) {
        List<Comment> comments = new ArrayList<>();
        Cursor cursor = dbHelper.getCommentsForPost(postId);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("comment"));
                String avatar = cursor.getString(cursor.getColumnIndexOrThrow("avatar"));
                comments.add(new Comment(username, content, avatar));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return comments;
    }

    private void sendComment() {
        String commentText = etComment.getText().toString().trim();
        if (!commentText.isEmpty()) {
            String currentUser = "Bạn";
            String avatarRes = "@drawable/icon_avatar";

            dbHelper.insertComment(postId, currentUser, commentText, avatarRes);


            Comment newComment = new Comment(currentUser, commentText, avatarRes);
            commentList.add(newComment);
            commentAdapter.notifyItemInserted(commentList.size() - 1);
            rvComments.smoothScrollToPosition(commentList.size() - 1);

            etComment.setText("");

            Toast.makeText(this, "Đã gửi bình luận", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Vui lòng nhập nội dung bình luận", Toast.LENGTH_SHORT).show();
        }
    }
}