/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vnpt.ssdc.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vnpt.ssdc.dto.Packages;
import com.vnpt.ssdc.dto.Product;
import com.vnpt.ssdc.dto.Users;
import com.vnpt.ssdc.service.PackagesService;
import com.vnpt.ssdc.service.ProductService;
import com.vnpt.ssdc.service.UserService;

/**
 *
 * @author kiendt
 */
@Controller
public class HomeController {
	@Autowired
	private ProductService service;
	@Autowired
	private UserService userService;
	
    @GetMapping("/")
    public String login() {
        return "home";
    }
    
    @GetMapping("/tables")
    public ModelAndView tables() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String currentPrincipalName = authentication.getName();
    	ModelAndView mav = new ModelAndView("tables");
		
		Users user = userService.selectByEmail(currentPrincipalName);
		
		mav.addObject("user", user);
		return mav;
    }
       
    @RequestMapping(value = "/saveTables", method = RequestMethod.POST)
	public ModelAndView saveTables(@ModelAttribute("product") Users user) {	
		service.update(user);
		ModelAndView mav = new ModelAndView("tables");
		mav.addObject("user", user);
		return mav;
	}
}
