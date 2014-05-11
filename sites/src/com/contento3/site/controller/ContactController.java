package com.contento3.site.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.contento3.module.email.EmailService;
import com.contento3.module.email.model.EmailInfo;


/**
 * This is a very specific contact us form handler. At the moment 
 * it is used on contento3 website to make things quicker for the 
 * c3 website.
 * 
 * The roadmap is to develop custom form solution for the platform.
 */
@Controller
@RequestMapping("/contact")
public class ContactController {

	@RequestMapping(value="/contact",method = RequestMethod.POST)
	public ModelAndView handleContactForm(@RequestParam(value = "name" , required = true) final String name,
							 @RequestParam(value = "email", required = true) final String email, 
							 @RequestParam(value = "message", required = true) final String message,
							 @RequestParam(value = "country", required = false) final String country,
							 @RequestParam(value = "reason", required = false) final String reason,
							 @RequestParam(value = "pageName", required = true) final String redirectTo,final Model model){
		
		final EmailService emailService = new EmailService();
		final EmailInfo emailInfo = new EmailInfo();
		emailInfo.setFrom("info@contento3.com");
		emailInfo.setTo("info@contento3.com");
		emailInfo.setSubject(reason);
		emailInfo.setEmailText(buildEmailHtml(name,country,message,email));
		emailService.send(emailInfo);
		final ModelAndView mv = new ModelAndView("/"+redirectTo);
		model.addAttribute("successMsg", "Thank you for contacting us, we will be in touch as soon as possible.");
		
		return mv;
	}

	private String buildEmailHtml(final String name,final String country, final String message,final String email){
		String string = "<div>Name:"+name+"</div><br/>" +
						"<div>Email:"+email+"</div><br/><br/>"+
						"<div>Country:"+country+"</div><br/><br/>"+
						"<div>"+message+"</div>";
		return string;
	}
}
