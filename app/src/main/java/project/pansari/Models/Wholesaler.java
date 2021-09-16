package project.pansari.Models;

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

//    public void clone(Wholesaler w) {
//
//        setName(w.getName());
//        setShopName(w.getShopName());
//        setWid(w.getWid());
//        setPinCode(w.getPinCode());
//        setContact(w.getContact());
//        setAddress(w.getAddress());
//        setStatus(w.isStatus());
//        setDeviceId(w.getDeviceId());
//        setEmail(w.getEmail());
//        setImage(w.getImage());
//    }
}
