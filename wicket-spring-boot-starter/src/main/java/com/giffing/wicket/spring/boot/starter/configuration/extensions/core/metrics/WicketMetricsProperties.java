package com.giffing.wicket.spring.boot.starter.configuration.extensions.core.metrics;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = WicketMetricsProperties.PROPERTY_PREFIX)
public class WicketMetricsProperties {


	public static final String PROPERTY_PREFIX = "wicket.core.metrics";
	
	private boolean enabled = true;
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
