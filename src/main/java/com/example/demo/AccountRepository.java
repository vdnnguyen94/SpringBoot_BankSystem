package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository  extends JpaRepository<Account, Integer> {

	
    // Find all accounts belonging to a specific customer
    List<Account> findByCustomer(Customer customer);

    // Find an account by its account number
    Account findByAccountNumber(String accountNumber);

    // Find accounts with a balance greater than a specified amount
    List<Account> findByBalanceGreaterThan(double amount);
}
