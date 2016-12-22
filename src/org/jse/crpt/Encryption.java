package org.jse.crpt;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.jse.file.FileHandler;

/**
 * @author thpeet
 *
 */
public class Encryption {

	public static void main(String[] args) throws Exception {

		if(args.length!=2){
			System.err.println("Usage java EncryptionTest filetarget filedestination");
			System.exit(1);
		}


		FileHandler fh = new FileHandler();
		fh.load(args[0]);

		if(fh.isLoaded()){

			System.out.println("File content loaded.");

			Encryption encryption = new Encryption();
			byte[] encrypted = encryption.doIt( fh.getContent() );

			FileHandler encryptedFile = new FileHandler(encrypted);
			encryptedFile.savetoFile(args[1]);
		}
	}




	public byte[] doIt(final byte[] inputdata){

		byte[] encryptedData = new byte[0];

		try{

			System.out.println( "Start generating DES key" );

			KeyGenerator kegen = KeyGenerator.getInstance("DES");

			Key privateKey = kegen.generateKey();

			System.out.println( "Finish generating DES key" );

			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			System.out.println( "\nCipher provider: " + cipher.getProvider().getInfo() );

			System.out.println( "\nStart encryption.." );

			cipher.init(Cipher.ENCRYPT_MODE, privateKey);

			System.out.println( "Encryption done." );

			return cipher.doFinal(inputdata);

		}catch(Exception exception){
			System.err.println(exception.getMessage());
		}

		return encryptedData;
	}



}
