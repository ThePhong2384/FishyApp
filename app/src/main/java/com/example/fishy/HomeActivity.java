package com.example.fishy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import java.util.List;
import java.util.Random;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Random random;
    private ImageView imgThongbao, imgPostfish, cartIcon, mesIcon;
    private DrawerLayout drawerLayout;
    private DatabaseHelper dbHelper;
    private List<Post> postList;
    private PostAdapter postAdapter;
    private RecyclerView recyclerViewPosts;
    private TextView tvFishCareTip;
    private String[] fishCareTips = {
            "Đảm bảo thay nước định kỳ 20-30% mỗi tuần để giữ môi trường sạch cho cá.",
            "Không cho cá ăn quá nhiều, chỉ nên cho ăn 1-2 lần/ngày với lượng vừa đủ.",
            "Kiểm tra nhiệt độ nước thường xuyên, nhiệt độ lý tưởng cho cá nhiệt đới là 24-28°C.",
            "Thêm cây thủy sinh vào bể để cung cấp oxy và tạo môi trường tự nhiên cho cá.",
            "Tránh đặt bể cá dưới ánh nắng trực tiếp, vì có thể làm tăng nhiệt độ và gây tảo.",
            "Sử dụng bộ lọc nước để giữ nước sạch và giảm chất độc hại trong bể."
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Ánh xạ các thành phần giao diện
        imgThongbao = findViewById(R.id.imgThongbao);
        imgPostfish = findViewById(R.id.imgPostfish);
        cartIcon = findViewById(R.id.shopping);
        mesIcon = findViewById(R.id.btnMessage);
        drawerLayout = findViewById(R.id.drawer_layout);
        recyclerViewPosts = findViewById(R.id.recyclerViewPosts);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this));
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundColor(Color.WHITE);
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

        dbHelper = new DatabaseHelper(this);
        postList = dbHelper.getAllPosts();
        postAdapter = new PostAdapter(this, postList);
        recyclerViewPosts.setAdapter(postAdapter);

        tvFishCareTip = findViewById(R.id.tvFishCareTip);
        random = new Random();

        findViewById(R.id.menuIcon).setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        // Xử lý sự kiện nhấn các nút trên thanh điều hướng dưới
        cartIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        mesIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MessagesActivity.class);
            startActivity(intent);
        });

        imgThongbao.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, NotificationActivity.class);
            startActivity(intent);
        });

        imgPostfish.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PostFishActivity.class);
            startActivity(intent);
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
         // Làm mới danh sách bài đăng
        postList.clear();
        postList.addAll(dbHelper.getAllPosts());
        postAdapter.notifyDataSetChanged();
        refreshFishCareTip(null);
    }



    public void refreshFishCareTip(View view) {
        int randomIndex = random.nextInt(fishCareTips.length);
        String randomTip = fishCareTips[randomIndex];
        tvFishCareTip.setText(randomTip);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_fish_species) {
            // Xử lý khi chọn "Giống loài cá cảnh"
        } else if (id == R.id.nav_equipment) {
            // Xử lý khi chọn "Dụng cụ nuôi cá"
        } else if (id == R.id.nav_food) {
            // Xử lý khi chọn "Thức ăn cho cá"
        } else if (id == R.id.nav_follow) {
            // Xử lý khi chọn "Theo dõi"
        } else if (id == R.id.nav_saved_posts) {
            // Xử lý khi chọn "Bài viết đã lưu"
        } else if (id == R.id.nav_settings) {
            // Xử lý khi chọn "Cài đặt"
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}