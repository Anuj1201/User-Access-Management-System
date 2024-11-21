package dao;


public class Request {
    private int id;
    private String userEmail;
    private String softwareName;
    private String reason;
    private String status;

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getSoftwareName() {
        return softwareName;
    }
    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
