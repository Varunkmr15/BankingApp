package model;

public class User {
    private int id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private boolean isAdmin;
    private boolean isFrozen;

    public User() {}

    public User(int id, String username, String fullName, String email, String phone, boolean isAdmin, boolean isFrozen) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.isAdmin = isAdmin;
        this.isFrozen = isFrozen;
    }
    //getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public boolean isAdmin() { return isAdmin; }
    public boolean isFrozen() { return isFrozen; }

    //setters
    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setAdmin(boolean admin) { isAdmin = admin; }
    public void setFrozen(boolean frozen) { isFrozen = frozen; }

}
