package com.contento3.site.registration;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractTemplateView;

import com.contento3.site.registration.model.User;
import com.contento3.site.user.dto.UserDto;
import com.contento3.site.user.service.UserRegistrationService;
import com.contento3.util.DomainUtil;

@Controller
public class UserRegistrationController {

	private static final Logger LOGGER = Logger.getLogger(UserRegistrationController.class);

	/**
	 * Handles all the rendering to handle freemarker template
	 */
	private AbstractTemplateView freemarkerView;

	/**
	 * Used to create new user for a site.
	 */
	private UserRegistrationService registrationService;
	
    @RequestMapping(value = "/register/login", method = RequestMethod.POST)
    public String registerLogin(@ModelAttribute("command") User user,BindingResult result) {
    	return "redirect:userSuccess.htm";
    }

    /**
     * Add new user for a site
     * @param user
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "/register/process", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("command") User user,BindingResult result,HttpServletRequest request) {
    	UserDto userDto = new UserDto();
    	userDto.setPassword(user.getPassword());
    	userDto.setUsername(user.getUsername());
    	userDto.setPasswordReminder(user.getPasswordReminder());
    	
    	LOGGER.info(String.format("Trying to register user with username : [%s]",user.getUsername()));
		registrationService.create(userDto,DomainUtil.fetchDomain(request));
    	return "redirect:userSuccess.htm"; //TODO this needs to be dynamic so that we can redirect to appropriate page based on site.
    }

    /**
     * Displays the user registration form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/register/form", method = RequestMethod.GET)
    public ModelAndView showRegistrationForm(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	LOGGER.info("User Registration");	
    	
		ModelAndView modelAndView = null;
		modelAndView = new ModelAndView();
		modelAndView.addObject("command", new User());
		modelAndView.setView(freemarkerView); 

		return modelAndView;
    }

    /**
     * Sets the FreeMarkerView object.
     * @param freemarkerView
     */
	public void setFreeMarkerView(final AbstractTemplateView freemarkerView){
		this.freemarkerView = freemarkerView;
	} 


	/**
	 * Sets the UserRegistrationService object
	 * @param registrationService
	 */
	public void setRegistrationService(final UserRegistrationService registrationService){
		this.registrationService = registrationService;
	} 

}
