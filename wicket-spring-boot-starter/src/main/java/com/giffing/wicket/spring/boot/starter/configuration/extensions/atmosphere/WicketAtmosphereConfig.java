package com.giffing.wicket.spring.boot.starter.configuration.extensions.atmosphere;

import org.apache.wicket.Page;
import org.apache.wicket.atmosphere.EventBus;
import org.apache.wicket.atmosphere.ResourceRegistrationListener;
import org.apache.wicket.atmosphere.config.AtmosphereLogLevel;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import com.giffing.wicket.spring.boot.starter.configuration.WicketApplicationInitConfiguration;
import com.giffing.wicket.spring.boot.starter.configuration.extensions.beanvalidation.BeanValidationProperties;

@Component
@ConditionalOnProperty(prefix = "wicket.beanvalidation", value = "enabled", matchIfMissing = true)
@ConditionalOnClass(value = org.apache.wicket.bean.validation.BeanValidationConfiguration.class)
@EnableConfigurationProperties({ BeanValidationProperties.class })
public class WicketAtmosphereConfig implements WicketApplicationInitConfiguration {

	@Autowired
	private GenericApplicationContext  applicationContext;
	
	@Override
	public void init(WebApplication webApplication) {
		EventBus eventBus = new EventBus(webApplication);
		eventBus.getParameters().setLogLevel(AtmosphereLogLevel.DEBUG);
		eventBus.addRegistrationListener(new ResourceRegistrationListener() {

			@Override
			public void resourceUnregistered(String uuid) {
				System.out.println("Unregistered " + uuid);
			}

			@Override
			public void resourceRegistered(String uuid, Page page) {
				System.out.println("Registered " + uuid);
			}
		});
		
		applicationContext.getBeanFactory().registerSingleton("eventBus", eventBus );
		
	}

}
