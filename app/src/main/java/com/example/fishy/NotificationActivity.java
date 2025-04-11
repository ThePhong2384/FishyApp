package com.example.fishy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotification;
    private TextView tvNoNotifications;
    private NotificationAdapter adapter;
    private List<Notification> notificationList;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerViewNotification = findViewById(R.id.recyclerViewNotification);
        tvNoNotifications = findViewById(R.id.tvNoNotifications);

        db = new DatabaseHelper(this);
        recyclerViewNotification.setLayoutManager(new LinearLayoutManager(this));
        //hiển thị danh sách theo vertical
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotifications();
        // được gọi khi quay lại activity
    }

    private void loadNotifications() {
        notificationList = db.getAllNotifications();
        adapter = new NotificationAdapter(this, notificationList);
        recyclerViewNotification.setAdapter(adapter);

        // Cập nhật giao diện
        if (notificationList.isEmpty()) {
            tvNoNotifications.setVisibility(View.VISIBLE);
        } else {
            tvNoNotifications.setVisibility(View.GONE);
        }
    }

    public void openHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void openCartActivity(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void openPostFishActivity(View view) {
        Intent intent = new Intent(this, PostFishActivity.class);
        startActivity(intent);
    }

    public void openMessagesActivity(View view) {
        Intent intent = new Intent(this, MessagesActivity.class);
        startActivity(intent);
    }
}