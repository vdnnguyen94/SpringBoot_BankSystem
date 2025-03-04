package com.example.demo;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Date;
//import java.util.Date;
import java.util.List;

//Customer entity class - Model class
@Entity
@Table(name="AccountType")
public class AccountType {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountTypeId")
    private int accountTypeId;

    @Column(name = "accountTypeName", nullable = false)
    private String accountTypeName;

    @Column(name = "accountTypeDesc")
    private String accountTypeDesc;

    @Column(name = "minimumBalanceAmount")
    private double minimumBalanceAmount;

    @Column(name = "hasOverDraft")
    private boolean hasOverDraft;

    @OneToMany(mappedBy = "accountType")
    private List<Account> accounts;

    public AccountType() {}

    public AccountType(String accountTypeName, String accountTypeDesc, double minimumBalanceAmount, boolean hasOverDraft) {
        this.accountTypeName = accountTypeName;
        this.accountTypeDesc = accountTypeDesc;
        this.minimumBalanceAmount = minimumBalanceAmount;
        this.hasOverDraft = hasOverDraft;
    }

    // Getters and Setters
    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public String getAccountTypeDesc() {
        return accountTypeDesc;
    }

    public void setAccountTypeDesc(String accountTypeDesc) {
        this.accountTypeDesc = accountTypeDesc;
    }

    public double getMinimumBalanceAmount() {
        return minimumBalanceAmount;
    }

    public void setMinimumBalanceAmount(double minimumBalanceAmount) {
        this.minimumBalanceAmount = minimumBalanceAmount;
    }

    public boolean isHasOverDraft() {
        return hasOverDraft;
    }

    public void setHasOverDraft(boolean hasOverDraft) {
        this.hasOverDraft = hasOverDraft;
    }
    @Override
    public String toString() {
        return "Account Type Name: " + accountTypeName + "--"+ "Description: " + accountTypeDesc +  "--Min Balanc: $" + minimumBalanceAmount +
               ", OverDraft:" + hasOverDraft;
    }
}