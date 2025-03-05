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
    
    // list accounts have insufficient fund 
    @Query("SELECT acc FROM Account acc WHERE acc.balance < acc.accountType.minimumBalanceAmount")
    List<Account> findLowBalanceAcc();

    // list  customers have saving higher than an amount
    @Query("SELECT acc.customer FROM Account acc WHERE acc.accountType.accountTypeName = 'Savings' AND acc.balance > :amount")
    List<Customer> findCustomersPremSaving(@Param("amount") double amount);

    // list customers have overdraft chequing
    @Query("SELECT DISTINCT acc.customer FROM Account acc WHERE acc.accountType.accountTypeName = 'Checking' AND acc.accountType.hasOverDraft = true")
    List<Customer> findCustomersHasOD();
}


//public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
//	
//	// Custom Query by method name
//	List<Employee> findBySalaryGreaterThan(double salary);
//	
//	// Custom Query by JPA JPQL 
//	// Using the Java object relations to easily join database
//	
//	//Example with DTO - Using dto style object to transfer data between application layers
//	@Query("select new com.example.jpa.EmployeeDepartmentData(e.empId, e.empName, e.jobTitle, e.salary, d.deptno, d.location, d.deptname) FROM Employee AS e JOIN e.deptno AS d WHERE d.deptno = ?1")
//	List<EmployeeDepartmentData> findEmployeesByDeptnoDTO(Integer id);
//	
//	@Query("SELECT d.employees FROM Department d WHERE d.deptno = ?1") //JPQL named indexed query
//    List<Employee> findEmployeesByDeptno(Integer id);
//	
//	@Query("SELECT DISTINCT e.deptno FROM Employee e WHERE e.salary > :salary") //JPQL named param query
//    List<Department> findDepartmentsWithHighSalaries(@Param("salary") double salary);
//	
//	@Query("SELECT e FROM Employee e JOIN FETCH e.deptno")
//	List<Employee> findAllWithDepartment();
//}