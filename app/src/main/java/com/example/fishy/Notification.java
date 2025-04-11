package com.example.fishy;

public class Notification {
    private int id;
    private String notifContent;
    private String notifTime;
    private int avtID;

    public Notification(int id,String notifContent, String notifTime, int avtID) {
        this.id = id;
        this.notifContent = notifContent;
        this.notifTime = notifTime;
        this.avtID = avtID;
    }

    public int getId() {
        return id;
    }

    public String getNotifContent() {
        return notifContent;
    }

    public String getNotifTime() {
        return notifTime;
    }

    public int getAvtID() {
        return avtID;
    }
}

