package com.giffing.wicket.spring.boot.starter.configuration.extensions.core.metrics;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.apache.wicket.metrics.WicketMetrics;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.giffing.wicket.spring.boot.context.extensions.ApplicationInitExtension;
import com.giffing.wicket.spring.boot.context.extensions.WicketApplicationInitConfiguration;
import com.giffing.wicket.spring.boot.context.extensions.boot.actuator.WicketAutoConfig;
import com.giffing.wicket.spring.boot.context.extensions.boot.actuator.WicketEndpointRepository;
import com.giffing.wicket.spring.boot.starter.configuration.extensions.core.metrics.WicketMetricAutoConfig.ConsoleProperties;
import com.giffing.wicket.spring.boot.starter.configuration.extensions.core.metrics.WicketMetricAutoConfig.GraphiteProperties;
import com.giffing.wicket.spring.boot.starter.web.WicketWebInitializer;

@ApplicationInitExtension
@ConditionalOnProperty(prefix = WicketMetricsProperties.PROPERTY_PREFIX, value = "enabled", matchIfMissing = true)
@ConditionalOnClass(value = GraphiteReporter.class)
@EnableConfigurationProperties({ WicketMetricsProperties.class, GraphiteProperties.class, ConsoleProperties.class })
public class WicketMetricAutoConfig implements WicketApplicationInitConfiguration {

	@Autowired
	private WicketMetricsProperties props;

	@Autowired
	private GraphiteProperties graphiteProperties;
	
	@Autowired
	private ConsoleProperties consoleProperties;

	@Autowired
	private WicketEndpointRepository wicketEndpointRepository;

	@Override
	public void init(WebApplication webApplication) {
		WicketMetrics.setFilterName(WicketWebInitializer.WICKET_FILTERNAME);
		MetricRegistry metricRegistry = WicketMetrics.getMetricRegistry();

		if (graphiteProperties.isEnabled()) {

			final Graphite graphite = new Graphite(new InetSocketAddress(graphiteProperties.getUrl(), graphiteProperties.getPort()));
			GraphiteReporter graphiteReporter = GraphiteReporter
					.forRegistry(metricRegistry)
					.prefixedWith(graphiteProperties.getPrefixedWidth())
					.convertRatesTo(graphiteProperties.getConvertRatesTo())
					.convertDurationsTo(graphiteProperties.getConvertDuratiosTo())
					.filter(MetricFilter.ALL) //TODO...
					//TODO add all possible configurations
					.build(graphite);

			graphiteReporter.start(graphiteProperties.getPeriod(), graphiteProperties.getPeriodUnit());

		}

		if(consoleProperties.isEnabled()) {
			ConsoleReporter reporter = ConsoleReporter
					.forRegistry(metricRegistry)
					.convertRatesTo(consoleProperties.getConvertRatesTo())
					.convertDurationsTo(consoleProperties.getConvertDuratiosTo())
					.filter(MetricFilter.ALL) //TODO...
					//TODO add all possible configurations
					.build();
			reporter.start(consoleProperties.getPeriod(), consoleProperties.getPeriodUnit());
		}
		

		wicketEndpointRepository
				.add(new WicketAutoConfig
						.Builder(this.getClass())
						.withDetail("properties", props)
						.withDetail("graphiteProperties", graphiteProperties)
						.withDetail("consoleProperties", consoleProperties)
						.build());

	}

	
	@ConfigurationProperties(prefix = WicketMetricsProperties.PROPERTY_PREFIX + ".console")
	static class ConsoleProperties {
		
		private boolean enabled = false;
		
		private long period = 5;

		private TimeUnit convertRatesTo = TimeUnit.SECONDS;

		private TimeUnit convertDuratiosTo = TimeUnit.MICROSECONDS;

		private TimeUnit periodUnit = TimeUnit.SECONDS;

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public long getPeriod() {
			return period;
		}

		public void setPeriod(long period) {
			this.period = period;
		}

		public TimeUnit getPeriodUnit() {
			return periodUnit;
		}

		public void setPeriodUnit(TimeUnit periodUnit) {
			this.periodUnit = periodUnit;
		}

		public TimeUnit getConvertRatesTo() {
			return convertRatesTo;
		}

		public void setConvertRatesTo(TimeUnit convertRatesTo) {
			this.convertRatesTo = convertRatesTo;
		}

		public TimeUnit getConvertDuratiosTo() {
			return convertDuratiosTo;
		}

		public void setConvertDuratiosTo(TimeUnit convertDuratiosTo) {
			this.convertDuratiosTo = convertDuratiosTo;
		}
		
	}
	
	@ConfigurationProperties(prefix = WicketMetricsProperties.PROPERTY_PREFIX + ".graphite")
	static class GraphiteProperties {

		private boolean enabled = false;

		private String prefixedWidth = "WebApplications"; // ?

		private TimeUnit convertRatesTo = TimeUnit.SECONDS;

		private TimeUnit convertDuratiosTo = TimeUnit.MICROSECONDS;

		private String url = "127.0.0.1";
		
		private Integer port = 2003;

		private long period = 5;
		
		private TimeUnit periodUnit = TimeUnit.SECONDS;
		
		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public String getPrefixedWidth() {
			return prefixedWidth;
		}

		public void setPrefixedWidth(String prefixedWidth) {
			this.prefixedWidth = prefixedWidth;
		}

		public TimeUnit getConvertRatesTo() {
			return convertRatesTo;
		}

		public void setConvertRatesTo(TimeUnit convertRatesTo) {
			this.convertRatesTo = convertRatesTo;
		}

		public TimeUnit getConvertDuratiosTo() {
			return convertDuratiosTo;
		}

		public void setConvertDuratiosTo(TimeUnit convertDuratiosTo) {
			this.convertDuratiosTo = convertDuratiosTo;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		public long getPeriod() {
			return period;
		}

		public void setPeriod(long period) {
			this.period = period;
		}

		public TimeUnit getPeriodUnit() {
			return periodUnit;
		}

		public void setPeriodUnit(TimeUnit periodUnit) {
			this.periodUnit = periodUnit;
		}

	}

}
