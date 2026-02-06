package com.ravi.controller;

import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BankOperationController {
	
	@GetMapping("/")
	public String showHome() {
		return "welcome";
	}
	
	@GetMapping("/offers")
	public String showOffersPage() {
		return "show_offers";
	}
	
	@GetMapping("/balance")
	public String showBalancePage(Map<String, Object> map) {
		
		map.put("balance", new Random().nextInt(40000));
		
		return "show_balance";
	}
	
	
	@GetMapping("/approve")
	public String showLoanPage(Map<String, Object> map) {
		
		map.put("loan", new Random().nextInt(100000));
		
		return "show_loan";
	}
	
	@GetMapping("/denied")
	public String showDeniedPage() {
		return "access_denied";
	}
}
