package com.safetynet.alerts.web.model;

public class person {
    private int id;
    private String firstName;
    private String LastName;
    private String address;
    private String city;
    private String zip;
    private String phone;

    public person(){

    }

    public person(int id, String firstName, String lastName, String address, String city, String zip, String phone,
            String email) {
        this.id = id;
        this.firstName = firstName;
        LastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "person [id=" + id + ", firstName=" + firstName + ", LastName=" + LastName + ", address=" + address
                + ", city=" + city + ", zip=" + zip + ", phone=" + phone + ", email=" + email + "]";
    }
    

}
