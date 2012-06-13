package com.contento3.util;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


public class DomainUtil {

	private static final Logger LOGGER = Logger.getLogger(DomainUtil.class);

	public static String fetchDomain(HttpServletRequest request) {
		URL url =null;
		try {
			url = new URL(request.getRequestURL().toString());
		} catch (MalformedURLException e) {
			LOGGER.error(String.format("Something wrong with url [%s]",request.getRequestURL()),e);
		}
		return url.getHost();
	}

}
