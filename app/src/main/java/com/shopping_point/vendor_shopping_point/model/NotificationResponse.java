package com.shopping_point.vendor_shopping_point.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationResponse {

    @SerializedName("notifications")
    private List<Notification> notifications;

    public List<Notification> getNotification(int seller_id) {
        return notifications;
    }

    public void setNotification(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
