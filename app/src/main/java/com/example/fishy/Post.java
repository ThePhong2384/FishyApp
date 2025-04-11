package com.example.fishy;

public class Post {
    private final int postId;
    private final String username;
    private final String time;
    private final String content;
    private final String imageURL;
    private final String avatarURL;
    private final int likeCount;
    private final int shareCount;
    private final String price;
    private final String breed;

    // Cập nhật constructor
    public Post(int postId, String username, String time, String content, String imageURL, String avatarURL, int likeCount, int shareCount, String price,String breed) {
        this.postId = postId;
        this.username = username;
        this.time = time;
        this.content = content;
        this.imageURL = imageURL;
        this.avatarURL = avatarURL;
        this.likeCount = likeCount;
        this.shareCount = shareCount;
        this.price = price;
        this.breed = breed;
    }


    public int getPostId() {
        return postId;
    }
    public String getUsername() { return username; }
    public String getTime() { return time; }
    public String getContent() { return content; }
    public String getImageURL() { return imageURL; }
    public String getAvatarURL() { return avatarURL; }
    public int getLikeCount() { return likeCount; }
    public int getShareCount() { return shareCount; }
    public String getPrice() { return price; }
    public String getBreed() {return breed;}
}