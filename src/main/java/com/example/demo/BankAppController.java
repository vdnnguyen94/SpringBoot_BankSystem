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

import java.util.List;

@Controller
public class BankAppController {
	
	@Autowired
    private CustomerRepository customerRepository;
	
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
	    	System.out.println(loggedInCustomer);
	    	System.out.println("-----------Login Success Success---------Line 72");
	        return "customer";
	    } else {
	    	message="Invalid Credentials";
	    	m.addAttribute("errorMessage",message);
	        return "login";
	    }
		
	
	}
	
}
