package org.jse.sgn;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.SignatureException;

/**
 * @author thpeet
 *
 * <p>Demonstrate how to sign a message using java.security.Signature class.</p>
 *
 * First it generates a private public key pair and sign a message with a private key.
 * Then verify the message with the public key.
 *
 */
public class DSignature {

	public static void main(String[] args)throws Exception {


		  if(args.length!=1){
	            System.err.println("Usage java DSignature text");
	            System.exit(1);
	      }

	      byte[] plainText = args[0].getBytes("UTF8");

	      System.out.println("\nGenerate RSA key pair");

	      // Generate key pair using RSA algorithm
	      KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
	      keyGen.initialize(1024);

	      KeyPair key = keyGen.generateKeyPair();
	      System.out.println( "Finish generating RSA key" );


	      Signature sig = Signature.getInstance("MD5withRSA");
	      sig.initSign( key.getPrivate() );

	      sig.update(plainText);
	      byte[] signature = sig.sign();

	      System.out.println( sig.getProvider().getInfo() );
	      System.out.println( "\nSignature:" );
	      System.out.println( new String(signature, "UTF8") );


	      // Verify the signature with the public key
	      System.out.println( "\nStart signature verification" );

	      Signature sigtoVerfify = Signature.getInstance("MD5withRSA");
	      sigtoVerfify.initVerify( key.getPublic() );
	      sigtoVerfify.update(plainText);

	      try{
	    	  if( sigtoVerfify.verify(signature) ){
	    		  System.out.println("Signature using public key verified");
	    	  }else{
	    		  System.out.println("Signature using public key failed");
	    	  }
	      }catch(SignatureException signatureException){
	    	  System.out.println("Signature using public key failed");
	      }
	}

}
