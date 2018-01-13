package com.holidu;

import models.Apartment;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

public class ApartmentTest {
    @Test
    public void findById() {
        running(fakeApplication(), () -> {
            Apartment apartment = Apartment.find.byId(1L);
            assertTrue(apartment != null);
            assertThat(apartment.getName(), equalTo("Finca on Mallorca 1"));
            assertThat(apartment.getFacilities().size(), equalTo(6));
        });
    }
}
