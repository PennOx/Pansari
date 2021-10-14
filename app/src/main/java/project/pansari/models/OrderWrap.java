package project.pansari.models;

public class OrderWrap extends Order {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
