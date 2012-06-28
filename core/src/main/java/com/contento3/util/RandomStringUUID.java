package com.contento3.util;

import java.util.UUID;

public class RandomStringUUID {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UUID uuid = UUID.randomUUID();
		 String randomUUIDString = uuid.toString();
		 System.out.println(randomUUIDString);
	}

}
