package com.giffing.wicket.spring.boot.starter.configuration.extensions.webpage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;
import org.apache.wicket.core.util.resource.UrlResourceStream;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.caching.IStaticCacheableResource;
import org.apache.wicket.util.resource.IResourceStream;
import org.springframework.core.io.Resource;

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

	private static class InputStreamResource implements IResource, IStaticCacheableResource {

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

		@Override
		public boolean isCachingEnabled() {
			return false;
		}

		@Override
		public Serializable getCacheKey() {
			try {
				return resource.getURI().toString();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}

		@Override
		public IResourceStream getResourceStream() {
			return null;
		}

	}
	
	public String getRelativePath(){
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
