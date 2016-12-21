package org.jse.me;

import java.security.MessageDigest;


public class MDigest {

	public static void main(String[] args)throws Exception {

		if( args.length !=1 ){
			System.err.println("Usage: java MessDigest text");
			System.exit(1);
		}


		byte[] plainText = args[0].getBytes("UTF8");

		// get a message digest object using the MD5 algorithm
		MessageDigest md = MessageDigest.getInstance("MD5");

		System.out.println(md.getProvider().getInfo());

		md.update(plainText);
		System.out.println(" \nDigest: ");

		String digest= new String( md.digest(), "UTF8");
		System.out.println( digest );

	}


}
