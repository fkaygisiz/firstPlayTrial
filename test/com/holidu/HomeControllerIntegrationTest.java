package com.holidu;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import static play.test.Helpers.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class HomeControllerIntegrationTest {

    @Test
    public void testIntegration() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, browser -> {
            browser.goTo("http://localhost:3333");
            assertThat(browser.pageSource(), equalTo("{\"title\":\"Holidu Search Challenge\",\"version\":\"1\"}"));
        });
    }
}
