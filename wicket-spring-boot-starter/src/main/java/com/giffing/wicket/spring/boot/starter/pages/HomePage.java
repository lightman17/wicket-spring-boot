package com.giffing.wicket.spring.boot.starter.pages;


import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.atmosphere.Subscribe;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import com.giffing.wicket.spring.boot.starter.app.WicketBootWebApplication;

/**
 * The default home page which is called after the user is successfully logged
 * in. Can be overridden in the {@link WicketBootWebApplication}
 * 
 * @author Marc Giffing
 *
 */
@AuthorizeInstantiation("ROLE_USER")
public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;

	private Component timeLabel;
	private Component messageLabel;
	
	public HomePage() {
		add(new Label("message", Model.of("Huhu")));
		
		add(timeLabel = new Label("time", Model.of("start")).setOutputMarkupId(true));
		add(messageLabel = new Label("message", Model.of("-")).setOutputMarkupId(true));
		
		IndicatingAjaxLink<String> ajaxLink = new IndicatingAjaxLink<String>("logout") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				System.out.println("aaaa");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				getSession().invalidate();
				setResponsePage(HomePage.class);
			}
		
			
		};
			
		add(ajaxLink);
	}
	
	@Subscribe
	public void updateTime(AjaxRequestTarget target, Date event)
	{
		timeLabel.setDefaultModelObject(event.toString());
		target.add(timeLabel);
	}

	@Subscribe
	public void receiveMessage(AjaxRequestTarget target, String message)
	{
		messageLabel.setDefaultModelObject(message);
		target.add(messageLabel);
	}
}
