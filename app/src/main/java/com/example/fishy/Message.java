package com.example.fishy;

public class Message {
    private final String username;
    private final String content;
    private final String time;
    private final int avatar;
    private final boolean isSentByMe;

    public Message(String username, String content, String time, int avatar, boolean isSentByMe) {
        this.username = username;
        this.content = content;
        this.time = time;
        this.avatar = avatar;
        this.isSentByMe = isSentByMe;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public int getAvatar() {
        return avatar;
    }

    public boolean isSentByMe() {
        return isSentByMe;
    }
}