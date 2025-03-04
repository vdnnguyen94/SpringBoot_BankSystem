package com.example.demo;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Date;
//import java.util.Date;

//Customer entity class - Model class
@Entity
@Table(name="Account")
public class Account {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountNumber")
    private int  accountNumber;

    @Column(name = "balance", nullable = false)
    private double balance;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;
    public Account() {}

    public Account(int  accountNumber, double balance, Customer customer) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customer = customer;
    }

    public int  getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int  accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    @Override
    public String toString() {
        return "Account Number=" + accountNumber + 
               ", Balance= $" + String.format("%.2f", balance) + "";
    }
    
    public String printAccountDetails() {
        return "Account Number=" + accountNumber + 
               ", Balance= $" + String.format("%.2f", balance) + 
               ", customer=" + (customer != null ? customer.getCustomerName() : "No Customer") + "}";
    }
    
    
}
