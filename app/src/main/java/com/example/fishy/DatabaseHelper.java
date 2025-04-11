package com.example.fishy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fishy.db";
    private static final int DATABASE_VERSION = 11;

    // USERS TABLE
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    // CONVERSATIONS TABLE
    private static final String TABLE_CONVERSATIONS = "conversations";
    private static final String COLUMN_CONV_USERNAME = "username";
    private static final String COLUMN_CONV_LAST_MESSAGE = "last_message";
    private static final String COLUMN_CONV_TIME = "time";
    private static final String COLUMN_CONV_AVATAR = "avatar";

    // COMMENTS TABLE
    private static final String TABLE_COMMENTS = "comments";
    private static final String COLUMN_COMMENT_ID = "id";
    private static final String COLUMN_COMMENT_POST_ID = "post_id";
    private static final String COLUMN_COMMENT_USERNAME = "username";
    private static final String COLUMN_COMMENT_TEXT = "comment";
    private static final String COLUMN_COMMENT_AVATAR = "avatar";

    // POSTS TABLE
    public static final String TABLE_POSTS = "posts";
    public static final String COLUMN_POST_ID = "id";
    public static final String COLUMN_POST_USERNAME = "username";
    public static final String COLUMN_POST_TIME = "time";
    public static final String COLUMN_POST_CONTENT = "content";
    public static final String COLUMN_POST_IMAGE = "image_res";
    public static final String COLUMN_POST_AVATAR = "avatar_res";
    public static final String COLUMN_POST_LIKE_COUNT = "like_count";
    public static final String COLUMN_POST_COMMENT_COUNT = "comment_count";
    public static final String COLUMN_POST_PRICE = "price";
    public static final String COLUMN_POST_BREED = "breed";

    // NOTIFICATIONS TABLE
    private static final String TABLE_NOTIFICATIONS = "notifications";
    private static final String COLUMN_MESSAGE = "message";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_AVT = "avt";

    // MESSAGES TABLE
    private static final String TABLE_MESSAGES = "messages";
    private static final String COLUMN_MSG_ID = "id";
    private static final String COLUMN_MSG_SENDER = "sender";
    private static final String COLUMN_MSG_CONTENT = "content";
    private static final String COLUMN_MSG_TIME = "time";
    private static final String COLUMN_MSG_AVATAR = "avatar";
    private static final String COLUMN_MSG_IS_SENT_BY_ME = "isSentByMe";
    private static final String COLUMN_MSG_RECEIVER = "receiver";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE + " TEXT UNIQUE, " +
                COLUMN_EMAIL + " TEXT UNIQUE, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_CONVERSATIONS_TABLE = "CREATE TABLE " + TABLE_CONVERSATIONS + " (" +
                COLUMN_CONV_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_CONV_LAST_MESSAGE + " TEXT, " +
                COLUMN_CONV_TIME + " TEXT, " +
                COLUMN_CONV_AVATAR + " INTEGER)";
        db.execSQL(CREATE_CONVERSATIONS_TABLE);

        String CREATE_COMMENTS_TABLE = "CREATE TABLE " + TABLE_COMMENTS + " (" +
                COLUMN_COMMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COMMENT_POST_ID + " INTEGER, " +
                COLUMN_COMMENT_USERNAME + " TEXT, " +
                COLUMN_COMMENT_TEXT + " TEXT, " +
                COLUMN_COMMENT_AVATAR + " TEXT)";
        db.execSQL(CREATE_COMMENTS_TABLE);

        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_POSTS + " (" +
                COLUMN_POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_POST_USERNAME + " TEXT, " +
                COLUMN_POST_TIME + " TEXT, " +
                COLUMN_POST_CONTENT + " TEXT, " +
                COLUMN_POST_IMAGE + " TEXT, " +
                COLUMN_POST_AVATAR + " TEXT, " +
                COLUMN_POST_LIKE_COUNT + " INTEGER, " +
                COLUMN_POST_COMMENT_COUNT + " INTEGER, " +
                COLUMN_POST_PRICE + " TEXT, " +
                COLUMN_POST_BREED + " TEXT)";
        db.execSQL(CREATE_POSTS_TABLE);

        String CREATE_NOTIFICATION_TABLE = "CREATE TABLE " + TABLE_NOTIFICATIONS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MESSAGE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_AVT + " INTEGER)";
        db.execSQL(CREATE_NOTIFICATION_TABLE);

        String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGES + " (" +
                COLUMN_MSG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MSG_SENDER + " TEXT, " +
                COLUMN_MSG_CONTENT + " TEXT, " +
                COLUMN_MSG_TIME + " TEXT, " +
                COLUMN_MSG_AVATAR + " INTEGER, " +
                COLUMN_MSG_IS_SENT_BY_ME + " INTEGER, " +
                COLUMN_MSG_RECEIVER + " TEXT)";
        db.execSQL(CREATE_MESSAGES_TABLE);

        // Chèn dữ liệu khởi tạo
        insertDefaultConversations(db);
        insertDefaultComments(db);
        insertDefaultPosts(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONVERSATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        onCreate(db);
    }

    // ---------------Đăng nhập - Đăng ký------------------
    public boolean checkUser(String email, String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_EMAIL + "=? OR " + COLUMN_PHONE + "=?", new String[]{email, phone});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean registerUser(String name, String phone, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS +
                " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    //--------------- Tin nhắn ---------------------
    private void insertDefaultConversations(SQLiteDatabase db) {
        insertConversation(db, "James", "Oke nhé", "5 phút trước", R.drawable.ic_user_avatar);
        insertConversation(db, "Nguyễn Văn A", "Xin chào!", "1 ngày trước", R.drawable.ic_user_avatar4);
        insertConversation(db, "Trần Thị B", "Bạn có bán cá chép không?", "15 phút trước", R.drawable.ic_user_avatar5);
        insertConversation(db, "Lê Văn C", "Cảm ơn bạn nhé!", "1 tiếng trước", R.drawable.ic_user_avatar6);
        insertConversation(db, "Phạm Văn D", "Tạm biệt", "2 phút trước", R.drawable.ic_user_avatar7);
        insertConversation(db, "Hồng Thanh", "Giá đắt quá vậy?", "40 phút trước", R.drawable.ic_user_avatar2);
        insertConversation(db, "Đích Lép", "Cá này bạn nuôi bao lâu rồi?", "8 giờ trước", R.drawable.ic_user_avatar3);
        insertConversation(db, "Viruss", "Tôi chốt con này", "15 giờ trước", R.drawable.ic_user_avatar8);
        insertConversation(db, "Rách", "Tôi mua nó với giá 5 triệu", "1 phút trước", R.drawable.ic_user_avatar9);
    }

    private void insertConversation(SQLiteDatabase db, String username, String message, String time, int avatarRes) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONV_USERNAME, username);
        values.put(COLUMN_CONV_LAST_MESSAGE, message);
        values.put(COLUMN_CONV_TIME, time);
        values.put(COLUMN_CONV_AVATAR, avatarRes);
        db.insert(TABLE_CONVERSATIONS, null, values);
    }

    public List<Message> getAllConversations() {
        List<Message> conversationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONVERSATIONS, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONV_USERNAME));
                String lastMessage = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONV_LAST_MESSAGE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONV_TIME));
                int avatar = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CONV_AVATAR));
                conversationList.add(new Message(username, lastMessage, time, avatar, false));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return conversationList;
    }

    // ---------------- Chi tiết tin nhắn --------------------
    public void addMessage(String sender, String content, String time, int avatar, boolean isSentByMe, String receiver) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MSG_SENDER, sender);
        values.put(COLUMN_MSG_CONTENT, content);
        values.put(COLUMN_MSG_TIME, time);
        values.put(COLUMN_MSG_AVATAR, avatar);
        values.put(COLUMN_MSG_IS_SENT_BY_ME, isSentByMe ? 1 : 0);
        values.put(COLUMN_MSG_RECEIVER, receiver);
        db.insert(TABLE_MESSAGES, null, values);

        updateConversation(receiver, content, time);
        db.close();
    }

    private void updateConversation(String username, String lastMessage, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONV_LAST_MESSAGE, lastMessage);
        values.put(COLUMN_CONV_TIME, time);

        Cursor cursor = db.query(TABLE_CONVERSATIONS, new String[]{COLUMN_CONV_USERNAME},
                COLUMN_CONV_USERNAME + "=?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            db.update(TABLE_CONVERSATIONS, values, COLUMN_CONV_USERNAME + "=?", new String[]{username});
        } else {
            values.put(COLUMN_CONV_USERNAME, username);
            values.put(COLUMN_CONV_AVATAR, R.drawable.ic_user_avatar);
            db.insert(TABLE_CONVERSATIONS, null, values);
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }

    public List<Message> getMessagesForUser(String username) {
        List<Message> messages = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MESSAGES +
                        " WHERE " + COLUMN_MSG_RECEIVER + "=? OR " + COLUMN_MSG_SENDER + "=? ORDER BY " + COLUMN_MSG_ID + " ASC",
                new String[]{username, username});

        if (!cursor.moveToFirst()) {
            Cursor convCursor = db.query(TABLE_CONVERSATIONS, null,
                    COLUMN_CONV_USERNAME + "=?", new String[]{username}, null, null, null);
            if (convCursor != null && convCursor.moveToFirst()) {
                String lastMessage = convCursor.getString(convCursor.getColumnIndexOrThrow(COLUMN_CONV_LAST_MESSAGE));
                String time = convCursor.getString(convCursor.getColumnIndexOrThrow(COLUMN_CONV_TIME));
                int avatar = convCursor.getInt(convCursor.getColumnIndexOrThrow(COLUMN_CONV_AVATAR));

                ContentValues values = new ContentValues();
                values.put(COLUMN_MSG_SENDER, username);
                values.put(COLUMN_MSG_CONTENT, lastMessage);
                values.put(COLUMN_MSG_TIME, time);
                values.put(COLUMN_MSG_AVATAR, avatar);
                values.put(COLUMN_MSG_IS_SENT_BY_ME, 0);
                values.put(COLUMN_MSG_RECEIVER, username);
                db.insert(TABLE_MESSAGES, null, values);
            }
            if (convCursor != null) {
                convCursor.close();
            }
        }

        cursor = db.rawQuery("SELECT * FROM " + TABLE_MESSAGES +
                        " WHERE " + COLUMN_MSG_RECEIVER + "=? OR " + COLUMN_MSG_SENDER + "=? ORDER BY " + COLUMN_MSG_ID + " ASC",
                new String[]{username, username});

        if (cursor.moveToFirst()) {
            do {
                String sender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MSG_SENDER));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MSG_CONTENT));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MSG_TIME));
                int avatar = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MSG_AVATAR));
                boolean isSentByMe = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MSG_IS_SENT_BY_ME)) == 1;

                messages.add(new Message(sender, content, time, avatar, isSentByMe));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return messages;
    }

    //------------------------ Comments ------------------------
    private void insertDefaultComments(SQLiteDatabase db) {
        insertComment(db, 1, "Nguyễn Văn A", "Đẹp thế ❤️❤️❤️", "@drawable/ic_user_avatar4");
        insertComment(db, 1, "Trần Thị B", "Giảm giá đi bro?", "@drawable/ic_user_avatar5");
        insertComment(db, 1, "Lê Văn C", "Đắt vậy sao =))))", "@drawable/ic_user_avatar6");
        insertComment(db, 1, "Phạm Văn D", "Con cá này to thế?", "@drawable/ic_user_avatar7");
        insertComment(db, 1, "Hồng Thanh", "HÚ HÚ HÚ!", "@drawable/ic_user_avatar2");
        insertComment(db, 1, "Đích Lép", "Con cá này bao nhiêu kg vậy?", "@drawable/ic_user_avatar3");
        insertComment(db, 1, "Viruss", "Tôi quan tâm sản phẩm này", "@drawable/ic_user_avatar8");
        insertComment(db, 1, "Rách", "Tôi muốn mua nó với giá 5 triệu", "@drawable/ic_user_avatar9");
    }

    private void insertComment(SQLiteDatabase db, int postId, String username, String comment, String avatarRes) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMENT_POST_ID, postId);
        values.put(COLUMN_COMMENT_USERNAME, username);
        values.put(COLUMN_COMMENT_TEXT, comment);
        values.put(COLUMN_COMMENT_AVATAR, avatarRes);
        db.insert(TABLE_COMMENTS, null, values);
    }

    public void insertComment(int postId, String username, String comment, String avatarRes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMENT_POST_ID, postId);
        values.put(COLUMN_COMMENT_USERNAME, username);
        values.put(COLUMN_COMMENT_TEXT, comment);
        values.put(COLUMN_COMMENT_AVATAR, avatarRes);
        db.insert(TABLE_COMMENTS, null, values);
        db.close();
    }

    public Cursor getCommentsForPost(int postId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_COMMENTS, null, COLUMN_COMMENT_POST_ID + "=?", new String[]{String.valueOf(postId)}, null, null, null);
    }

    //------------------------ Posts ------------------------
    private void insertDefaultPosts(SQLiteDatabase db) {
        insertPost(db, "James", "2 phút trước", "Mình còn con cá màu đẹp cực đỉnh anh cho. Pass giá yêu thương nhé mọi người 😍 #ca-nuoc-man",
                "@drawable/image_fish", "@drawable/ic_user_avatar", 175, 23, "65.000Đ", "Cá Betta");
        insertPost(db, "Sarah", "2 phút trước", "Mới bắt được con này ở biển Sầm Sơn, mong là sẽ bay nhanh trong hôm nay...",
                "@drawable/image_fish1", "@drawable/ic_user_avatar1", 80, 5, "100.000Đ", "Cá Koi");
        insertPost(db, "Tom", "30 phút trước", "Mới câu được con cá rất to, cần pass nhanh giá rẻ...",
                "@drawable/image_fish3", "@drawable/ic_user_avatar2", 400, 80, "900.000Đ", "Cá Guppy");
        insertPost(db, "Thanh", "1 tiếng trước", "Nhà có nuôi 1 con cá chép , nhu cầu muốn đẩy đi nhanh do không còn giá trị sử dụng ...",
                "@drawable/image_fish2", "@drawable/ic_user_avatar3", 333, 22, "350.000Đ", "Cá Chép");
    }

    private void insertPost(SQLiteDatabase db, String username, String time, String content, String imageRes, String avatarRes,
                            int likeCount, int commentCount, String price, String breed) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_POST_USERNAME, username);
        values.put(COLUMN_POST_TIME, time);
        values.put(COLUMN_POST_CONTENT, content);
        values.put(COLUMN_POST_IMAGE, imageRes);
        values.put(COLUMN_POST_AVATAR, avatarRes);
        values.put(COLUMN_POST_LIKE_COUNT, likeCount);
        values.put(COLUMN_POST_COMMENT_COUNT, commentCount);
        values.put(COLUMN_POST_PRICE, price);
        values.put(COLUMN_POST_BREED, breed);
        db.insert(TABLE_POSTS, null, values);
    }

    public long insertPost(String breed, String price, String description, String image, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POST_USERNAME, "Thee Phong");
        values.put(COLUMN_POST_TIME, time);
        values.put(COLUMN_POST_CONTENT, description);
        values.put(COLUMN_POST_IMAGE, image);
        values.put(COLUMN_POST_AVATAR, "@drawable/icon_avatar");
        values.put(COLUMN_POST_LIKE_COUNT, 0);
        values.put(COLUMN_POST_COMMENT_COUNT, 0);
        values.put(COLUMN_POST_PRICE, price);
        values.put(COLUMN_POST_BREED, breed);
        long result = db.insert(TABLE_POSTS, null, values);
        db.close();
        return result;
    }

    public void deletePost(int postId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POSTS, COLUMN_POST_ID + "=?", new String[]{String.valueOf(postId)});
        db.close();
    }

    public List<Post> getAllPosts() {
        List<Post> postList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_POSTS + " ORDER BY " + COLUMN_POST_ID + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                int postId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_POST_ID));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POST_USERNAME));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POST_TIME));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POST_CONTENT));
                String imageRes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POST_IMAGE));
                String avatarRes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POST_AVATAR));
                int likeCount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_POST_LIKE_COUNT));
                int commentCount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_POST_COMMENT_COUNT));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POST_PRICE));
                String breed = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POST_BREED));

                postList.add(new Post(postId, username, time, content, imageRes, avatarRes, likeCount, commentCount, price, breed));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return postList;
    }

    //------------------------ Notifications ------------------------
    public void insertNotification(String message, String time, int avt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGE, message);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_AVT, avt);
        db.insert(TABLE_NOTIFICATIONS, null, values);
        db.close();
    }

    public List<Notification> getAllNotifications() {
        List<Notification> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTIFICATIONS + " ORDER BY id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String message = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MESSAGE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
                int avt = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AVT));

                list.add(new Notification(id, message, time, avt));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void deleteNotification(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTIFICATIONS, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}