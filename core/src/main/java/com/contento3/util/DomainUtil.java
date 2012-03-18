package com.contento3.util;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

public class DomainUtil {

	public static String fetchDomain(HttpServletRequest request) throws MalformedURLException {
		URL url = new URL(request.getRequestURL().toString());
		return url.getHost();
	}

}
