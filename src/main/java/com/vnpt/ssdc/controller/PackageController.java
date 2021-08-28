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

import javax.servlet.http.HttpServletRequest;

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
import com.vnpt.ssdc.service.PackagesService;
import com.vnpt.ssdc.service.ProductService;

/**
 *
 * @author kiendt
 */
@Controller
public class PackageController {
	@Autowired
	private ProductService service;
	@Autowired
	private PackagesService packagesService;
    
    @GetMapping("/packages")
    public ModelAndView packages() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String currentPrincipalName = authentication.getName();
    	ModelAndView mav = new ModelAndView("packages");
		
		List<Packages> packages = packagesService.listAll();
		
		mav.addObject("packages", packages);
		return mav;
    }
    
    @RequestMapping("/addPackage")
	public String showNewProductPage(Model model) {
    	Packages packages = new Packages();
		model.addAttribute("packages", packages);
		
		return "new_package";
	}
    
    @RequestMapping(value = "/savePackage", method = RequestMethod.POST)
	public ModelAndView savePackage(@ModelAttribute("packages") Packages packages) {
		if(packages.getId() != null) {
			packagesService.update(packages);
		} else {
			packagesService.save(packages);
		}
		
		ModelAndView mav = new ModelAndView("packages");
		List<Packages> listPackages = packagesService.listAll();
		mav.addObject("packages", listPackages);
		return mav;
	}
    
    @RequestMapping("/editPackage")
	public ModelAndView editPackage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("edit_package");
		Packages packages = packagesService.get(Long.parseLong(request.getParameter("id")));
		mav.addObject("packages", packages);
		
		return mav;
	}
    
    @RequestMapping("/deletetPackage")
	public ModelAndView deletetPackage(HttpServletRequest request) {
    	packagesService.delete((Long.parseLong(request.getParameter("id"))));
    	ModelAndView mav = new ModelAndView("packages");
    	List<Packages> listPackages = packagesService.listAll();
		mav.addObject("packages", listPackages);
		return mav;		
	}
}
