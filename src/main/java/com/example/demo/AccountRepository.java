package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository  extends JpaRepository<Account, Integer> {

	
    // list Accounts related to Customers
    List<Account> findByCustomer(Customer customer);

    // Find an account by its account number
    Account findByAccountNumber(int accountNumber);

    // List accounts with a balance greater specific variable
    List<Account> findByBalanceGreaterThan(double amount);
    
    // list accounts by type
    List<Account> findByAccountType(AccountType accountType);
}
