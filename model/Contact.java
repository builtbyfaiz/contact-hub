package model;

public class Contact {
    private String name;
    private String number;
    private String email;
    private String secondaryNum;
    private String notes;
    private String ip;

    // Constructor
    public Contact(String name, String number, String email,
                   String secondaryNum, String notes, String ip) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.secondaryNum = secondaryNum;
        this.notes = notes;
        this.ip = ip;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecondaryNum() {
        return secondaryNum;
    }

    public void setSecondaryNum(String secondaryNum) {
        this.secondaryNum = secondaryNum;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}