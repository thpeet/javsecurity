package org.jse.file;

import java.net.URI;

public interface FileOperation {

	public void load(String filenamepath) throws Exception;

	public void load(URI uri);

	public boolean isLoaded();

	public byte[] getContent();

	public void save(URI uri) throws Exception;

	public void savetoFile(String filenamepath) throws Exception;


}
