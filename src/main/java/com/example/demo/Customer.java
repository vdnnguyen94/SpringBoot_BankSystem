package com.example.demo;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;
//import java.util.Date;

//Customer entity class - Model class
@Entity
@Table(name="Customer")
public class Customer {
    
    @Id
    @Column(name="customerid")
    private int customerId;
    @Column(name="username", nullable=false, unique=true)
    private String username;
    @Column(name="password", nullable=false)
    private String password;
    @Column(name="customername")
    private String customerName;
    @Column(name="dob")
    private Date dob;
    @Column(name="address")
    private String address;
    @Column(name="city")
    private String city;
    @Column(name="postalcode")
    private String postalcode;
    @Column(name="emailid", unique=true)
    private String emailId;
    @Column(name="phone")
    private String phone;

    public Customer() {
    }

    public Customer(int customerId, String username, String password, String customerName, Date dob, 
                    String address, String city, String postalcode, String emailId, String phone) {
    	super();
        this.customerId = customerId;
        this.username = username;
        this.password = password;
        this.customerName = customerName;
        this.dob = dob;
        this.address = address;
        this.city = city;
        this.postalcode = postalcode;
        this.emailId = emailId;
        this.phone = phone;
    }

    // Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public String toString() {
        return "Customer{" +
               "customerId=" + customerId +
               ", username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", customerName='" + customerName + '\'' +
               ", dob='" + dob + '\'' +
               ", address='" + address + '\'' +
               ", city='" + city + '\'' +
               ", postalcode='" + postalcode + '\'' +
               ", emailId='" + emailId + '\'' +
               ", phone='" + phone + '\'' +
               '}';
    }
}