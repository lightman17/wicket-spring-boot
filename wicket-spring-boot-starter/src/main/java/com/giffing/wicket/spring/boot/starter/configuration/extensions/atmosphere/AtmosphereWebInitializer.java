package com.giffing.wicket.spring.boot.starter.configuration.extensions.atmosphere;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.apache.wicket.atmosphere.TrackMessageSizeFilter;
import org.atmosphere.cache.UUIDBroadcasterCache;
import org.atmosphere.container.JSR356AsyncSupport;
import org.atmosphere.container.Tomcat7Servlet30SupportWithWebSocket;
import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereServlet;
import org.atmosphere.websocket.protocol.EchoProtocol;
import org.osgi.service.component.annotations.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration(value="wicketWebInitializer")
@ConditionalOnClass(value=AtmosphereServlet.class)
public class AtmosphereWebInitializer implements ServletContextInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		
		Dynamic addServlet = servletContext.addServlet("atmospherefilter", AtmosphereServlet.class);
		addServlet.setInitParameter("org.atmosphere.useWebSocket", "true");
		addServlet.setInitParameter("org.atmosphere.useNative", "true");
		addServlet.setInitParameter(ApplicationConfig.BROADCASTER_CACHE, UUIDBroadcasterCache.class.getName());
//		addServlet.setInitParameter("org.atmosphere.cpr.sessionSupport", "true");
		addServlet.setInitParameter("filterMappingUrlPattern", "/*");
		addServlet.setInitParameter("org.atmosphere.websocket.WebSocketProtocol", EchoProtocol.class.getName());
		addServlet.setInitParameter("org.atmosphere.cpr.broadcastFilterClasses", TrackMessageSizeFilter.class.getName());
//		addServlet.setInitParameter("org.atmosphere.cpr.asyncSupport", Tomcat7Servlet30SupportWithWebSocket.class.getName());
		servletContext.addListener(org.atmosphere.cpr.SessionSupport.class);
		addServlet.setLoadOnStartup(0);
		addServlet.addMapping("/*");
		addServlet.setAsyncSupported(true);
	}

}
