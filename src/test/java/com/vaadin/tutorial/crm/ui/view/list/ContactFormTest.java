package com.vaadin.tutorial.crm.ui.view.list;

import com.vaadin.tutorial.crm.backend.entity.Company;
import com.vaadin.tutorial.crm.backend.entity.Contact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ContactFormTest {
    private List<Company> companies;
    private Contact marcUsher;
    private Company company1;
    private Company company2;

    @Before
    public void setupData() {
        companies = new ArrayList<>();
        company1 = new Company("Vaadin LTD");
        company2 = new Company("IT 2");
        companies.add(company1);
        companies.add(company2);

        marcUsher = new Contact();
        marcUsher.setFirstName("Marc");
        marcUsher.setLastName("Usher");
        marcUsher.setEmail((marcUsher.getFirstName() + "@" + marcUsher.getLastName().
                replaceAll("[\\s-]", "") + ".com").toLowerCase());
        marcUsher.setStatus(Contact.Status.NotContacted);
        marcUsher.setCompany(company2);
    }

    @Test
    public void formFieldsPopulated() {
        ContactForm contactForm = new ContactForm(companies);
        contactForm.setContact(marcUsher);
        Assert.assertEquals("Marc", contactForm.firstName.getValue());
        Assert.assertEquals("Usher", contactForm.lastName.getValue());
        Assert.assertEquals("marc@usher.com", contactForm.email.getValue());
        Assert.assertEquals(company2, contactForm.company.getValue());
        Assert.assertEquals(Contact.Status.NotContacted, contactForm.status.getValue());
    }

    @Test
    public void saveEventHasCorrectValue(){

        ContactForm contactForm = new ContactForm(companies);
        Contact contact = new Contact();
        contactForm.setContact(contact);

        //populate contactForm
        contactForm.firstName.setValue("John");
        contactForm.lastName.setValue("Doe");
        contactForm.company.setValue(company1);
        contactForm.email.setValue("john@doe.com");
        contactForm.status.setValue(Contact.Status.Customer);

        //As ContactForm fires an event on save and the event data is needed for the test,
        // an AtomicReference is used to store the event data, without using a class field.
        AtomicReference<Contact> savedContactRef = new AtomicReference<>(null);
        contactForm.addListener(ContactForm.SaveEvent.class, e -> {
            savedContactRef.set(e.getContact());
        });
        contactForm.save.click();
        Contact saveContact = savedContactRef.get();

        Assert.assertEquals("John", saveContact.getFirstName());
        Assert.assertEquals("Doe", saveContact.getLastName());
        Assert.assertEquals("john@doe.com", saveContact.getEmail());
        Assert.assertEquals(company1, saveContact.getCompany());
        Assert.assertEquals(Contact.Status.Customer, saveContact.getStatus());

    }
}
