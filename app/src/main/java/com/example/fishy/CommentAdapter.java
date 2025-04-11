package com.example.fishy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private final Context context;
    private final List<Comment> commentList;
    private final int authorAvatarRes;
    private final String authorUsername;

    public CommentAdapter(Context context, List<Comment> commentList, int authorAvatarRes, String authorUsername) {
        this.context = context;
        this.commentList = commentList;
        this.authorAvatarRes = authorAvatarRes;
        this.authorUsername = authorUsername;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);

        holder.usernameTextView.setText(comment.getUsername() != null ? comment.getUsername() : "Unknown");
        holder.commentTextView.setText(comment.getContent() != null ? comment.getContent() : "");

        // Xử lý avatar
        String avatarRes = comment.getAvatarURL();
        if (avatarRes != null && avatarRes.startsWith("@drawable/")) {
            int avatarId = context.getResources().getIdentifier(
                    avatarRes.replace("@drawable/", ""),
                    "drawable",
                    context.getPackageName()
            );
            if (avatarId != 0) {
                holder.avatarImageView.setImageResource(avatarId);
            } else {
                holder.avatarImageView.setImageResource(R.drawable.ic_user_avatar);
            }
        } else {
            holder.avatarImageView.setImageResource(R.drawable.ic_user_avatar);
        }

        // Nếu là tác giả bài viết, dùng authorAvatarRes
        if (comment.getUsername() != null && comment.getUsername().equals(authorUsername)) {
            holder.avatarImageView.setImageResource(authorAvatarRes);
        }
    }

    @Override
    public int getItemCount() {
        return commentList != null ? commentList.size() : 0;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;
        TextView usernameTextView, commentTextView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            commentTextView = itemView.findViewById(R.id.commentTextView);
        }
    }
}