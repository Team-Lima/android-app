package com.lima2017.neuralguide.api.web;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WebApiConfigTest {
    @Test
    public void on_creation_with_null_values_gives_defaults () throws Exception {
        WebApiConfig instance = new WebApiConfig();

        assertEquals("www.neural-guide.me", instance.getBaseUrl());
        assertEquals("1", instance.getVersion());
        assertEquals(500000, instance.getTimeout());
    }

    @Test
    public void on_creation_with_given_values_uses_these () throws Exception {
        WebApiConfig instance = new WebApiConfig("1.1", "me.me");

        assertEquals("me.me", instance.getBaseUrl());
        assertEquals("1.1", instance.getVersion());
        assertEquals(500000, instance.getTimeout());
    }

    @Test
    public void test_if_construction_of_URL_works () throws Exception {
        WebApiConfig instance = new WebApiConfig();

        assertEquals("https://www.neural-guide.me/v1/caption", instance.getUrl());
    }

    @Test
    public void test_that_setter_of_timer_is_configured_appropriately() throws Exception {
        WebApiConfig instance = new WebApiConfig();
        instance.setTimeout(40000);

        assertEquals(40000, instance.getTimeout());
    }
}
