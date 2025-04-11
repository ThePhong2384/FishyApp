package com.example.fishy;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 0;

    private final List<Message> messageList;
    private final int myAvatarResId;
    private final boolean isChatDetail;

    public MessageAdapter(List<Message> messageList, int myAvatarResId, boolean isChatDetail) {
        this.messageList = messageList;
        this.myAvatarResId = myAvatarResId;
        this.isChatDetail = isChatDetail;
    }

    @Override
    public int getItemViewType(int position) {
        return messageList.get(position).isSentByMe() ? VIEW_TYPE_SENT : VIEW_TYPE_RECEIVED;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);

        if (holder.getItemViewType() == VIEW_TYPE_SENT) {
            SentMessageViewHolder sentHolder = (SentMessageViewHolder) holder;
            sentHolder.tvContent.setText(message.getContent());
            sentHolder.tvTime.setText(message.getTime());
            sentHolder.imgAvatar.setImageResource(myAvatarResId); // Gán avatar cho tin nhắn gửi
        } else {
            ReceivedMessageViewHolder receivedHolder = (ReceivedMessageViewHolder) holder;
            receivedHolder.tvContent.setText(message.getContent());
            receivedHolder.tvTime.setText(message.getTime());
            receivedHolder.imgAvatar.setImageResource(message.getAvatar());

            if (!isChatDetail) {
                receivedHolder.tvUsername.setVisibility(View.VISIBLE);
                receivedHolder.tvUsername.setText(message.getUsername());
            } else {
                receivedHolder.tvUsername.setVisibility(View.GONE);
            }
        }

        if (!isChatDetail) {
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ChatDetailActivity.class);
                intent.putExtra("username", message.getUsername());
                intent.putExtra("avatar", message.getAvatar());
                v.getContext().startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent, tvTime;
        ImageView imgAvatar; // Thêm ImageView cho avatar

        public SentMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTime = itemView.findViewById(R.id.tvTime);
            imgAvatar = itemView.findViewById(R.id.imgAvatar); // Ánh xạ ImageView
        }
    }

    public static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvUsername, tvContent, tvTime;

        public ReceivedMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}