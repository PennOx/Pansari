package project.pansari.models;

import java.util.Map;

public class Order {

    private String orderId;
    private String orderFrom;
    private long orderTime;
    private String orderStatus;
    private Map<String, Map<String, CartProduct>> Wholesalers;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Map<String, Map<String, CartProduct>> getWholesalers() {
        return Wholesalers;
    }

    public void setWholesalers(Map<String, Map<String, CartProduct>> wholesalers) {
        Wholesalers = wholesalers;
    }
}

