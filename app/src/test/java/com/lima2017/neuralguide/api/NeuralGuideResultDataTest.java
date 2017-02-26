package com.lima2017.neuralguide.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NeuralGuideResultDataTest {

    @Test
    public void testing_getter_and_setter_of_text () {
        NeuralGuideResultData instance = new NeuralGuideResultData();
        instance.setText("Hello");

        assertEquals("Hello", instance.getText());
    }

    @Test
    public void testing_getter_and_setter_of_improvement_tips () {
        NeuralGuideResultData instance = new NeuralGuideResultData();
        String[] data = new String[]{"a", "b"};
        instance.setImprovementTips(data);

        assertEquals("a", instance.getImprovementTips()[0]);
        assertEquals("b", instance.getImprovementTips()[1]);
    }

    @Test
    public void testing_getter_and_setter_of_confidence () {
        NeuralGuideResultData instance = new NeuralGuideResultData();
        instance.setConfidence(0.01);

        assertEquals(0.01, instance.getConfidence(), 0);
    }

    @Test
    public void testing_getter_and_setter_of_classification_success () {
        NeuralGuideResultData instance = new NeuralGuideResultData();
        instance.setClassificationSuccess(true);

        assertEquals(true, instance.getClassificationSuccess());
    }
}
