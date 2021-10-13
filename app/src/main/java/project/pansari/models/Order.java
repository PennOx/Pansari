package project.pansari.models;


import java.util.Map;

public class Order {

    private String id;
    private String from;
    private String to;
    private long timestamp;
    private String status;
    private Map<String, CartProduct> products;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, CartProduct> getProducts() {
        return products;
    }

    public void setProducts(Map<String, CartProduct> products) {
        this.products = products;
    }
}

