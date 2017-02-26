package com.lima2017.neuralguide.api;

/**
 * Represents the format of the data section of the Json received by the client.
 */
public class NeuralGuideResultData {
    /**The text returned in the Json to show to the user*/
    private String mText;

    /**The improvement tips received to be conveyed to the user*/
    private String[] mImprovementTips;

    /**The confidence interval returned from the server*/
    private double mConfidence;

    /**Whether the neural network returned a successful response*/
    private boolean mClassificationSuccess;

    /**
     * @param mText Sets the value of the text field from the Json file
     */
    public void setText(String mText) {
        this.mText = mText;
    }
    /**
     * @return The text to be displayed to the client
     */
    public String getText(){
        return mText;
    }

    /**
     * @param mImprovementTips Set the value of the improvement tips from the Json file
     */
    public void setImprovementTips(String[] mImprovementTips) {
        this.mImprovementTips = mImprovementTips;
    }

    /**
     * @return The improvement tips generated by the server on how to improve the result
     */
    public String[] getImprovementTips(){
        return mImprovementTips;
    }

    /**
     * @param mConfidence Sets the confidence value from the Json file
     */
    public void setConfidence(double mConfidence) {
        this.mConfidence = mConfidence;
        //Is it worth limiting this to between 0 and 1??
    }

    /**
     * @return The confidence interval generated by the neural net
     */
    public double getConfidence(){
        return mConfidence;
    }

    /**
     * @param mClassificationSuccess Sets whether the classification was successful from the Json file
     */
    public void setClassificationSuccess(boolean mClassificationSuccess) {
        this.mClassificationSuccess = mClassificationSuccess;
    }

    /**
     * @return The classification status of the neural net indicating if text exists
     */
    public boolean getClassificationSuccess () { return mClassificationSuccess; }

}