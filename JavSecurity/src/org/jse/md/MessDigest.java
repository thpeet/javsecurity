package org.jse.md;

import java.security.MessageDigest;

public class MessDigest {

	public static void main(String[] args)throws Exception {

		if( args.length !=1){
			System.err.println("Usage: java MessDigest text");
			System.exit(1);
		}

		byte[] plainText = args[0].getBytes("UTF8");


		// get a message digest object using the MD5 algorithm
		MessageDigest md = MessageDigest.getInstance("MD5");

		System.out.println("\n" + md.getProvider().getInfo());

		md.update(plainText);
		System.out.println(" \nDigest: ");
		System.out.println( new String( md.digest(), "UTF8"));


	}

}
