package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import java.util.List;

@Controller
public class BankAppController {
	
	@Autowired
    private CustomerRepository customerRepository;

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountTypeRepository accountTypeRepository;
	
	@RequestMapping("/")
	public String home()
	{
		return "index";
	}
	
	@RequestMapping("/register")
	public String registerForm()
	{
		return "registercustomer";
	}
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute(name = "registerForm") Customer newAccount,
			BindingResult bindingResult, 
            RedirectAttributes redirectAttributes)
	{
		System.out.println("-----------TESTING---------");
		System.out.println("Customer Registration Data: " + newAccount.toString());
	
	    // Check for validation errors
	    if (bindingResult.hasErrors()) {
	        // Capture error messages
	        redirectAttributes.addFlashAttribute("errorMessage", "Please fill in all required fields correctly.");
	        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerForm", bindingResult);
	        redirectAttributes.addFlashAttribute("registerForm", newAccount);
	        return "redirect:/register";  // Redirect back to registration page
	    }
		try {
			// Attempt to save the new customer to the database
	        
			customerRepository.save(newAccount);
			System.out.println("SUCCESS");
		} catch (Exception e) {
			// Log the exception and set the error message in the model
			System.out.println("Error saving customer: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMessage", "Error saving customer: " + e.getMessage());
	        return "redirect:/register";
		}
		    
	    return "redirect:/show";
	}
	
	@RequestMapping("/show")
    public String show(Model model)
    {
        model.addAttribute("customers",customerRepository.findAll());
        return "show";
    } 
	
	@RequestMapping("/login")
	public String loginGet()
	{
		return "login";
	}
	
	@PostMapping("/login")
	public String loginPost(@ModelAttribute(name="loginForm") LoginBean loginBean, Model m) {
		
		System.out.println("-----------TESTING---------Line 65");
		System.out.println(loginBean);
		String message;
	    List<Customer> customers = customerRepository.findByUserNameAndPassword(loginBean.getUsername(), loginBean.getPassword());

	    if (!customers.isEmpty()) {
	    	Customer loggedInCustomer = customers.get(0);
	    	m.addAttribute("customer", loggedInCustomer);
	    	
	        List<Account> accounts = accountRepository.findByCustomer(loggedInCustomer);
	        m.addAttribute("accounts", accounts);
	    	System.out.println(loggedInCustomer);
	    	System.out.println("-----------Login Success Success---------Line 72");
	        System.out.println("Accounts: " + accounts);

	        //return "customer"; Causing Connection LOST when refresh page
	        return "redirect:/CustomerDetails/" + loggedInCustomer.getCustomerId();
	    } else {
	    	message="Invalid Credentials";
	    	m.addAttribute("errorMessage",message);
	        return "login";
	    }
		
	}
	
	
	@GetMapping("/CustomerDetails/{customerId}")
	public String customerDetails(@PathVariable int customerId, Model model) {
	    Customer customer = customerRepository.findById(customerId)
	            .orElseThrow(() -> new RuntimeException("Customer not found"));

	    List<Account> accounts = accountRepository.findByCustomer(customer);
	    
	    model.addAttribute("customer", customer);
	    model.addAttribute("accounts", accounts);
	    return "customer"; 
	}
	
	@PostMapping("/update")
	public String updateCustomer(@RequestParam int customerId, 
	                             @RequestParam String address, 
	                             @RequestParam String phone, 
	                             @RequestParam String emailId, 
	                             RedirectAttributes redirectAttributes) {
	    Customer customer = customerRepository.findById(customerId)
	            .orElseThrow(() -> new RuntimeException("Customer not found"));

	    // Update fields
	    customer.setAddress(address);
	    customer.setPhone(phone);
	    customer.setEmailId(emailId);

	    // Save updated customer
	    customerRepository.save(customer);
	    // model not working if using redirect
		//model.addAttribute("successMessage", "Your account is successfully updated");
	    redirectAttributes.addFlashAttribute("successMessage", "Your account is successfully updated");
	    // Reload customer details
	    return "redirect:/CustomerDetails/" + customerId;
	}
	
	//Delete Account
    @GetMapping("/deleteAccount/{accountNumber}")
    public String deleteAccount(@PathVariable int accountNumber, RedirectAttributes redirectAttributes) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        int customerId = account.getCustomer().getCustomerId();
        accountRepository.delete(account);
        redirectAttributes.addFlashAttribute("successMessage", "Account deleted successfully!");
        return "redirect:/CustomerDetails/" + customerId;
    }
    
    //Update balance will query account and customer return to the update form
    @GetMapping("/UpdateAccount/{accountNumber}")
    public String updateBalanceForm(@PathVariable int accountNumber, Model model) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        
        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        Customer customer = account.getCustomer();
        
        model.addAttribute("customer", customer);
        model.addAttribute("account", account);
        
        return "updatebalance"; 
    }
    
    @PostMapping("/UpdateAccount")
    public String updateBalance(@RequestParam int accountNumber, 
                                @RequestParam double balance, 
                                RedirectAttributes redirectAttributes) {
        Account account = accountRepository.findByAccountNumber(accountNumber);

        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        
        account.setBalance(balance);
        accountRepository.save(account);
        
        Customer customer = account.getCustomer(); // Retrieve the customer
        redirectAttributes.addFlashAttribute("successMessage", "Successfully updated balance");
        return "redirect:/CustomerDetails/" + customer.getCustomerId();
    }
    
    @GetMapping("/CreateAccount/{customerId}")
    public String createAccountForm(@PathVariable int customerId, Model model) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        List<AccountType> accountTypes = accountTypeRepository.findAll(); // Fetch account types

        model.addAttribute("customer", customer);
        model.addAttribute("accountTypes", accountTypes);
        model.addAttribute("account", new Account()); 

        return "createaccount"; 
    }
    
    @PostMapping("/CreateAccount")
    public String createAccount(@RequestParam int customerId, 
                                @RequestParam int accountTypeId, 
                                @RequestParam double balance, 
                                @RequestParam(required = false, defaultValue = "0") double overDraftLimit, 
                                RedirectAttributes redirectAttributes) {
        
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        AccountType accountType = accountTypeRepository.findById(accountTypeId)
                .orElseThrow(() -> new RuntimeException("Account Type not found"));
        
        if (!accountType.isHasOverDraft()) {
            overDraftLimit = 0; // Set overdraft limit to 0 if the account type does not allow overdraft
        }

        Account newAccount = new Account(balance, overDraftLimit, customer, accountType);


        accountRepository.save(newAccount);

        redirectAttributes.addFlashAttribute("successMessage", "Account created successfully!");

        return "redirect:/CustomerDetails/" + customerId;
    }
}
