package com.example.enduser.Model;

public class Customer {

    public String name;
    public String phone;
    public String email;
    public String password;
    public String address;

    public Customer() {
    }

    public Customer(String name, String phone, String email, String password, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
