package org.jse.crpt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.Cipher;

import org.jse.file.FileHandler;



/**
 * @author thpeet
 *
 * Data encryption using generated public key.
 *
 * This demo uses RSA 1024 bit algorithm to generate private and public key pair.
 * 1024 RSA bit can encrypt 117 bytes max. It is not suited for long file encryption
 * nor for production use.
 *
 */
public class PublicKeyEncryption implements Encryptor{


	public static void main(String[] args)throws Exception {

		if(args.length!=2){
			System.err.println("Usage java PublicKeyEncription filetarget filedestination");
			System.exit(1);
		}

		FileHandler fh = new FileHandler();
		fh.load(args[0]);

		if(fh.isLoaded()){

			if(fh.size() > 117){
				System.err.println("Source is too long max 117 bytes.");
				System.exit(1);
			}

			System.out.println("File content loaded.");

			PublicKeyEncryption encryption = new PublicKeyEncryption();
			byte[] encrypted = encryption.doIt( fh.getContent() );

			FileHandler encryptedFile = new FileHandler(encrypted);
			encryptedFile.savetoFile(args[1]);
		}




	}

	@Override
	public byte[] doIt(byte[] inputdata) {

		byte[] encryptedData = new byte[inputdata.length];

		try{

			//generate an RSA key pair
			System.out.println( "\nStart generating RSA key" );

			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);

			KeyPair keypair = keyGen.generateKeyPair();


			System.out.println( "Finish generating RSA key" );

			Cipher cipher  = Cipher.getInstance("RSA/ECB/PKCS1Padding");

			System.out.println( "\n" + cipher.getProvider().getInfo() );

			System.out.println( "\nStart encryption.." );

			cipher.init(Cipher.ENCRYPT_MODE, keypair.getPublic());

			cipher.doFinal( inputdata );

			System.out.println( "Encryption done." );


		}catch(Exception exception){
			System.err.println(exception.getMessage());
			exception.printStackTrace();
		}

		return encryptedData;
	}





}
