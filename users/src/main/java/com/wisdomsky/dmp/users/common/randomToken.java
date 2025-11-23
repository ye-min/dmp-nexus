package com.wisdomsky.dmp.users.common;

import java.util.Random;

public class randomToken {
	public static String getaToken(int length){
		String base = "^#`~abcdefghijklmnopqrstuvwxyz0123456789$ABCDEFGHIJKLMNOPQRSRUVWXYZ><.?";   
		Random random = new Random();   
		StringBuffer sb = new StringBuffer();   
		for (int i = 0; i < length; i++) {   
		    int number = random.nextInt(base.length());   
		    sb.append(base.charAt(number));   
		}   
		return sb.toString() ;
	}
	
	public static String getaCouponCode(int length ) {
		String base = "0123456789ABCDEFGHIJKLMNOPQRSRUVWXYZ";   
		Random random = new Random();   
		StringBuffer sb = new StringBuffer();   
		for (int i = 0; i < length; i++) {   
		    int number = random.nextInt(base.length());   
		    sb.append(base.charAt(number));   
		}   
	return sb.toString() ;
		
	}
	
	public static String getasmscode(int length){
		String base = "1234567890";   
		Random random = new Random();   
		StringBuffer sb = new StringBuffer();   
		for (int i = 0; i < length; i++) {   
		    int number = random.nextInt(base.length());   
		    sb.append(base.charAt(number));   
		}   
		return sb.toString() ;
	}
	public static String getanumcode(int length){
		return getasmscode(length);
	}
	public static String getatcode(int length){
			String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSRUVWXYZ";   
			Random random = new Random();   
			StringBuffer sb = new StringBuffer();   
			for (int i = 0; i < length; i++) {   
			    int number = random.nextInt(base.length());   
			    sb.append(base.charAt(number));   
			}   
		return sb.toString() ;
	}

}
