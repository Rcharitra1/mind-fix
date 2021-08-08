package ca.nait.rcharitra1.finalproject.model;

public class Question {

    private String questionString;
    private String answerString;
    private String [] inCorrectAnswers;

    public Question(String questionString, String answerString, String []inCorrectAnswers) {
        this.questionString = questionString;
        this.answerString = answerString;
        this.inCorrectAnswers = inCorrectAnswers;

    }
    public Question()
    {

    }

    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public String getAnswerString() {
        return answerString;
    }

    public String[] getInCorrectAnswers() {
        return inCorrectAnswers;
    }

    public void setInCorrectAnswers(String[] inCorrectAnswers) {
        this.inCorrectAnswers = inCorrectAnswers;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }
}
