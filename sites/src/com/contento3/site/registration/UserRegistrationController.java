package com.contento3.site.registration;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.AbstractTemplateView;

import com.contento3.account.dto.AccountDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.model.SystemTemplateNameEnum;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.site.registration.model.User;
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
    public String registerLogin(@ModelAttribute("command") User user,BindingResult result) {
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
    public String addUser(@ModelAttribute("user")final User user,final BindingResult result,final HttpServletRequest request,final Model model) throws EntityNotFoundException {
    	Validate.notNull(user,"site cannot be null in user registration");

    	UserDto userDto = new UserDto();
    	userDto.setPassword(user.getPassword());
    	userDto.setUsername(user.getUsername());
    	userDto.setPasswordReminder(user.getPasswordReminder());
    	
    	final SiteDto site = (SiteDto) request.getAttribute("site");
    	final AccountDto account = site.getAccountDto();	
    	userDto.setSiteId(site.getSiteId());
    	
		//This is required to get the template for right site
		model.addAttribute("siteId",site.getSiteId());

    	LOGGER.info(String.format("Trying to register user with username : [%s]",user.getUsername()));
		registrationService.create(userDto,DomainUtil.fetchDomain(request));
		
		//TODO 
		//Email the user for confirmation
		
		//Get the appropriate forwarding template after use is added.
		final TemplateDto template = templateService.findSystemTemplateForAccount(SystemTemplateNameEnum.SYSTEM_REGISTER_SUCCESS, account.getAccountId());
		return template.getTemplateName(); //TODO this needs to be dynamic so that we can redirect to appropriate page based on site.
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
		model.addAttribute("siteId",site.getSiteId());
		model.addAttribute("user",new User());
		return "user";
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
