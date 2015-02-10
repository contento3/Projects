package com.contento3.site.registration;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.contento3.account.dto.AccountDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.model.SystemTemplateNameEnum;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.site.user.dto.UserDto;
import com.contento3.site.user.service.UserRegistrationService;
import com.contento3.util.DomainUtil;

@Controller
public class UserRegistrationController {

	private static final Logger LOGGER = Logger.getLogger(UserRegistrationController.class);

	/**
	 * Used to create new user for a site.
	 */
	private UserRegistrationService registrationService;

	/**
	 * Template service that gets the registration related templates.
	 */
	private TemplateService templateService;
	
    @RequestMapping(value = "/register/login", method = RequestMethod.POST)
    public String registerLogin(@ModelAttribute("command") UserDto user,BindingResult result) {
    	return "redirect:userSuccess.htm";
    }

    /**
     * Add new user for a site
     * @param user
     * @param result
     * @param request
     * @return
     * @throws EntityNotFoundException 
     */
    @RequestMapping(value = "/register/process", method = RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute("user") final UserDto user,final BindingResult result,final HttpServletRequest request,final Model model) throws EntityNotFoundException {
    	Validate.notNull(user,"site cannot be null in user registration");

    	final SiteDto site = (SiteDto) request.getAttribute("site");
    	final AccountDto account = site.getAccountDto();	

		//Get the appropriate forwarding template after use is added.
		TemplateDto template;
		try {
			template = templateService.findSystemTemplateForAccount(SystemTemplateNameEnum.SYSTEM_REGISTER_SUCCESS, account.getAccountId());
		}
		catch(final EntityNotFoundException e){
			template = templateService.findSystemTemplateForAccount(SystemTemplateNameEnum.SYSTEM_REGISTER, account.getAccountId());
		}
		
    	if (result.hasErrors()) {
    		model.addAttribute("user",user);
    		return "/template"+template.getTemplatePath()+"/"+template.getTemplateName(); 
        }

    	user.setSiteId(site.getSiteId());
    	
		//This is required to get the template for right site
		model.addAttribute("site",site);
		
		try {
			registrationService.create(user,DomainUtil.fetchDomain(request));
		}
		catch (final EntityAlreadyFoundException e){
			result.addError(new ObjectError("invalid", "User already exists"));
			return "/template"+template.getTemplatePath()+"/"+template.getTemplateName(); 
		}
    	LOGGER.info(String.format("New user registered with username: [%s]",user.getUsername()));
		
		return "/template"+template.getTemplatePath()+"/"+template.getTemplateName(); 
    }
    

    /**
     * Displays the user registration form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/register/form", method = RequestMethod.GET)
    public String  showRegistrationForm(final Model model,final HttpServletRequest request) throws Exception {
    	final SiteDto site = (SiteDto) request.getAttribute("site");
    	final AccountDto account = site.getAccountDto();	
    	
    	Validate.notNull(site,"site cannot be null in user registration");
    	Validate.notNull(account,"account cannot be null in user registration");
    	
    	LOGGER.info("User Registration registering a user for site with siteId"+site.getSiteId());	
    	    	
		//This is required to get the template for right site
		model.addAttribute("site",site);
		model.addAttribute("user",new UserDto());
		final TemplateDto template = templateService.findSystemTemplateForAccount(SystemTemplateNameEnum.SYSTEM_REGISTER, account.getAccountId());
		return "/template"+template.getTemplatePath()+"/"+template.getTemplateName(); 
    }

	/**
	 * Sets the UserRegistrationService object
	 * @param registrationService
	 */
	public void setRegistrationService(final UserRegistrationService registrationService){
		this.registrationService = registrationService;
	} 

	/**
	 * Sets the TemplateService object
	 * @param registrationService
	 */
	public void setTemplateService(final TemplateService templateService){
		this.templateService = templateService;
	} 

}
