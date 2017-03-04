package com.lima2017.neuralguide.api.web;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lima2017.neuralguide.api.ImageCaptionResult;
import com.lima2017.neuralguide.api.OnImageCaptionedListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

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
        ArgumentCaptor<String> captor =  ArgumentCaptor.forClass(String.class);
        /*doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                return null;
            }
        }).when(onImageCaptionedListener).onImageCaptioned(captor.capture());*/
        //"{\"data\":\"AAECAwQFBgcICQ==\"}"
        when(httpRequestManager.sendHttpPostRequest(captor.capture())).thenReturn(new ApiResponse(200, "{\n" +
                "  \"success\": true,\n" +
                "  \"status\": 200,\n" +
                "  \"data\": {\n" +
                "     \"confidence\": 0.01,\n" +
                "     \"improvementTips\": [ \"too blurry\" ],\n" +
                "     \"classificationSuccess\": false\n" +
                "  }\n" +
                "}\n"));
        QueryCaptionEndpointTask instance = new QueryCaptionEndpointTask(onImageCaptionedListener, new ObjectMapper(), httpRequestManager, stringMapping);
        byte[] bytes = new byte[10];
        for (byte i = 0; i < 10; i++){
            bytes[i] = i;
        }
        Optional<ImageCaptionResult> response = instance.doInBackground(bytes);
        //wire mock
        assertThat(response.isPresent()).isTrue();
        assertThat(response.get().success()).isFalse();
        assertThat(response.get().getCaption()).isEqualTo(Optional.empty());
        assertThat(captor.getValue()).isEqualTo("{\"data\":\"AAECAwQFBgcICQ==\"}");
    }
}

