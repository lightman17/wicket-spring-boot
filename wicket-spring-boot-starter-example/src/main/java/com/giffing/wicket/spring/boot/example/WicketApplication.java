package com.giffing.wicket.spring.boot.example;

import org.apache.wicket.Page;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.giffing.wicket.spring.boot.example.pages.BootMePage;
import com.giffing.wicket.spring.boot.starter.app.WicketBootWebApplication;
import com.giffing.wicket.spring.boot.starter.context.WicketSpringBootApplication;
import com.giffing.wicket.spring.boot.starter.pages.HomePage;

@WicketSpringBootApplication
public class WicketApplication extends WicketBootWebApplication {
	
	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder()
			.sources(WicketApplication.class)
			.run(args);
	}
	
	
	@Override
	public Class<? extends Page> getHomePage() {
		return BootMePage.class;
	}
}
