package ru.vsu.cs.zmaev.model;

import java.util.List;

public class Quiz {
    private String title;
    private String description;
    private int numberOfQuestions;
    private int timeToComplete;
    private List<Question> questions;

    public Quiz() {
        // Default constructor required for calls to DataSnapshot.getValue(Quiz.class)
    }

    public Quiz(String title, String description, int numberOfQuestions, int timeToComplete, List<Question> questions) {
        this.title = title;
        this.description = description;
        this.numberOfQuestions = numberOfQuestions;
        this.timeToComplete = timeToComplete;
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(int timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}