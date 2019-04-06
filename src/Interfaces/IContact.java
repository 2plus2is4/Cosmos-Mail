package Interfaces;

public class IContact {
    private String name;
    private String password;
    private String email;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public IContact() {
    }

    public IContact(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
}

