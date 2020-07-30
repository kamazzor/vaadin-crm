package com.vaadin.tutorial.crm.backend.service;

import com.vaadin.tutorial.crm.backend.entity.Company;
import com.vaadin.tutorial.crm.backend.entity.Contact;
import com.vaadin.tutorial.crm.backend.repository.CompanyRepository;
import com.vaadin.tutorial.crm.backend.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ContactService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);
    private ContactRepository contactRepository;
    private CompanyRepository companyRepository;

    public ContactService(ContactRepository contactRepository,
                          CompanyRepository companyRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
    }

    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    public List<Contact> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    public long count() {
        return contactRepository.count();
    }

    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }

    public void save (Contact contact){
        if (contact == null) {
            LOGGER.error("Contact is null. Are you sure you have connected " +
                    "your form to the application?");
        }
        contactRepository.save(contact);
    }

    @PostConstruct
    public void populateTestData() {
        if (companyRepository.count() == 0) {
            companyRepository.saveAll(
                    Stream.of("Path-Way Electronics", "E-Tech Management", "Path-E-Tech Management")
                            .map(Company::new)
                            .collect(Collectors.toList()));
        }

        if (contactRepository.count() == 0) {
            Random r = new Random(0);
            List<Company> companies = companyRepository.findAll();
            contactRepository.saveAll(
                    Stream.of("Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
                            "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
                            "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
                            "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
                            "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
                            "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
                            "Jaydan Jackson", "Bernard Nilsen")
                            .map(name -> {
                                String[] split = name.split(" ");
                                Contact contact = new Contact();
                                contact.setFirstName(split[0]);
                                contact.setLastName(split[1]);
                                contact.setCompany(companies.get(r.nextInt(companies.size())));
                                contact.setStatus(Contact.Status.values()[r.nextInt(Contact.Status.values().length)]);
                                String email = (contact.getFirstName() + "." + contact.getLastName()
                                        + "@" + contact.getCompany().getName()
                                        .replaceAll("[\\s-]", "") + ".com").toLowerCase();
                                contact.setEmail(email);
                                return contact;
                            }).collect(Collectors.toList()));
        }
    }
}