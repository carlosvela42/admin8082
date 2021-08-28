/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vnpt.ssdc.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.vnpt.ssdc.dto.Code;
import com.vnpt.ssdc.dto.Product;
import com.vnpt.ssdc.service.CodeService;
import com.vnpt.ssdc.service.ProductService;
import com.vnpt.ssdc.service.UserService;

/**
 *
 * @author kiendt
 */
@Controller
public class CodeController {
	@Autowired
	private ProductService service;
	@Autowired
	private UserService userService;
	@Autowired
	private CodeService codeService;
    
	@GetMapping("/code")
    public ModelAndView code() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String currentPrincipalName = authentication.getName();
    	ModelAndView mav = new ModelAndView("code");
		
		List<Code> code = codeService.listAll();
		
		mav.addObject("codes", code);
		return mav;
    }
	
	 @RequestMapping("/addCode")
		public String showNewProductPage(Model model) {
	    	Code code = new Code();
			model.addAttribute("code", code);
			
			return "new_code";
		}
	    
	    @RequestMapping(value = "/saveCode", method = RequestMethod.POST)
		public ModelAndView saveCode(@ModelAttribute("code") Code code) {
			if(code.getId() != null) {
				codeService.update(code);
			} else {
				codeService.save(code);
			}
			
			ModelAndView mav = new ModelAndView("code");
			List<Code> listCode = codeService.listAll();
			mav.addObject("codes", listCode);
			return mav;
		}
	    
	    @RequestMapping("/editCode")
		public ModelAndView editCode(HttpServletRequest request) {
			ModelAndView mav = new ModelAndView("edit_code");
			Code code = codeService.get(Long.parseLong(request.getParameter("id")));
			mav.addObject("code", code);
			
			return mav;
		}
}
