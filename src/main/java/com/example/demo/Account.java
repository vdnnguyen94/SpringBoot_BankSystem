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
    
    @Column(name = "overDraftLimit")
    private double overDraftLimit;
    
    @ManyToOne
    @JoinColumn(name = "accountTypeId", nullable = false)
    private AccountType accountType; 
    
    public Account() {}

    
    public Account(double balance, double overDraftLimit, Customer customer, AccountType accountType) {
        this.balance = balance;
        this.overDraftLimit = overDraftLimit;
        this.customer = customer;
        this.accountType = accountType;
    }

    public int  getAccountNumber() {
        return accountNumber;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public double getOverDraftLimit() {
    	return overDraftLimit;
    }

    public void setOverDraftLimit(double overDraftLimit) {
    	this.overDraftLimit = overDraftLimit;
    }
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    @Override
    public String toString() {
        return "Account Number=" + accountNumber + 
               ", Balance= $" + String.format("%.2f", balance) + "\n" + accountType;
    }
    
    public String printAccountDetails() {
        return "Account Number=" + accountNumber + 
               ", Balance= $" + String.format("%.2f", balance) + 
               ", customer=" + (customer != null ? customer.getCustomerName() : "No Customer") + "}";
    }
    
    
}
