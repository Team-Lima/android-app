package com.lima2017.neuralguide.api.web;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java8.util.Optional;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpRequestManagerTest {
    @Rule
    public WireMockRule theWireMockRule = new WireMockRule(wireMockConfig().dynamicPort().dynamicHttpsPort());

    @Mock WebApiConfig config;

    @Test
    public void when_given_correct_data_returns_result () throws Exception {
        when(config.getUrl()).thenReturn("http://localhost:" + theWireMockRule.port() + "/v1/caption");
        HttpRequestManager instance = new HttpRequestManager(config);

        stubFor(post(urlEqualTo("/v1/caption"))
                .withRequestBody(equalTo("{\"data\":\"AAECAwQFBgcICQ==\"}"))
                .willReturn(aResponse().withStatus(200)
                .withBody("\"{\\n\" +\n" +
                        "                \"  \\\"success\\\": true,\\n\" +\n" +
                        "                \"  \\\"status\\\": 200,\\n\" +\n" +
                        "                \"  \\\"data\\\": {\\n\" +\n" +
                        "                \"     \\\"confidence\\\": 0.01,\\n\" +\n" +
                        "                \"     \\\"improvementTips\\\": [ \\\"blurry\\\" ],\\n\" +\n" +
                        "                \"     \\\"classificationSuccess\\\": false\\n\" +\n" +
                        "                \"  }\\n\" +\n" +
                        "                \"}\\n\"")));
        ApiResponse response = instance.sendHttpPostRequest("{\"data\":\"AAECAwQFBgcICQ==\"}");

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getResponse().get()).isEqualTo("\"{\\n\" +\n" +
                "                \"  \\\"success\\\": true,\\n\" +\n" +
                "                \"  \\\"status\\\": 200,\\n\" +\n" +
                "                \"  \\\"data\\\": {\\n\" +\n" +
                "                \"     \\\"confidence\\\": 0.01,\\n\" +\n" +
                "                \"     \\\"improvementTips\\\": [ \\\"blurry\\\" ],\\n\" +\n" +
                "                \"     \\\"classificationSuccess\\\": false\\n\" +\n" +
                "                \"  }\\n\" +\n" +
                "                \"}\\n\"");
    }

    @Test
    public void when_given_incorrect_data_returns_result () throws Exception {
        when(config.getUrl()).thenReturn("http://localhost:" + theWireMockRule.port() + "/v1/caption");
        HttpRequestManager instance = new HttpRequestManager(config);

        stubFor(post(urlEqualTo("/v1/caption"))
                .withRequestBody(equalTo("{\"data\": \"Empty\" }"))
                .willReturn(aResponse().withStatus(404)));
        ApiResponse response = instance.sendHttpPostRequest("{\"data\": \"Empty\" }");

        assertThat(response.getStatusCode()).isEqualTo(404);
        assertThat(response.getResponse()).isEqualTo(Optional.empty());
    }
}
