package com.giffing.wicket.spring.boot.starter.configuration.extensions.webpage;

import javax.inject.Inject;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * A default WebPage which can be used for common configuration possibilities.
 * 
 * 
 * @author Marc Giffing
 *
 */
public class WicketBootWebPage extends WebPage{

	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="wicketextensionResourceCollector")
	private ResourceCollector resourceCollector;
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		for(CustomAbstractResource resource : resourceCollector.getJavascriptResources()){
			JavaScriptReferenceHeaderItem forReference = JavaScriptReferenceHeaderItem
					.forReference(resource);
			response.render(forReference);
			
		}
		
	}

}
