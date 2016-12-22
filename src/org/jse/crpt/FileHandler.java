package org.jse.crpt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHandler {

	private static final int BUFFER_SIZE = 2048;

	private String pathname;
	private boolean loaded = false;;
	private ByteArrayOutputStream content = new ByteArrayOutputStream();


	public FileHandler(String pathname){

		this.pathname = pathname;

		File file = new File(this.pathname);

		if( file.exists() && file.isFile() ){

			try{

				BufferedInputStream bufInputStream = new BufferedInputStream( new FileInputStream(file) );

				byte[] buffer = new byte[BUFFER_SIZE];
				int bread = 0;
				while( (bread = bufInputStream.read(buffer) )!=-1 ){
					content.write(buffer,0,bread);

				}

				content.flush();
				bufInputStream.close();

			}catch(FileNotFoundException fileNotFoundException){
				this.loaded = false;
			}catch (IOException ioException) {
				this.loaded = false;
			}

			this.loaded = content.size() > 0 ;

		}
	}


	public boolean isLoaded() {
		return loaded;
	}


	public byte[] toArray(){
		return content.toByteArray();
	}


	public int size(){
		return content.size();
	}


	public void persist(String destPathName) throws Exception{
		BufferedOutputStream bo = new BufferedOutputStream( new FileOutputStream(new File(destPathName)));
		bo.write(this.toArray(),0,content.size());
		bo.flush();
		bo.close();
	}


	public static void main(String[] args)throws Exception{

		if( args.length != 1){
			System.out.println("Usage: java FileHandler filepath");
			System.exit(1);
		}

		System.out.println("Loading "+ args[0]);

		FileHandler fileHandler = new FileHandler( args[0] );

		if(fileHandler.isLoaded()){
			System.out.println( String.format("File %s loaded. Content: %d bytes read.", args[0], fileHandler.size() ) );
		}

		fileHandler.persist("c:/temp/log/test.txt");

	}

}
