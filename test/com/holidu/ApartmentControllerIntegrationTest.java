package com.holidu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Apartment;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;
import static play.test.Helpers.HTMLUNIT;

public class ApartmentControllerIntegrationTest {
    @Test
    public void testIntegration() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo("http://localhost:3333/apartment/search?name=Finca");
            try {
                List<Apartment> apartments = new ObjectMapper().readValue(browser.pageSource(), new TypeReference<List<Apartment>>() {
                });
                assertThat(apartments.size(), equalTo(3));
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("Exception " + e);
            }
        });
    }

    @Test
    public void shouldGetOnlyFincasOfWhichNameContainsFincaAndFacilitiesContainsTVandGarden() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo("http://localhost:3333/apartment/search?name=Finca&facilities=GARDEN&facilities=TV&apartmentType=Finca");
            try {
                List<Apartment> apartments = new ObjectMapper().readValue(browser.pageSource(), new TypeReference<List<Apartment>>() {
                });
                assertThat(apartments.size(), equalTo(1));
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("Exception " + e);
            }
        });
    }

    @Test
    public void shouldGetOnlyApartmentsOfWhichFacilitiesContainsGarden() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo("http://localhost:3333/apartment/search?facilities=GARDEN");
            try {
                List<Apartment> apartments = new ObjectMapper().readValue(browser.pageSource(), new TypeReference<List<Apartment>>() {
                });
                for (Apartment apartment : apartments) {
                    long gardenFacilityCount = apartment.getFacilities().stream().filter(e -> e.getName().toLowerCase().contains("garden")).count();
                    assertTrue(0 < gardenFacilityCount);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("Exception " + e);
            }
        });
    }
}
