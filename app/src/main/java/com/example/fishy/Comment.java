package com.example.fishy;

public class Comment {
    private String username;
    private String content;
    private String avatarURL;

    public Comment(String username, String content, String avatarURL) {
        this.username = username;
        this.content = content;
        this.avatarURL = avatarURL;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public String getAvatarURL() {
        return avatarURL;
    }
}
