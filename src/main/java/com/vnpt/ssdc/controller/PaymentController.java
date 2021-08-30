package com.vnpt.ssdc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vnpt.ssdc.dto.Packages;
import com.vnpt.ssdc.dto.Payment;
import com.vnpt.ssdc.dto.Product;
import com.vnpt.ssdc.service.PackagesService;
import com.vnpt.ssdc.service.PaymentService;

@Controller
public class PaymentController {
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private PackagesService packagesService;
	
	@GetMapping("/payment")
    public ModelAndView payment() {    	
    	ModelAndView mav = new ModelAndView("payment");	
    	Payment paymentSearch = new Payment();
    	mav.addObject("paymentSearch", paymentSearch);	
		List<Payment> payment = paymentService.listAll(paymentSearch);		
		mav.addObject("payment", payment);		
		List<Packages> packages = packagesService.listAll();
		
		mav.addObject("packages", packages);
		return mav;
    }
	
	@RequestMapping("/searchPayment")
    public ModelAndView searchPayment(@ModelAttribute("paymentSearch") Payment paymentSearch) {    	
    	ModelAndView mav = new ModelAndView("payment");	
    	
    	mav.addObject("paymentSearch", paymentSearch);	
		List<Payment> payment = paymentService.listAll(paymentSearch);		
		mav.addObject("payment", payment);		
		List<Packages> packages = packagesService.listAll();
		
		mav.addObject("packages", packages);
		return mav;
    }
}
