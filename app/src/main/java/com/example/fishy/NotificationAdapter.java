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

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private Context context;// đại diện cho activity cấp quyền truy cap tai nguyen : intent, toast
    private List<Notification> notificationList;

    private DatabaseHelper dbHelper;
    public NotificationAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    //Tạo view cho một mục thông báo , được gọi khi rv cần tạo viewholder mới để hiển thị một thông báo
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);// lưu trữ các view của một mục, khi mục đó được tái sử dụng,
    }

    @Override
    // để gán dữ liệu từ đối tượng Notification qua cac getter vào các view trong viewholder
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position); // lấy notif tại vị trí position
        holder.tvNotifContent.setText(notification.getNotifContent());
        holder.tvNotifTime.setText(notification.getNotifTime());
        holder.imgAvt.setImageResource(notification.getAvtID());

        holder.imgDelete.setOnClickListener(v -> {
            dbHelper.deleteNotification(notification.getId());

            notificationList.remove(position);// xoá tb tịa ví trị position
            notifyItemRemoved(position); // tbao cho recycleview mục tb tại vị trí position đã bị xoá(xoá khỏi list hiển thị)
            notifyItemRangeChanged(position, notificationList.size());// di chuyển các mục

            if (notificationList.isEmpty()) {
                ((NotificationActivity) context).findViewById(R.id.tvNoNotifications).setVisibility(View.VISIBLE);
                ((NotificationActivity) context).findViewById(R.id.recyclerViewNotification).setVisibility(View.GONE);
            }
        });


    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    //Lớp này lưu trữ các tham chiếu đến các view của item_notifications,
    //giảm số lan goi findviewbyid
    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvt,imgDelete;
        TextView tvNotifContent, tvNotifTime;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvt = itemView.findViewById(R.id.imgAvt);
            tvNotifContent = itemView.findViewById(R.id.tvNotifContent);
            tvNotifTime = itemView.findViewById(R.id.tvNotifTime);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}

//