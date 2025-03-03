package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BankAppController {
	
	@Autowired
    private CustomerRepository customerRepository;

	@Autowired
	private AccountRepository accountRepository;
	
	@RequestMapping("/")
	public String home()
	{
		return "index";
	}
	
	
	@PostMapping("/register")
	public ModelAndView register(@ModelAttribute(name = "registerForm") Customer newAccount) 
	{
		System.out.println("-----------TESTING---------");
		System.out.println("Customer Registration Data: " + newAccount.toString());
	
		try {
			// Attempt to save the new customer to the database
	        
			customerRepository.save(newAccount);
			System.out.println("SUCCESS");
		} catch (Exception e) {
			// Log the exception and set the error message in the model
			System.out.println("Error saving customer: " + e.getMessage());
			return new ModelAndView("show","errorMessage",e.getMessage());
		}
		    
	    return new ModelAndView("show","customers", customerRepository.findAll());
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
}
