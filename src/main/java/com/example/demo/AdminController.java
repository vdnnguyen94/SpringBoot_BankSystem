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
@RequestMapping("/admin")
public class AdminController {

	@Autowired
    private CustomerRepository customerRepository;

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountTypeRepository accountTypeRepository;
	

    @GetMapping
    public String adminDashboard(Model model) {
        return "admin";
    }

    @GetMapping("/lowBalance")
    public String getAccountsBelowMinBalance(RedirectAttributes redirectAttributes) {
        List<Account> lowBalanceAccs = accountRepository.findLowBalanceAcc();
        redirectAttributes.addFlashAttribute("contentAccounts", lowBalanceAccs);
        return "redirect:/admin";
    }

    @GetMapping("/premiumSavings")
    public String getHighSavingsCustomers(RedirectAttributes redirectAttributes) {
        List<Customer> customers = accountRepository.findCustomersPremSaving(1000);
        redirectAttributes.addFlashAttribute("contentCustomers", customers);
        return "redirect:/admin";
    }

    @GetMapping("/odCheque")
    public String getOverdraftChequingCustomers(RedirectAttributes redirectAttributes) {
        List<Customer> customers = accountRepository.findCustomersHasOD();
        redirectAttributes.addFlashAttribute("contentCustomers", customers);
        return "redirect:/admin";
    }
}
