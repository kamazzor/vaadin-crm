package com.vaadin.tutorial.crm.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.tutorial.crm.ui.view.login.LoginView;
import org.springframework.stereotype.Component;

/***
 * The {@link Component} annotation registers the listener. Vaadin will pick it up on startup.
 */
@Component 
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

	/***
	 * listen for the initialization of the UI (the internal root component in Vaadin)
	 * and then add a listener before every view transition.
	 * @param event
	 */
	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(uiEvent -> { 
			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::authenticateNavigation);
		});
	}

	/***
	 * That method reroutes all requests to the login, if the user is not logged in
	 * @param event
	 */
	private void authenticateNavigation(BeforeEnterEvent event) {
		if (!LoginView.class.equals(event.getNavigationTarget())
		    && !SecurityUtils.isUserLoggedIn()) { 
			event.rerouteTo(LoginView.class);
		}
	}
}