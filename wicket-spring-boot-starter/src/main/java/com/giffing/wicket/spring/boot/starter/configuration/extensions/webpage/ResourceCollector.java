package com.giffing.wicket.spring.boot.starter.configuration.extensions.webpage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
public class ResourceCollector {
	
	private List<CustomAbstractResource> javascriptResources = new ArrayList<>();
	
	@PostConstruct
	public void init(){
		ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();   
		try {
			Resource[] mappingLocations = patternResolver.getResources("classpath*:wicket-static/**/*.js");
			for (Resource resource : mappingLocations) {
				javascriptResources.add(new CustomAbstractResource(resource));
			}
			
			
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public List<CustomAbstractResource> getJavascriptResources() {
		return javascriptResources;
	}

	public void setJavascriptResources(List<CustomAbstractResource> javascriptResources) {
		this.javascriptResources = javascriptResources;
	}

}
