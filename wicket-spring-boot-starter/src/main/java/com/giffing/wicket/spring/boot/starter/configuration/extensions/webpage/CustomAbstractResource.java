package com.giffing.wicket.spring.boot.starter.configuration.extensions.webpage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.springframework.core.io.Resource;
import org.wicketstuff.annotation.mount.MountPath;

public class CustomAbstractResource extends ResourceReference {
	
	private Resource resource;

	private static CustomAbstractResource instance;

	public CustomAbstractResource(Resource resource) {
		super(CustomAbstractResource.class, resource.getFilename());
	    this.resource = resource;
	}

	@Override
	public IResource getResource() {

	    return new InputStreamResource(resource);
	}

	private static class InputStreamResource implements IResource {

		private static final long serialVersionUID = 1L;
		
		private final Resource resource;

	    public InputStreamResource(Resource resource) {
	        this.resource = resource;
	    }

	    @Override
	    public void respond(Attributes attributes) {
	    	OutputStream outputStream = attributes.getResponse().getOutputStream();
	    	try {
	    		InputStream inputStream = resource.getInputStream();
				IOUtils.copy(inputStream, outputStream);
				attributes.getParameters().add("filename", resource.getFilename());
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
	    }

	}
	
	public String getFilename(){
		String filename = this.resource.getFilename();
		try {
			String[] split = this.resource.getURI().toString().split("wicket-static");
			filename = split[1];
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return filename;
	}

}
