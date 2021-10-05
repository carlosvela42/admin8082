package com.vnpt.ssdc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@RequestMapping("/editPayment")
	public ModelAndView editPayment(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("edit_payment");
		Payment paymentSearch = new Payment();
		paymentSearch.setId(Long.parseLong(request.getParameter("id")));
		List<Payment> payment = paymentService.listAll(paymentSearch);
		if(payment.size() > 0) {
			mav.addObject("payment", payment.get(0));
		}
		else mav.addObject("payment", new Product());
		List<Packages> packages = packagesService.listAll();
		
		mav.addObject("packages", packages);
		return mav;
	}
	
	@RequestMapping(value = "/savePayment", method = RequestMethod.POST)
	public ModelAndView savePayment(@ModelAttribute("payment") Payment paymentSearch) {
		paymentService.updateMap(paymentSearch);
		paymentService.updatePayment(paymentSearch);
		if("3".equals(paymentSearch.getStatus())) {
			paymentService.updateIsCancel(paymentSearch);
		}
		if("1".equals(paymentSearch.getStatus())) {
			paymentService.updateNextPayDate(paymentSearch);
		}
		ModelAndView mav = new ModelAndView("payment");
		List<Payment> payment = paymentService.listAll(paymentSearch);		
		mav.addObject("payment", payment);	
		List<Packages> packages = packagesService.listAll();
		
		mav.addObject("packages", packages);
		mav.addObject("paymentSearch", paymentSearch);	
		return mav;
	}
}
