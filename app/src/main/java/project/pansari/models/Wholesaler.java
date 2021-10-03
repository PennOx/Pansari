package project.pansari.models;

public class Wholesaler extends User {

    private String wid;
    private boolean status;

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
