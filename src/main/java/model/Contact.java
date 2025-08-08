package model;

import java.time.LocalDate;

public class Contact {
    private String name;
    private String phone;
    private String email;
    private String tag;
    private LocalDate birthday;

    public Contact(String name, String phone, String email, String tag, LocalDate birthday) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tag = tag;
        this.birthday = birthday;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getTag() { return tag; }
    public LocalDate getBirthday() { return birthday; }

    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setTag(String tag) { this.tag = tag; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }

    @Override
    public String toString() {
        return "Name: " + name +
               ", Phone: " + phone +
               ", Email: " + email +
               ", Tag: " + tag +
               ", Birthday: " + (birthday != null ? birthday.toString() : "N/A");
    }

    public String toCSV() {
        return name + "," + phone + "," + email + "," + tag + "," + (birthday != null ? birthday : "");
    }
}
