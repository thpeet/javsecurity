package org.jse.sgn;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;

import javax.crypto.Cipher;

/**
 * @author thpeet
 *
 * Sign a message and send it to a recipient.
 * Compute message digest and encrypt it. Send encrypted digest along with the message in clear text.
 *
 *
 *
 */
public class DSign {

    public static void main(String[] args)throws Exception {

        if(args.length!=1){
            System.err.println("Usage java DSign text");
            System.exit(1);
        }

        byte[] plainText = args[0].getBytes("UTF8");

        System.out.println("Digesting message using MD5 algorithm: message to sign: " + formatValue(args[0]));

        // Get a message digest object using the MD5 algorithm
        MessageDigest md = MessageDigest.getInstance("MD5");

        System.out.println(md.getProvider().getInfo());

        md.update(plainText);

        byte[] digest = md.digest();

        String digestString = new String(digest, "UTF8");

        System.out.println("\nDigest: " + formatValue( digestString ));

        System.out.println("\nGenerate RSA key pair");

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);

        KeyPair key = keyGen.generateKeyPair();
        System.out.println( "Finish generating RSA key" );



        // Encrypt the message digest with the RSA private key
        // to create the signature
        System.out.println( "\nStart encryption with private key..." );

        Cipher cipher  = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        cipher.init(Cipher.ENCRYPT_MODE, key.getPrivate());
        byte[] cipherText = cipher.doFinal( digest );

        System.out.print( "Finish encryption: " );
        System.out.println( formatValue( new String(cipherText, "UTF8")) );




        // Verify decrypt digest with the RSA public key.
        System.out.println( "\nStart decryption with public key..." );

        cipher.init(Cipher.DECRYPT_MODE, key.getPublic());
        byte[] decryptedDigest = cipher.doFinal(cipherText);

        System.out.print( "Finish decryption:	[ " );
        System.out.println( formatValue( new String(decryptedDigest, "UTF8") ) );

        // Recreate the message digest form plain test message.
        // What the recipient must do to verify the authenticity.
        md.reset();
        md.update(plainText);
        byte[] digestReceivedMessage = md.digest();

   	 	System.out.println( "---------------------------------------------------------------------------------" );

        if( areIdenticalDigest(decryptedDigest, digestReceivedMessage) ){
        	 System.out.println( "Signature verified and message can be trusted." );
        }else{
        	 System.out.println( "Signature not ok" );
        }
    }


    private static boolean areIdenticalDigest(byte[] digest1, byte[] digest2){

  	  boolean identical = true;

    	if(digest1.length == digest2.length){
    		  int i=0;
    	      while( identical && (i < digest1.length) ){
    	    	identical = (digest1[i] == digest2[i]);
    	        i++;
    	      }
    	}else{
    		identical = false;
    	}

    	return identical;
    }


    private static String formatValue(String value){
    	return String.format("[ %s ]", value);
    }

}
