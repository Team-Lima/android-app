package com.lima2017.neuralguide.api;

/**
 * Created by Tamara Norman on 09/02/2017.
 */

public class NeuralGuideData {
    private String text;
    private String[] improvementTips;
    private int confidence;

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public String[] getImprovementTips(){
        return improvementTips;
    }

    public void setImprovementTips(String[] improvementTips){
        this.improvementTips = improvementTips;
    }

    public int getConfidence(){
        return confidence;
    }

    public void setConfidence(int Confidence){
        this.confidence = confidence;
    }
}
