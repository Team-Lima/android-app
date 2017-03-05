package com.lima2017.neuralguide.api.web;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lima2017.neuralguide.api.ImageCaptionResult;
import com.lima2017.neuralguide.api.ImprovementTip;
import com.lima2017.neuralguide.api.OnImageCaptionedListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import java8.util.Optional;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QueryCaptionEndpointTaskTest {
    @Mock OnImageCaptionedListener onImageCaptionedListener;
    @Mock IHttpRequestManager httpRequestManager;
    @Mock StringToImprovementTipMapping stringMapping;

    @Test
    public void on_given_failure_response_returns_correct_values () throws Exception {
        ArgumentCaptor<String> httpRequestCaptor =  ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String[]> mapperCaptor =  ArgumentCaptor.forClass(String[].class);

        when(httpRequestManager.sendHttpPostRequest(httpRequestCaptor.capture())).thenReturn(new ApiResponse(200, "{\n" +
                "  \"success\": true,\n" +
                "  \"status\": 200,\n" +
                "  \"data\": {\n" +
                "     \"confidence\": 0.01,\n" +
                "     \"improvementTips\": [ \"blurry\" ],\n" +
                "     \"classificationSuccess\": false\n" +
                "  }\n" +
                "}\n"));
        Set<ImprovementTip> tipsSet = new HashSet<>();
        tipsSet.add(ImprovementTip.TooBlurry);
        when(stringMapping.createImprovementTipsSet(mapperCaptor.capture())).thenReturn(tipsSet);
        QueryCaptionEndpointTask instance = new QueryCaptionEndpointTask(onImageCaptionedListener, new ObjectMapper(), httpRequestManager, stringMapping);
        byte[] bytes = new byte[10];
        for (byte i = 0; i < 10; i++){
            bytes[i] = i;
        }
        Optional<ImageCaptionResult> response = instance.doInBackground(bytes);

        assertThat(httpRequestCaptor.getValue()).isEqualTo("{\"data\":\"AAECAwQFBgcICQ==\"}");
        assertThat(mapperCaptor.getValue()[0]).isEqualTo("blurry");

        assertThat(response.isPresent()).isTrue();
        assertThat(response.get().success()).isFalse();
        assertThat(response.get().getCaption()).isEqualTo(Optional.empty());
        assertThat(response.get().getImprovementTips().contains(ImprovementTip.TooBlurry)).isTrue();

    }

    @Test
    public void on_given_success_response_returns_correct_values () throws Exception {
        ArgumentCaptor<String> httpRequestCaptor =  ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String[]> mapperCaptor =  ArgumentCaptor.forClass(String[].class);

        when(httpRequestManager.sendHttpPostRequest(httpRequestCaptor.capture())).thenReturn(new ApiResponse(200, "{\n" +
                "  \"success\": true,\n" +
                "  \"status\": 200,\n" +
                "  \"data\": {\n" +
                "     \"text\": \"a dog\",\n" +
                "     \"confidence\": 0.01,\n" +
                "     \"improvementTips\": [ \"dark\" ],\n" +
                "     \"classificationSuccess\": true\n" +
                "  }\n" +
                "}\n"));
        Set<ImprovementTip> tipsSet = new HashSet<>();
        tipsSet.add(ImprovementTip.TooDark);
        when(stringMapping.createImprovementTipsSet(mapperCaptor.capture())).thenReturn(tipsSet);
        QueryCaptionEndpointTask instance = new QueryCaptionEndpointTask(onImageCaptionedListener, new ObjectMapper(), httpRequestManager, stringMapping);
        byte[] bytes = new byte[10];
        for (byte i = 0; i < 10; i++){
            bytes[i] = i;
        }
        Optional<ImageCaptionResult> response = instance.doInBackground(bytes);

        assertThat(httpRequestCaptor.getValue()).isEqualTo("{\"data\":\"AAECAwQFBgcICQ==\"}");
        assertThat(mapperCaptor.getValue()[0]).isEqualTo("dark");

        assertThat(response.isPresent()).isTrue();
        assertThat(response.get().success()).isTrue();
        assertThat(response.get().getCaption().get()).isEqualTo("a dog");
        assertThat(response.get().getImprovementTips().contains(ImprovementTip.TooDark)).isTrue();
    }

    @Test
    public void on_given_failure_response_returns_null_values () throws Exception {
        ArgumentCaptor<String> captor =  ArgumentCaptor.forClass(String.class);

        when(httpRequestManager.sendHttpPostRequest(captor.capture())).thenReturn(new ApiResponse(100));
        QueryCaptionEndpointTask instance = new QueryCaptionEndpointTask(onImageCaptionedListener, new ObjectMapper(), httpRequestManager, stringMapping);
        byte[] bytes = null;
        Optional<ImageCaptionResult> response = instance.doInBackground(bytes);

        assertThat(captor.getValue()).isEqualTo("{\"data\":null}");

        assertThat(response.isPresent()).isTrue();
        assertThat(response.get().success()).isFalse();
        assertThat(response.get().getCaption()).isEqualTo(Optional.empty());
        assertThat(response.get().getImprovementTips().isEmpty()).isTrue();
    }
}

