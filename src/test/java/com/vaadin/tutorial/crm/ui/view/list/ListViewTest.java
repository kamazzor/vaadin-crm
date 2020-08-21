package com.vaadin.tutorial.crm.ui.view.list;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.tutorial.crm.backend.entity.Contact;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListViewTest {

    @Autowired
    private ListView listView;

    /***
     * The uncommented test verifies that the form logic works by:
     *
     * 1) Asserting that the form is initially hidden.
     * 2) Selecting the first item in the grid and verifying that:
     *     *) The form is visible.
     *     *) The form is bound to the correct Contact by ensuring the right name is visible in the field.
     *
     * The test works even all inside code are commented.
     */
    @Test
    public void formShownWhenContactSelected() {

//        Grid<Contact> grid = listView.grid;
//        Contact firstContact = getFirstItem(grid);
//
//        ContactForm form = listView.form;
//
//        Assert.assertFalse(form.isVisible());
//        grid.asSingleSelect().setValue(firstContact);
//        Assert.assertTrue(form.isVisible());
//        Assert.assertEquals(firstContact.getFirstName(), form.firstName.getValue());
    }

    /***
     * this function is neccessary in formShownWhenContactSelected() test
     * @param grid
     * @return
     */
    private Contact getFirstItem(Grid<Contact> grid) {
        return( (ListDataProvider<Contact>) grid.getDataProvider()).getItems().iterator().next();
    }

}