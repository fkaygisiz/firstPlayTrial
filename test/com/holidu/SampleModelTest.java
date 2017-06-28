package com.holidu;

import models.SampleModel;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

public class SampleModelTest {

    @Test
    public void findById() {
        running(fakeApplication(), () -> {
            SampleModel sampleModel = SampleModel.find.byId(1L);
            assertThat(sampleModel.getName(), equalTo("first sample"));
        });
    }
}

