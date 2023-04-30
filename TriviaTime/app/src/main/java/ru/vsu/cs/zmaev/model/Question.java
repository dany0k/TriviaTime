package ru.vsu.cs.zmaev.model;

import java.util.List;

public class Question {
    private String questionText;
    private List<String> answerOptions;
    private int correctAnswer;

    public Question() {
        // Default constructor required for calls to DataSnapshot.getValue(Question.class)
    }

    public Question(String questionText, List<String> answerOptions, int correctAnswer) {
        this.questionText = questionText;
        this.answerOptions = answerOptions;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<String> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}