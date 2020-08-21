package com.vaadin.tutorial.crm.it;

import com.vaadin.flow.component.login.testbench.LoginFormElement;
import org.junit.Assert;
import org.junit.Test;

/***
 * The LoginIT class tests the login view of Vaadin CRM
 */
public class LoginIT extends AbstractTest{

    public LoginIT(String route) {
        super("");
    }

    /***
     * Method  validate if can you log in as "user"
     */
    @Test
    public void loginAsValidUserSucceeds() {
        // Find the LoginForm used on the page
        LoginFormElement form = $(LoginFormElement.class).first();
        // Enter the credentials and log in
        form.getUsernameField().setValue("user");
        form.getPasswordField().setValue("password");
        form.getSubmitButton().click();
        // Ensure the login form is no longer visible
        Assert.assertFalse($(LoginFormElement.class).exists());
    }
}
