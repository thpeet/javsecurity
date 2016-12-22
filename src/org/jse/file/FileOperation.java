package org.jse.file;

import java.net.URI;

public interface FileOperation {

	public void load(String path) throws Exception;

	public void load(URI uri);

	public boolean isLoaded();

	public byte[] getContent();

	public void save(URI uri) throws Exception;


}
