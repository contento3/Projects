package com.olive.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginLogoutController extends BaseController {
  @RequestMapping(method=RequestMethod.GET,value="/login.do")
  public void home() {
  } 
}	