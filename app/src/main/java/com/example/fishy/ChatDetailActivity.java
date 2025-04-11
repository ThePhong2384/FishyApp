package com.example.fishy;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMessages;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private EditText edtMessage;
    private String username;
    private int avatar;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        edtMessage = findViewById(R.id.edtMessage);
        ImageView btnSend = findViewById(R.id.btnSend);
        ImageView btnBack = findViewById(R.id.btnBack);

        username = getIntent().getStringExtra("username");
        avatar = getIntent().getIntExtra("avatar", R.drawable.ic_user_avatar);

        if (username == null) {
            finish();
            return;
        }

        TextView tvUsername = findViewById(R.id.tvUsername);
        tvUsername.setText(username);

        ImageView imgAvatar = findViewById(R.id.imgAvatar);
        imgAvatar.setImageResource(avatar);

        dbHelper = new DatabaseHelper(this);
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList, R.drawable.icon_avatar, true);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(messageAdapter);

        loadMessages();

        btnBack.setOnClickListener(v -> finish());
        btnSend.setOnClickListener(v -> sendMessage());
    }

    private void loadMessages() {
        messageList.clear();
        List<Message> messages = dbHelper.getMessagesForUser(username);
        if (messages != null) {
            messageList.addAll(messages);
        }
        messageAdapter.notifyDataSetChanged();
        if (!messageList.isEmpty()) {
            recyclerViewMessages.scrollToPosition(messageList.size() - 1);
        }
    }

    private void sendMessage() {
        String messageText = edtMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            dbHelper.addMessage("Bạn", messageText, time, R.drawable.icon_avatar, true, username);
            Message newMessage = new Message("Bạn", messageText, time, R.drawable.icon_avatar, true);
            messageList.add(newMessage);
            messageAdapter.notifyItemInserted(messageList.size() - 1);
            recyclerViewMessages.smoothScrollToPosition(messageList.size() - 1);
            edtMessage.setText("");
        }
    }
}