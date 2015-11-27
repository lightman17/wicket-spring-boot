package com.giffing.wicket.spring.boot.starter.configuration.extensions.webpage;

import javax.inject.Inject;

import org.apache.wicket.protocol.http.WebApplication;

import com.giffing.wicket.spring.boot.context.extensions.ApplicationInitExtension;
import com.giffing.wicket.spring.boot.context.extensions.WicketApplicationInitConfiguration;

@ApplicationInitExtension
public class WicketBootWebPageConfig implements WicketApplicationInitConfiguration{
	
	@Inject
	private ResourceCollector resourceCollector;
	
	@Override
	public void init(WebApplication webApplication) {
		for (CustomAbstractResource resource : resourceCollector.getJavascriptResources()) {
			webApplication.mountResource("/script" +resource.getFilename(), resource);
		}
	}

}
