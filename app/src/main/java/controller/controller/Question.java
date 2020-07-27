package controller.controller;

import java.util.List;

public class Question {

    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIdx;

    public Question(String mQuestion, List<String> mChoiceList, int mAnswerIdx) {
        this.mQuestion = mQuestion;
        this.mChoiceList = mChoiceList;
        this.mAnswerIdx = mAnswerIdx;
    }

    public void setmQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public void setmChoiceList(List<String> mChoiceList) {
        this.mChoiceList = mChoiceList;
    }

    public void setmAnswerIdx(int mAnswerIdx) {
        this.mAnswerIdx = mAnswerIdx;
    }


    public String getmQuestion() {
        return mQuestion;
    }

    public List<String> getmChoiceList() {
        return mChoiceList;
    }

    public int getmAnswerIdx() {
        return mAnswerIdx;
    }
}
