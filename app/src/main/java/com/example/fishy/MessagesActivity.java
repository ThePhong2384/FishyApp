package com.example.fishy;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private RecyclerView rvMessages;
    private MessageAdapter messageAdapter;
    private DatabaseHelper dbHelper;
    private List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        rvMessages = findViewById(R.id.rvMessages);
        ImageView btnBack = findViewById(R.id.btnBack);

        rvMessages.setLayoutManager(new LinearLayoutManager(this));
        btnBack.setOnClickListener(v -> finish());

        dbHelper = new DatabaseHelper(this);
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList, R.drawable.icon_avatar, false);
        rvMessages.setAdapter(messageAdapter);

        loadConversations();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadConversations();
    }

    private void loadConversations() {
        messageList.clear();
        // trả về một list doi tuong message, đại diện cho tin nhắn cuối cùng gửi đi
        List<Message> conversations = dbHelper.getAllConversations();
        if (conversations != null) {
            messageList.addAll(conversations);
        }
        messageAdapter.notifyDataSetChanged(); // tb cho recycleview de update giao dien
    }
}