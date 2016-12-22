package org.jse.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class FileHandler implements FileOperation {

	private static final int BUFFER_SIZE = 2048;

	private URI uri;
	private boolean loaded = false;;
	private ByteArrayOutputStream data;


	public FileHandler(){
		this.data = new ByteArrayOutputStream();
	}


	public FileHandler(byte[] data){
		this.data = new ByteArrayOutputStream(data.length);
		this.data.write(data, 0, data.length);

	}


	public boolean isLoaded() {
		return loaded;
	}


	public int size(){
		return data.size();
	}

	@Override
	public void load(String path) throws URISyntaxException {

			URI uri = URI.create("file:///"+path);
			load(uri);
	}


	@Override
	public void load(URI uri) {

		this.uri = uri;

		File file = new File(this.uri.getPath());

		if( file.exists() && file.isFile() ){

			try{

				BufferedInputStream bufInputStream = new BufferedInputStream( new FileInputStream(file) );

				byte[] buffer = new byte[BUFFER_SIZE];
				int bread = 0;
				while( (bread = bufInputStream.read(buffer) )!=-1 ){
					data.write(buffer,0,bread);

				}

				data.flush();
				bufInputStream.close();

			}catch(FileNotFoundException fileNotFoundException){
				this.loaded = false;
			}catch (IOException ioException) {
				this.loaded = false;
			}

			this.loaded = data.size() > 0 ;

		}

	}


	@Override
	public byte[] getContent() {
		return this.data.toByteArray();
	}


	@Override
	public void save(URI uri) throws Exception {

		BufferedOutputStream bo = new BufferedOutputStream( new FileOutputStream(new File(uri.getPath())));
		bo.write( this.data.toByteArray(), 0, this.data.size() );
		bo.flush();
		bo.close();
	}


	public static void main(String[] args)throws Exception{

		if( args.length != 1){
			System.out.println("Usage: java FileHandler filepath");
			System.exit(1);
		}

		System.out.println("Loading "+ args[0]);


		FileHandler fileHandler = new FileHandler();
		fileHandler.load( args[0] );

		if(fileHandler.isLoaded()){
			System.out.println( String.format("File %s loaded. Content: %d bytes read.", args[0], fileHandler.size() ) );
		}


	}
















}
