package com.mgodevelopment.geoquiz.models;

/**
 * Created by Martin on 3/14/2017.
 */

public class Question {
    // add comment

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mUserAnswer;
    private Boolean mCorrect;

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public Question(int textResId, boolean answerTrue, boolean userAnswer, Boolean correct) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mUserAnswer = userAnswer;
        mCorrect = correct;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isUserAnswer() {
        return mUserAnswer;
    }

    public void setUserAnswer(boolean userAnswer) {
        mUserAnswer = userAnswer;
    }

    public Boolean getCorrect() {
        return mCorrect;
    }

    public void setCorrect(Boolean correct) {
        mCorrect = correct;
    }
}
