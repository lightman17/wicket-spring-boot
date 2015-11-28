package com.giffing.wicket.spring.boot.starter.configuration.extensions.webpage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.wicket.ResourceBundles;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference.Key;

import com.giffing.wicket.spring.boot.context.extensions.ApplicationInitExtension;
import com.giffing.wicket.spring.boot.context.extensions.WicketApplicationInitConfiguration;

@ApplicationInitExtension
public class WicketBootWebPageConfig implements WicketApplicationInitConfiguration{
	
	@Inject
	private ResourceCollector resourceCollector;
	
	@Override
	public void init(WebApplication webApplication) {
		ResourceBundles bundles = webApplication.getResourceBundles();
		
		Map<String, List<CustomAbstractResource>> bundlesMap = new HashMap<>();
		for (CustomAbstractResource resource : resourceCollector.getJavascriptResources()) {
			FilenameParser parser = new FilenameParser(resource.getRelativePath(), "wicket-static");
			if(parser.haseBundleName()){
				List<CustomAbstractResource> bundleList =  bundlesMap.get(parser.getBundlename());
				if(bundleList == null){
					bundleList = new ArrayList<>();
					bundlesMap.put(parser.getBundlename(), bundleList);
				}
				bundleList.add(resource);
			}else {
				webApplication.mountResource("/script" +resource.getRelativePath(), resource);
			}
		}
		
		for(Entry<String, List<CustomAbstractResource>> entry : bundlesMap.entrySet()){
			String key = entry.getKey();
			List<CustomAbstractResource> value = entry.getValue();
			List<JavaScriptResourceReference> jsRefs = new ArrayList<>();
			for (CustomAbstractResource customAbstractResource : value) {
				jsRefs.add(new JavaScriptResourceReference(new Key(customAbstractResource)));
			}
			JavaScriptResourceReference[] array = jsRefs.toArray(new JavaScriptResourceReference[jsRefs.size()]);
			bundles.addJavaScriptBundle(webApplication.getClass(), key, array);
		}
		
	}

}
