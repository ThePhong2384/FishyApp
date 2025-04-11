package com.example.fishy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostFishActivity extends AppCompatActivity {

    private Spinner spinnerFishBreed;
    private EditText edtDescription, edtPrice;
    private Button btnPost;
    private DatabaseHelper db;
    private boolean isPosting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_fish);

        spinnerFishBreed = findViewById(R.id.spinnerFishBreed);
        edtDescription = findViewById(R.id.edtDescription);
        edtPrice = findViewById(R.id.edtPrice);
        btnPost = findViewById(R.id.btnPost);
        db = new DatabaseHelper(this);

        String[] fishBreeds = {"Cá Betta", "Cá Koi", "Cá Guppy", "Cá Neon", "Cá Dĩa"};
        //Tạo một ArrayAdapter để kết nối mảng fishBreeds với Spinner, sử dụng layout mặc định của Android
        //android.R.layout.simple_spinner_item :để hiển thị mục được chọn trong Spinner khi nó ở trạng thái đóng
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fishBreeds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// tao layout cho dsach cac muc khi spinner đưc mở
        spinnerFishBreed.setAdapter(adapter);

        btnPost.setOnClickListener(v -> {
            if (isPosting) return;

            isPosting = true;
            String description = edtDescription.getText().toString();
            String price = edtPrice.getText().toString();
            String breed = spinnerFishBreed.getSelectedItem().toString();
            String time = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
            int imageResId = R.drawable.image_fish5;
            int avt = R.drawable.icon_avatar;

            try {
                if (description.isEmpty() || price.isEmpty()) {
                    Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double priceValue;
                try {
                    priceValue = Double.parseDouble(price);
                    if (priceValue < 0) {
                        Toast.makeText(this, "Giá bán không được âm!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Giá bán không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                long postId = db.insertPost(breed, price, description, String.valueOf(imageResId),time);

                if (postId == -1) {
                    Toast.makeText(this, "Lỗi khi lưu bài đăng vào database!", Toast.LENGTH_LONG).show();
                    return;
                }

                String message = "Bạn đã đăng bài về " + breed + " với giá " + price + " VND ";
                db.insertNotification(message, time, avt);


                Toast.makeText(this, "Bài viết của bạn đã được đăng!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } finally {
                isPosting = false; // Đặt lại trạng thái trong mọi trường hợp
            }
        });
    }


    public void openHomeActivity(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }

    public void openMessagesActivity(View view) {
        startActivity(new Intent(this, MessagesActivity.class));
    }

    public void openCartActivity(View view) {
        startActivity(new Intent(this, CartActivity.class));
    }

    public void openNotificationActivity(View view) {
        startActivity(new Intent(this, NotificationActivity.class));
    }

}